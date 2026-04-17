package it.permessi.rest.permessi.service;

import it.permessi.rest.permessi.dto.PageResponse;
import it.permessi.rest.permessi.dto.RuoloFormDto;
import it.permessi.rest.permessi.dto.RuoloDto;
import it.permessi.rest.permessi.entity.Ruolo;
import it.permessi.rest.permessi.repository.RuoloRepository;
import it.permessi.rest.permessi.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/** Logica di business per Ruoli (CRUD; join gestite da RuoloPermessoService). */
@Service
public class RuoloService {

    @Autowired private RuoloRepository repo;

    /** Crea ruolo (senza permessi). */
    public RuoloDto create(RuoloFormDto form) {
        Ruolo r = new Ruolo();
        r.setNome(form.getNome());
        r.setAlias(form.getAlias());
        return DtoMapper.toRuoloDtoLight(repo.save(r));
    }

    /** Aggiorna ruolo (nome/alias). */
    public RuoloDto update(RuoloFormDto form) {
        if (form.getId() == null) throw new RuntimeException("Id ruolo obbligatorio per update");
        Ruolo r = repo.findById(form.getId())
                .orElseThrow(() -> new RuntimeException("Ruolo non trovato"));
        r.setNome(form.getNome());
        r.setAlias(form.getAlias());
        return DtoMapper.toRuoloDtoLight(repo.save(r));
    }

    /** Elimina ruolo. */
    public void delete(Long id) { repo.deleteById(id); }

    /** Dettaglio ruolo: include associazioni RuoloPermesso (nested light). */
    public RuoloDto getById(Long id) {
        Ruolo r = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruolo non trovato"));
        return DtoMapper.toRuoloDtoWithAssoc(r);
    }

    /** Lista paginata ruoli (light). */
    public PageResponse<RuoloDto> list(Pageable pageable) {
        return PageResponse.from(repo.findAll(pageable).map(DtoMapper::toRuoloDtoLight));
    }

    /** Lista completa non paginata (light). */
    public List<RuoloDto> listAll() {
        return repo.findAll().stream()
                .map(DtoMapper::toRuoloDtoLight)
                .collect(Collectors.toList());
    }
}
