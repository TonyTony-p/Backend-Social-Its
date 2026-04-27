package it.permessi.rest.permessi.repository;

import it.permessi.rest.permessi.entity.ConsegnaCompito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConsegnaCompitoRepository extends JpaRepository<ConsegnaCompito, Long> {
    List<ConsegnaCompito> findByCompito_Id(Long compitoId);
    Optional<ConsegnaCompito> findByCompito_IdAndStudente_Username(Long compitoId, String username);
    boolean existsByCompito_IdAndStudente_Username(Long compitoId, String username);
}
