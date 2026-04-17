package it.permessi.rest.permessi.service;

import it.permessi.rest.permessi.dto.GruppoDto;
import it.permessi.rest.permessi.dto.LoginRequest;
import it.permessi.rest.permessi.dto.LoginResponse;
import it.permessi.rest.permessi.dto.NuovaPasswordRequest;
import it.permessi.rest.permessi.dto.PasswordResetRequest;
import it.permessi.rest.permessi.dto.PermessoDto;
import it.permessi.rest.permessi.dto.RegistrazioneRequest;
import it.permessi.rest.permessi.dto.RuoloDto;
import it.permessi.rest.permessi.dto.UtenteFormDto;
import it.permessi.rest.permessi.dto.VerificaCodiceRequest;
import it.permessi.rest.permessi.entity.Ruolo;
import it.permessi.rest.permessi.entity.TokenResetPassword;
import it.permessi.rest.permessi.entity.Utente;
import it.permessi.rest.permessi.mapper.DtoMapper;
import it.permessi.rest.permessi.repository.RuoloRepository;
import it.permessi.rest.permessi.repository.TokenResetPasswordRepository;
import it.permessi.rest.permessi.repository.UtenteRepository;
import it.permessi.rest.permessi.security.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/** Logica di autenticazione: login (JWT) e costruzione payload autorizzazioni. */
@Service
public class AuthService {

    @Autowired private AuthenticationManager authManager;
    @Autowired private JwtUtils jwtUtils;
    @Autowired private UtenteRepository utenteRepository;
    @Autowired private RuoloRepository ruoloRepo;
    @Autowired private UtenteService utenteService;
    @Autowired private TokenResetPasswordRepository resetCodeRepository;
    @Autowired private EmailService emailService;
    @Autowired private PasswordEncoder passwordEncoder;


    /** Esegue il login e costruisce la LoginResponse completa. */
    public LoginResponse login(@Valid LoginRequest req) {
        // 1) Autentica credenziali
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        // 2) Genera JWT
        String token = jwtUtils.generateJwtToken(req.getUsername(), Map.of());

        // 3) Recupera utente
        Utente u = utenteRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        // 4) Ruoli (nel tuo dominio: uno solo, ma restituiamo una lista)
        List<RuoloDto> ruoli = (u.getRuolo() != null)
                ? List.of(DtoMapper.toRuoloDtoLight(u.getRuolo()))
                : List.of();

        // 5) Permessi dal ruolo (dedupe per alias)
        List<PermessoDto> permessi = (u.getRuolo() != null)
                ? u.getRuolo().getRuoloPermessi().stream()
                    .map(rp -> DtoMapper.toPermessoDtoLight(rp.getPermesso()))
                    .collect(Collectors.collectingAndThen(
                            Collectors.toMap(PermessoDto::getAlias, p -> p, (a, b) -> a),
                            m -> new ArrayList<>(m.values())))
                : List.of();

        // 6) Gruppi dai permessi (DTO già pronti; dedupe per alias)
        List<GruppoDto> gruppi = permessi.stream()
                .map(PermessoDto::getGruppo)
                .filter(Objects::nonNull)
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(GruppoDto::getAlias, g -> g, (a, b) -> a),
                        m -> new ArrayList<>(m.values())));

        // 7) Costruisci risposta
        LoginResponse resp = new LoginResponse();
        resp.setToken(token);
        resp.setUsername(u.getUsername());
        resp.setNome(u.getNome());         // NEW
        resp.setCognome(u.getCognome());   // NEW
        resp.setRuoli(ruoli);
        resp.setPermessi(permessi);
        resp.setGruppi(gruppi);
        return resp;
    }
    
    public void registrazione(@Valid RegistrazioneRequest req) {
        // 1) Verifica che l'username non esista già
        if (utenteRepository.findByUsername(req.getUsername()).isPresent()) {
            throw new RuntimeException("Username già registrato");
        }

        // 2) Crea UtenteFormDto
        UtenteFormDto formDto = new UtenteFormDto();
        formDto.setNome(req.getNome());
        formDto.setCognome(req.getCognome());
        formDto.setUsername(req.getUsername());
        formDto.setPassword(req.getPassword());
        formDto.setEmail(req.getEmail()); // Obbligatorio dal form
        
        // Imposta ruolo USER
        Ruolo ruoloUser = ruoloRepo.findByNome("USER")
                .orElseThrow(() -> new RuntimeException("Ruolo USER non trovato"));
        
        RuoloDto ruoloDto = new RuoloDto();
        ruoloDto.setId(ruoloUser.getId());
        formDto.setRuolo(ruoloDto);

        // 3) Salva l'utente
        utenteService.create(formDto);
    }

    /** Logout stateless: qui non serve fare nulla. */
    public void logout() {
        // Se vuoi implementare una blacklist di token, fallo qui.
    }
    
    
    //provaaa
    
    /** Invia codice a 4 cifre via email */
    public void richiestaResetPassword(PasswordResetRequest req) {
        // Trova utente per email (assicurati che Utente abbia il campo email)
        Utente utente = utenteRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Email non trovata"));
        
        // Elimina eventuali codici precedenti
        resetCodeRepository.findByUtente(utente).ifPresent(resetCodeRepository::delete);
        
        // Genera codice casuale a 4 cifre
        String codice = String.format("%04d", new Random().nextInt(10000));
        
        // Crea nuovo codice (valido 15 minuti, 3 tentativi)
        TokenResetPassword resetCode = new TokenResetPassword();
        resetCode.setCodice(codice);
        resetCode.setUtente(utente);
        resetCode.setDataScadenza(LocalDateTime.now().plusMinutes(15));
        resetCode.setTentativiRimasti(3);
        resetCodeRepository.save(resetCode);
        
        // Invia email
        emailService.inviaCodiceDiReset(utente.getEmail(), codice);
    }

    /** Verifica il codice a 4 cifre */
    public boolean verificaCodice(VerificaCodiceRequest req) {
    	TokenResetPassword resetCode = resetCodeRepository.findByCodice(req.getCodice())
                .orElseThrow(() -> new RuntimeException("Codice non valido"));
        
        // Verifica scadenza
        if (resetCode.getDataScadenza().isBefore(LocalDateTime.now())) {
            resetCodeRepository.delete(resetCode);
            throw new RuntimeException("Codice scaduto");
        }
        
        // Verifica tentativi
        if (resetCode.getTentativiRimasti() <= 0) {
            resetCodeRepository.delete(resetCode);
            throw new RuntimeException("Troppi tentativi. Richiedi un nuovo codice.");
        }
        
        // Decrementa tentativi
        resetCode.setTentativiRimasti(resetCode.getTentativiRimasti() - 1);
        resetCodeRepository.save(resetCode);
        
        return true;
    }

    /** Imposta nuova password con il codice */
    public void impostaNuovaPassword(NuovaPasswordRequest req) {
    	TokenResetPassword resetCode = resetCodeRepository.findByCodice(req.getCodice())
                .orElseThrow(() -> new RuntimeException("Codice non valido"));
        
        // Verifica scadenza
        if (resetCode.getDataScadenza().isBefore(LocalDateTime.now())) {
            resetCodeRepository.delete(resetCode);
            throw new RuntimeException("Codice scaduto");
        }
        
        // Aggiorna password
        Utente utente = resetCode.getUtente();
        utente.setPassword(passwordEncoder.encode(req.getNuovaPassword()));
        utenteRepository.save(utente);
        
        // Elimina codice usato
        resetCodeRepository.delete(resetCode);
    }
}
