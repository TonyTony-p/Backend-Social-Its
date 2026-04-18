package it.permessi.rest.permessi.service;

import it.permessi.rest.permessi.dto.PageResponse;
import it.permessi.rest.permessi.dto.ProfiloDto;
import it.permessi.rest.permessi.dto.ProfiloFormDto;
import it.permessi.rest.permessi.dto.UtenteFormDto;
import it.permessi.rest.permessi.dto.UtenteDto;
import it.permessi.rest.permessi.entity.Ruolo;
import it.permessi.rest.permessi.entity.Utente;
import it.permessi.rest.permessi.repository.RuoloRepository;
import it.permessi.rest.permessi.repository.UtenteRepository;
import it.permessi.rest.permessi.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/** Logica di business per Utenti. */
@Service
public class UtenteService {

    @Autowired private UtenteRepository repo;
    @Autowired private RuoloRepository ruoloRepo;
    @Autowired private PasswordEncoder encoder;

    
    
    //lo puo fare solo admin
    /** Crea utente (password codificata). */
    public UtenteDto create(UtenteFormDto form) {
        Utente u = new Utente();
        apply(u, form, true);
        return DtoMapper.toUtenteDto(repo.save(u)); // Ruolo light dentro UtenteDto
    }

    /** Aggiorna utente. */
    public UtenteDto update(UtenteFormDto form) {
        if (form.getId() == null) throw new RuntimeException("Id utente obbligatorio per update");
        Utente u = repo.findById(form.getId())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        apply(u, form, false);
        return DtoMapper.toUtenteDto(repo.save(u)); // Ruolo light
    }

    /** Elimina utente. */
    public void delete(Long id) {
    	repo.deleteById(id);
    	}
    
    

    /** Dettaglio utente. (Ruolo light) */
    @Transactional(readOnly = true)
    public UtenteDto getById(Long id) {
        return repo.findById(id)
                .map(DtoMapper::toUtenteDto)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
    }
    
    

    /** Lista paginata utenti (mapper light). */
    @Transactional(readOnly = true)
    public PageResponse<UtenteDto> list(Pageable pageable) {
        return PageResponse.from(repo.findAll(pageable).map(DtoMapper::toUtenteDto));
    }

    
    
    /** Lista completa non paginata (mapper light). */
    @Transactional(readOnly = true)
    public List<UtenteDto> listAll() {
        return repo.findAll().stream()
                .map(DtoMapper::toUtenteDto)
                .collect(Collectors.toList());
    }
    
    
    
    /** Profilo utente corrente (basato sull'username dell'utente loggato). */
    @Transactional(readOnly = true)
    public UtenteDto getMyProfile(String username) {
        return repo.findByUsername(username)
                .map(DtoMapper::toUtenteDto)
                .orElseThrow(() -> new RuntimeException("Utente non trovato con username: " + username));
    }
    
    
    
    /** Utente con lista post (dettaglio completo). Un utente visualizza il profilo di un altro utente*/
    @Transactional(readOnly = true)
    public UtenteDto getUtenteConPost(Long id) {
        return repo.findWithPostsById(id)
                .map(DtoMapper::toUtenteDtoWithPosts)
                .orElseThrow(() -> new RuntimeException("Utente non trovato con id: " + id));
    }

    
    
    /** Profilo utente corrente con lista post. */
    @Transactional(readOnly = true)
    public UtenteDto getMyProfileConPost(String username) {
        return repo.findWithPostsByUsername(username)
                .map(DtoMapper::toUtenteDtoWithPosts)
                .orElseThrow(() -> new RuntimeException("Utente non trovato con username: " + username));
    }
    
    
    
    /** Profilo pubblico di un utente per username (include post e stats). */
    @Transactional(readOnly = true)
    public ProfiloDto getProfilo(String username) {
        return repo.findWithPostsByUsername(username)
                .map(DtoMapper::toProfiloDto)
                .orElseThrow(() -> new RuntimeException("Utente non trovato con username: " + username));
    }

    /** Aggiorna i campi modificabili del proprio profilo. */
    @Transactional
    public ProfiloDto updateMyProfilo(String username, ProfiloFormDto form) {
        Utente u = repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato con username: " + username));
        if (form.getNome() != null && !form.getNome().isBlank()) u.setNome(form.getNome());
        if (form.getCognome() != null && !form.getCognome().isBlank()) u.setCognome(form.getCognome());
        u.setBio(form.getBio());
        u.setFotoProfilo(form.getFotoProfilo());
        if (form.getDataNascita() != null) u.setDataNascita(form.getDataNascita());
        if (form.getTelefono() != null) u.setTelefono(form.getTelefono());
        if (form.getIndirizzo() != null) u.setIndirizzo(form.getIndirizzo());
        return DtoMapper.toProfiloDto(repo.save(u));
    }

    /** Copia campi dal form, gestendo password e ruolo. */
    private void apply(Utente u, UtenteFormDto form, boolean encodePasswordAlways) {
        u.setNome(form.getNome());
        u.setCognome(form.getCognome());
        u.setEmail(form.getEmail());
        u.setUsername(form.getUsername());
        if (encodePasswordAlways || (form.getPassword() != null && !form.getPassword().isBlank())) {
            u.setPassword(encoder.encode(form.getPassword()));
        }
        u.setDataNascita(form.getDataNascita());
        u.setTelefono(form.getTelefono());
        u.setIndirizzo(form.getIndirizzo());

        Ruolo ruolo = ruoloRepo.findById(form.getRuolo().getId())
                .orElseThrow(() -> new RuntimeException("Ruolo non trovato"));
        u.setRuolo(ruolo);
    }
    
    
}
