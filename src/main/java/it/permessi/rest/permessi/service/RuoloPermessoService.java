package it.permessi.rest.permessi.service;

import it.permessi.rest.permessi.dto.PermessoDto;
import it.permessi.rest.permessi.dto.RuoloDto;
import it.permessi.rest.permessi.dto.RuoloPermessoRequest;
import it.permessi.rest.permessi.entity.Permesso;
import it.permessi.rest.permessi.entity.Ruolo;
import it.permessi.rest.permessi.entity.RuoloPermesso;
import it.permessi.rest.permessi.repository.PermessoRepository;
import it.permessi.rest.permessi.repository.RuoloPermessoRepository;
import it.permessi.rest.permessi.repository.RuoloRepository;
import it.permessi.rest.permessi.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Logica di business per l'entità di join RuoloPermesso (associa/dissocia).
 * NOTA: RuoloPermesso ha @Id Long (NON PK composta).
 */
@Service
public class RuoloPermessoService {

    @Autowired private RuoloPermessoRepository rpRepo;
    @Autowired private RuoloRepository ruoloRepo;
    @Autowired private PermessoRepository permRepo;

    /** Ritorna TUTTI i permessi associati ad un ruolo (non paginato) – DTO light. */
    public List<PermessoDto> listPermessiByRuolo(Long ruoloId) {
        return rpRepo.findByRuolo_Id(ruoloId).stream()
                .map(RuoloPermesso::getPermesso)
                .map(DtoMapper::toPermessoDtoLight)
                .collect(Collectors.toList());
    }

    /** Crea l'associazione (ruolo, permesso) se non già presente. */
    public RuoloDto creaAssociazione(RuoloPermessoRequest req) {
        Ruolo ruolo = ruoloRepo.findById(req.getRuoloId())
                .orElseThrow(() -> new RuntimeException("Ruolo non trovato"));
        Permesso perm = permRepo.findById(req.getPermesso().getId())
                .orElseThrow(() -> new RuntimeException("Permesso non trovato"));

        if (!rpRepo.existsByRuolo_IdAndPermesso_Id(ruolo.getId(), perm.getId())) {
            rpRepo.save(new RuoloPermesso(ruolo, perm));
        }
        // Ritorna il ruolo con associazioni aggiornate (nested light)
        return DtoMapper.toRuoloDtoWithAssoc(ruolo);
    }

    /** Elimina l'associazione (ruolo, permesso) se presente. */
    public RuoloDto eliminaAssociazione(RuoloPermessoRequest req) {
        rpRepo.deleteByRuolo_IdAndPermesso_Id(req.getRuoloId(), req.getPermesso().getId());
        Ruolo ruolo = ruoloRepo.findById(req.getRuoloId())
                .orElseThrow(() -> new RuntimeException("Ruolo non trovato"));
        return DtoMapper.toRuoloDtoWithAssoc(ruolo);
    }
}
