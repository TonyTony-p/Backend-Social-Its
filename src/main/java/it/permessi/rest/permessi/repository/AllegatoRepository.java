package it.permessi.rest.permessi.repository;

import it.permessi.rest.permessi.entity.Allegato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllegatoRepository extends JpaRepository<Allegato, Long> {}
