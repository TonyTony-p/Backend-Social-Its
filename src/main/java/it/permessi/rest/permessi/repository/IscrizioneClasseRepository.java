package it.permessi.rest.permessi.repository;

import it.permessi.rest.permessi.entity.IscrizioneClasse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IscrizioneClasseRepository extends JpaRepository<IscrizioneClasse, Long> {
    List<IscrizioneClasse> findByClasse_Id(Long classeId);

    @Query("SELECT i FROM IscrizioneClasse i LEFT JOIN FETCH i.classe c LEFT JOIN FETCH c.professore LEFT JOIN FETCH i.studente WHERE i.studente.username = :username")
    List<IscrizioneClasse> findByStudente_Username(@Param("username") String username);

    Optional<IscrizioneClasse> findByStudente_UsernameAndClasse_Id(String username, Long classeId);
    boolean existsByStudente_UsernameAndClasse_Id(String username, Long classeId);
    long countByClasse_IdAndStato(Long classeId, IscrizioneClasse.StatoIscrizione stato);
    List<IscrizioneClasse> findByClasse_IdAndStato(Long classeId, IscrizioneClasse.StatoIscrizione stato);
}
