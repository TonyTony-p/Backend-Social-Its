package it.permessi.rest.permessi.service;

import it.permessi.rest.permessi.dto.CreaProfessoreRequest;
import it.permessi.rest.permessi.dto.UtenteDto;
import it.permessi.rest.permessi.entity.Ruolo;
import it.permessi.rest.permessi.entity.Utente;
import it.permessi.rest.permessi.mapper.DtoMapper;
import it.permessi.rest.permessi.repository.RuoloRepository;
import it.permessi.rest.permessi.repository.UtenteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IstitutoService {

    private static final Logger log = LoggerFactory.getLogger(IstitutoService.class);

    @Autowired private UtenteRepository utenteRepo;
    @Autowired private RuoloRepository ruoloRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    @Transactional
    public UtenteDto creaProfessore(CreaProfessoreRequest req) {
        if (utenteRepo.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Email già registrata");
        }

        Ruolo ruoloProfessore = ruoloRepo.findByNome("PROFESSORE")
                .orElseThrow(() -> new RuntimeException("Ruolo PROFESSORE non trovato"));

        String username = generaUsername(req.getNome(), req.getCognome());

        Utente utente = new Utente();
        utente.setNome(req.getNome());
        utente.setCognome(req.getCognome());
        utente.setEmail(req.getEmail());
        utente.setUsername(username);
        utente.setPassword(passwordEncoder.encode(req.getPassword()));
        utente.setRuolo(ruoloProfessore);

        Utente saved = utenteRepo.save(utente);
        log.info("creato_professore username={} email={}", username, req.getEmail());
        return DtoMapper.toUtenteDto(saved);
    }

    @Transactional(readOnly = true)
    public List<UtenteDto> listaProfessori() {
        Ruolo ruoloProfessore = ruoloRepo.findByNome("PROFESSORE")
                .orElseThrow(() -> new RuntimeException("Ruolo PROFESSORE non trovato"));
        return utenteRepo.findAll().stream()
                .filter(u -> ruoloProfessore.equals(u.getRuolo()))
                .map(DtoMapper::toUtenteDto)
                .collect(Collectors.toList());
    }

    private String generaUsername(String nome, String cognome) {
        String base = (nome.substring(0, 1) + cognome).toLowerCase().replaceAll("[^a-z0-9]", "");
        String candidate = base;
        int counter = 1;
        while (utenteRepo.findByUsername(candidate).isPresent()) {
            candidate = base + counter++;
        }
        return candidate;
    }
}
