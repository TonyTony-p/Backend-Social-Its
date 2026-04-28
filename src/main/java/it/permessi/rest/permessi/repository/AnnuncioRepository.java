package it.permessi.rest.permessi.repository;

import it.permessi.rest.permessi.entity.Annuncio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnuncioRepository extends JpaRepository<Annuncio, Long> {
    List<Annuncio> findByClasse_IdOrderByCreatedAtDesc(Long classeId);
}
