package it.permessi.rest.permessi.repository;

import it.permessi.rest.permessi.entity.Post;
import it.permessi.rest.permessi.entity.Sondaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SondaggioRepository extends JpaRepository<Sondaggio, Long> {

    @Modifying
    @Query("DELETE FROM Sondaggio s WHERE s.post = :post")
    void bulkDeleteByPost(@Param("post") Post post);
}
