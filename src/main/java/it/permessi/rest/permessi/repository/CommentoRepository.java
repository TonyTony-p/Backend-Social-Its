package it.permessi.rest.permessi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.permessi.rest.permessi.entity.Commento;
import it.permessi.rest.permessi.entity.Post;

public interface CommentoRepository extends JpaRepository <Commento, Long> {

	Optional<Commento> findByIdCommento(Integer idCommento);
	List<Commento> findByUtenteId(Long utenteId);

	@org.springframework.data.jpa.repository.Modifying
	@org.springframework.data.jpa.repository.Query("DELETE FROM Commento c WHERE c.post = :post")
	void bulkDeleteByPost(@org.springframework.data.repository.query.Param("post") Post post);
}
