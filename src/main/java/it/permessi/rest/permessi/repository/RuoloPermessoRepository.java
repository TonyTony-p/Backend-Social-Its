package it.permessi.rest.permessi.repository;

import it.permessi.rest.permessi.entity.RuoloPermesso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RuoloPermessoRepository extends JpaRepository<RuoloPermesso, Long> {

    List<RuoloPermesso> findByRuolo_Id(Long ruoloId);
    List<RuoloPermesso> findByPermesso_Id(Long permessoId);

    void deleteByRuolo_IdAndPermesso_Id(Long ruoloId, Long permessoId);

    Optional<RuoloPermesso> findByRuolo_IdAndPermesso_Id(Long ruoloId, Long permessoId);

    boolean existsByRuolo_IdAndPermesso_Id(Long ruoloId, Long permessoId);
}
