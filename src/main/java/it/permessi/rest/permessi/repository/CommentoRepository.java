package it.permessi.rest.permessi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.permessi.rest.permessi.entity.Commento;

public interface CommentoRepository extends JpaRepository <Commento, Long> {

	Optional<Commento> findByIdCommento(Integer idCommento);
	List<Commento> findByUtenteId(Long utenteId);
}
