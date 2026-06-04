package it.permessi.rest.permessi.repository;

import it.permessi.rest.permessi.entity.OpzioneSondaggio;
import it.permessi.rest.permessi.entity.Sondaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OpzioneRepository extends JpaRepository<OpzioneSondaggio, Long> {

    @Modifying
    @Query("DELETE FROM OpzioneSondaggio o WHERE o.sondaggio = :sondaggio")
    void bulkDeleteBySondaggio(@Param("sondaggio") Sondaggio sondaggio);
}
