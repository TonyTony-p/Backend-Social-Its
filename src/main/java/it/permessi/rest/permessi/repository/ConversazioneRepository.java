package it.permessi.rest.permessi.repository;

import it.permessi.rest.permessi.entity.Conversazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConversazioneRepository extends JpaRepository<Conversazione, Long> {

    @Query("SELECT c FROM Conversazione c WHERE (c.utente1.username = :u1 AND c.utente2.username = :u2) OR (c.utente1.username = :u2 AND c.utente2.username = :u1)")
    Optional<Conversazione> findBetween(@Param("u1") String u1, @Param("u2") String u2);

    @Query("SELECT c FROM Conversazione c WHERE c.utente1.username = :username OR c.utente2.username = :username ORDER BY c.updatedAt DESC")
    List<Conversazione> findAllByUsername(@Param("username") String username);
}
