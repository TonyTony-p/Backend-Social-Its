package it.permessi.rest.permessi.repository;

import it.permessi.rest.permessi.entity.CommentoAnnuncio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentoAnnuncioRepository extends JpaRepository<CommentoAnnuncio, Long> {
    List<CommentoAnnuncio> findByAnnuncio_IdOrderByCreatedAtAsc(Long annuncioId);
    long countByAnnuncio_Id(Long annuncioId);
}
