package it.permessi.rest.permessi.repository;

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

    @Query("SELECT p FROM Post p LEFT JOIN p.likes ORDER BY SIZE(p.likes) DESC")
    List<Post> findPostsOrderByLikesDesc(Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.utente.username IN :usernames ORDER BY p.dataOra DESC")
    List<Post> findByUtenteUsernameIn(@Param("usernames") List<String> usernames);
}
