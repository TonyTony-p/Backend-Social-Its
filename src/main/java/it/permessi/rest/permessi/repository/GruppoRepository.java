package it.permessi.rest.permessi.repository;

import it.permessi.rest.permessi.entity.Gruppo;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repository CRUD per Gruppo. */
public interface GruppoRepository extends JpaRepository<Gruppo, Long> { }
