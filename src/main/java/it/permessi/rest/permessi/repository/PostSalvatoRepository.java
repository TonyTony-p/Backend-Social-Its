package it.permessi.rest.permessi.repository;

import it.permessi.rest.permessi.entity.Post;
import it.permessi.rest.permessi.entity.PostSalvato;
import it.permessi.rest.permessi.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostSalvatoRepository extends JpaRepository<PostSalvato, Long> {
    boolean existsByUtenteAndPost(Utente utente, Post post);
    Optional<PostSalvato> findByUtenteAndPost(Utente utente, Post post);
    List<PostSalvato> findByUtente(Utente utente);
    List<PostSalvato> findByUtenteOrderBySalvatoAtDesc(Utente utente);
}
