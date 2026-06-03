package it.permessi.rest.permessi.service;

import it.permessi.rest.permessi.dto.SegnalazioneDto;
import it.permessi.rest.permessi.dto.SegnalazioneFormDto;
import it.permessi.rest.permessi.entity.Post;
import it.permessi.rest.permessi.entity.Segnalazione;
import it.permessi.rest.permessi.entity.Utente;
import it.permessi.rest.permessi.repository.PostRepository;
import it.permessi.rest.permessi.repository.SegnalazioneRepository;
import it.permessi.rest.permessi.repository.UtenteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SegnalazioneService {

    private static final List<String> MOTIVI_VALIDI = List.of(
            "SPAM", "INAPPROPRIATO", "MOLESTIE", "DISINFORMAZIONE", "ALTRO"
    );

    @Autowired private SegnalazioneRepository segnalazioneRepo;
    @Autowired private PostRepository postRepo;
    @Autowired private UtenteRepository utenteRepo;

    @Transactional
    public SegnalazioneDto segnala(SegnalazioneFormDto form, UserDetails userDetails) {
        if (form.getIdPost() == null || form.getMotivo() == null || form.getMotivo().isBlank())
            throw new IllegalArgumentException("idPost e motivo sono obbligatori");

        String motivoUpper = form.getMotivo().toUpperCase();
        if (!MOTIVI_VALIDI.contains(motivoUpper))
            throw new IllegalArgumentException("Motivo non valido");

        Post post = postRepo.findById(form.getIdPost())
                .orElseThrow(() -> new EntityNotFoundException("Post non trovato"));

        Utente utente = utenteRepo.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));

        if (segnalazioneRepo.existsByUtenteAndPost(utente, post))
            throw new IllegalStateException("Hai già segnalato questo post");

        // Non permettere di segnalare i propri post
        if (post.getUtente().getUsername().equals(userDetails.getUsername()))
            throw new IllegalArgumentException("Non puoi segnalare il tuo post");

        Segnalazione s = new Segnalazione();
        s.setPost(post);
        s.setUtente(utente);
        s.setMotivo(motivoUpper);
        segnalazioneRepo.save(s);

        return new SegnalazioneDto(s.getId(), post.getIdPost(), utente.getUsername(),
                s.getMotivo(), s.getCreatedAt());
    }
}
