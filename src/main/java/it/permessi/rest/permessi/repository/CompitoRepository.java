package it.permessi.rest.permessi.repository;

import it.permessi.rest.permessi.entity.Compito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompitoRepository extends JpaRepository<Compito, Long> {
    List<Compito> findByClasse_IdOrderByCreatedAtDesc(Long classeId);
}
