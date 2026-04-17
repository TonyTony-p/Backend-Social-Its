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
}
