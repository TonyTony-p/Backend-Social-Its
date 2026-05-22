package it.permessi.rest.permessi.repository;

import it.permessi.rest.permessi.entity.Notifica;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificaRepository extends JpaRepository<Notifica, Long> {

    @Query(value = "SELECT * FROM notifiche n WHERE n.destinatario_id = (SELECT id FROM utenti WHERE username = :username) ORDER BY n.created_at DESC", nativeQuery = true)
    List<Notifica> findByDestinatario_UsernameOrderByCreatedAtDesc(@Param("username") String username, Pageable pageable);

    @Query(value = "SELECT count(*) FROM notifiche n WHERE n.destinatario_id = (SELECT id FROM utenti WHERE username = :username) AND n.letta = 0", nativeQuery = true)
    long countNonLette(@Param("username") String username);

    @Query(value = "SELECT * FROM notifiche n WHERE n.destinatario_id = (SELECT id FROM utenti WHERE username = :username) AND n.letta = 0", nativeQuery = true)
    List<Notifica> findNonLette(@Param("username") String username);

    @Modifying
    @Query("UPDATE Notifica n SET n.letta = true WHERE n.destinatario.username = :username AND n.letta = false")
    void segnaComeLetteTutte(@Param("username") String username);

    @Modifying
    @Query("DELETE FROM Notifica n WHERE n.createdAt < :limite")
    void deleteByCreatedAtBefore(@Param("limite") java.time.LocalDateTime limite);

    /** Prima notifica non letta dello stesso tipo e riferimento per un destinatario (per deduplicazione). */
    @Query("SELECT n FROM Notifica n WHERE n.destinatario.username = :username AND n.tipo = :tipo AND n.idRiferimento = :rifId AND n.letta = false ORDER BY n.createdAt DESC")
    List<Notifica> findUnreadByTipoAndRiferimento(@Param("username") String username,
                                                   @Param("tipo") String tipo,
                                                   @Param("rifId") Long rifId,
                                                   Pageable pageable);
}
