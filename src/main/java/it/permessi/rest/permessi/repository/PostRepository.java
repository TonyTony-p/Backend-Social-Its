package it.permessi.rest.permessi.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.permessi.rest.permessi.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> { 
	 List<Post> findByUtenteId(Long idUtente);
	    @Query("SELECT p FROM Post p LEFT JOIN p.likes ORDER BY SIZE(p.likes) DESC")//usata query per istruzione
	    List<Post> findPostsOrderByLikesDesc(Pageable pageable);
}
