package it.permessi.rest.permessi.repository;

import it.permessi.rest.permessi.entity.Ruolo;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/** Repository CRUD per Ruolo. */
public interface RuoloRepository extends JpaRepository<Ruolo, Long> {
    Optional<Ruolo> findByNome(String nome);
    Optional<Ruolo> findByAlias(String alias);
    boolean existsByNome(String nome);
}
