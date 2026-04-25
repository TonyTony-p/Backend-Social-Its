package it.permessi.rest.permessi.repository;

import it.permessi.rest.permessi.entity.MaterialeClasse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialeClasseRepository extends JpaRepository<MaterialeClasse, Long> {
    List<MaterialeClasse> findByClasse_IdOrderByDataCaricamentoDesc(Long classeId);
}
