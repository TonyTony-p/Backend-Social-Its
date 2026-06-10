package it.permessi.rest.permessi.repository;

import it.permessi.rest.permessi.entity.Post;
import it.permessi.rest.permessi.entity.Segnalazione;
import it.permessi.rest.permessi.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SegnalazioneRepository extends JpaRepository<Segnalazione, Long> {
    boolean existsByUtenteAndPost(Utente utente, Post post);
    void deleteByPost(Post post);

    @org.springframework.data.jpa.repository.Modifying
    @org.springframework.data.jpa.repository.Query("DELETE FROM Segnalazione s WHERE s.post = :post")
    void bulkDeleteByPost(@org.springframework.data.repository.query.Param("post") Post post);
}
