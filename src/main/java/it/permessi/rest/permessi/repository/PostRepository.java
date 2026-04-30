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

    Page<Post> findAllByOrderByDataOraDesc(Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.dataOra >= :since ORDER BY SIZE(p.likes) DESC")
    List<Post> findTrendingPostsSince(@Param("since") LocalDateTime since, Pageable pageable);

    @Query("SELECT p FROM Post p ORDER BY SIZE(p.likes) DESC")
    List<Post> findPostsOrderByLikesDesc(Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.utente.username IN :usernames ORDER BY p.dataOra DESC")
    List<Post> findByUtenteUsernameIn(@Param("usernames") List<String> usernames);
}
