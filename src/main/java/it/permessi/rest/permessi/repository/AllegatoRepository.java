package it.permessi.rest.permessi.repository;

import it.permessi.rest.permessi.entity.Allegato;
import it.permessi.rest.permessi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AllegatoRepository extends JpaRepository<Allegato, Long> {

    @Modifying
    @Query("DELETE FROM Allegato a WHERE a.post = :post")
    void bulkDeleteByPost(@Param("post") Post post);
}
