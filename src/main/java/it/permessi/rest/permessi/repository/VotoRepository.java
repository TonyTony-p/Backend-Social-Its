package it.permessi.rest.permessi.repository;

import it.permessi.rest.permessi.entity.Sondaggio;
import it.permessi.rest.permessi.entity.Utente;
import it.permessi.rest.permessi.entity.VotoSondaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VotoRepository extends JpaRepository<VotoSondaggio, Long> {

    Optional<VotoSondaggio> findBySondaggioAndUtente(Sondaggio sondaggio, Utente utente);

    @Query("SELECT v.opzione.idOpzione FROM VotoSondaggio v WHERE v.utente.username = :username AND v.sondaggio.idSondaggio = :sondaggioId")
    Long findIdOpzioneByUsernameAndSondaggioId(@Param("username") String username, @Param("sondaggioId") Long sondaggioId);
}
