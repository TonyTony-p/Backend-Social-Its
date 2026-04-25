package it.permessi.rest.permessi.repository;

import it.permessi.rest.permessi.entity.OpzioneSondaggio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpzioneRepository extends JpaRepository<OpzioneSondaggio, Long> {
}
