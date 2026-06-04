package it.permessi.rest.permessi.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.permessi.rest.permessi.entity.Like;
import it.permessi.rest.permessi.entity.Post;
import it.permessi.rest.permessi.entity.Utente;

public interface LikeRepository extends JpaRepository <Like, Long>{

	boolean existsByUtenteAndPost(Utente utente, Post post);
	Optional<Like> findByUtenteAndPost(Utente utente, Post post);
	Page<Like> findByUtente(Utente utente, Pageable pageable);
	Page<Like> findByPost(Post post, Pageable pageable);

	@org.springframework.data.jpa.repository.Modifying
	@org.springframework.data.jpa.repository.Query("DELETE FROM Like l WHERE l.post = :post")
	void bulkDeleteByPost(@org.springframework.data.repository.query.Param("post") Post post);

	@org.springframework.data.jpa.repository.Query("SELECT l.post FROM Like l WHERE l.utente.username = :username ORDER BY l.post.idPost DESC")
	java.util.List<Post> findPostsLikedByUsername(@org.springframework.data.repository.query.Param("username") String username);
}
