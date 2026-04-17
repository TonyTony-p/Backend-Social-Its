package it.permessi.rest.permessi.repository;

import it.permessi.rest.permessi.entity.Permesso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/** Repository CRUD per Permesso. */
public interface PermessoRepository extends JpaRepository<Permesso, Long> {
    List<Permesso> findByGruppo_Id(Long gruppoId);
}
