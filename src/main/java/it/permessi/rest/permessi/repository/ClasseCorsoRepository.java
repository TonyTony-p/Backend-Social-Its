package it.permessi.rest.permessi.repository;

import it.permessi.rest.permessi.entity.ClasseCorso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClasseCorsoRepository extends JpaRepository<ClasseCorso, Long> {
    List<ClasseCorso> findByProfessore_Username(String username);
    Page<ClasseCorso> findByTipo(ClasseCorso.TipoClasse tipo, Pageable pageable);
    Optional<ClasseCorso> findByCodiceInvito(String codiceInvito);

    @Query("SELECT c FROM ClasseCorso c WHERE c.tipo = 'PUBBLICA' ORDER BY (SELECT COUNT(i) FROM IscrizioneClasse i WHERE i.classe = c AND i.stato = 'APPROVATA') DESC")
    List<ClasseCorso> findTopClassiPubbliche(Pageable pageable);

    long countByIstituto_Id(Long istitutoId);
}
