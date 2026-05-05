package it.permessi.rest.permessi.repository;

import it.permessi.rest.permessi.entity.Messaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessaggioRepository extends JpaRepository<Messaggio, Long> {

    @Modifying
    @Query("UPDATE Messaggio m SET m.letto = true WHERE m.conversazione.id = :convId AND m.mittente.username <> :username AND m.letto = false")
    void segnaComeLetti(@Param("convId") Long convId, @Param("username") String username);

    @Query("SELECT COUNT(m) FROM Messaggio m WHERE m.conversazione.id = :convId AND m.mittente.username <> :username AND m.letto = false")
    int countNonLetti(@Param("convId") Long convId, @Param("username") String username);

    @Query("SELECT COUNT(m) FROM Messaggio m WHERE (m.conversazione.utente1.username = :username OR m.conversazione.utente2.username = :username) AND m.mittente.username <> :username AND m.letto = false")
    long countTotaleNonLetti(@Param("username") String username);

    @Modifying
    @Query("UPDATE Messaggio m SET m.eliminato = true WHERE m.id = :id AND m.mittente.username = :username")
    int eliminaById(@Param("id") Long id, @Param("username") String username);

    /** Verifica che l'utente sia partecipante della conversazione del messaggio. */
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Messaggio m WHERE m.id = :id AND (m.conversazione.utente1.username = :username OR m.conversazione.utente2.username = :username)")
    boolean isPartecipante(@Param("id") Long id, @Param("username") String username);
}
