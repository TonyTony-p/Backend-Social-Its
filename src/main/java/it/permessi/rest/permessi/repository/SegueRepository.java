package it.permessi.rest.permessi.repository;

import it.permessi.rest.permessi.entity.Segue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SegueRepository extends JpaRepository<Segue, Long> {

    boolean existsBySeguaceUsernameAndSeguitoUsername(String seguaceUsername, String seguitoUsername);

    Optional<Segue> findBySeguaceUsernameAndSeguitoUsername(String seguaceUsername, String seguitoUsername);

    long countBySeguaceUsername(String username);

    long countBySeguitoUsername(String username);

    @Query("SELECT s.seguito.username FROM Segue s WHERE s.seguace.username = :username")
    List<String> findSeguitiUsernamesBySeguace(@Param("username") String username);
}
