package it.permessi.rest.permessi.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.permessi.rest.permessi.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUtenteId(Long idUtente);
    List<Post> findByUtenteUsernameOrderByDataOraDesc(String username);

    Page<Post> findAllByOrderByDataOraDesc(Pageable pageable);

    @Query(value = "SELECT p FROM Post p LEFT JOIN p.likes l WHERE p.dataOra >= :since GROUP BY p ORDER BY COUNT(l) DESC",
           countQuery = "SELECT COUNT(p) FROM Post p WHERE p.dataOra >= :since")
    Page<Post> findTrendingPostsSince(@Param("since") LocalDateTime since, Pageable pageable);

    @Query("SELECT p FROM Post p ORDER BY SIZE(p.likes) DESC")
    List<Post> findPostsOrderByLikesDesc(Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.utente.username IN :usernames ORDER BY p.dataOra DESC")
    Page<Post> findByUtenteUsernameIn(@Param("usernames") List<String> usernames, Pageable pageable);
}
