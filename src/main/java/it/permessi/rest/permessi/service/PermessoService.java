package it.permessi.rest.permessi.service;

import it.permessi.rest.permessi.dto.PageResponse;
import it.permessi.rest.permessi.dto.PermessoFormDto;
import it.permessi.rest.permessi.dto.PermessoDto;
import it.permessi.rest.permessi.entity.Gruppo;
import it.permessi.rest.permessi.entity.Permesso;
import it.permessi.rest.permessi.repository.GruppoRepository;
import it.permessi.rest.permessi.repository.PermessoRepository;
import it.permessi.rest.permessi.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/** Logica di business per Permessi. */
@Service
public class PermessoService {

    @Autowired private PermessoRepository repo;
    @Autowired private GruppoRepository gruppoRepo;

    /** Crea permesso (gruppo opzionale). */
    public PermessoDto create(PermessoFormDto form) {
        Permesso p = new Permesso();
        p.setNome(form.getNome());
        p.setAlias(form.getAlias());
        if (form.getGruppo() != null && form.getGruppo().getId() != null) {
            Gruppo g = gruppoRepo.findById(form.getGruppo().getId())
                    .orElseThrow(() -> new RuntimeException("Gruppo non trovato"));
            p.setGruppo(g);
        }
        return DtoMapper.toPermessoDtoLight(repo.save(p));
    }

    /** Aggiorna permesso (nome, alias, gruppo). */
    public PermessoDto update(PermessoFormDto form) {
        if (form.getId() == null) throw new RuntimeException("Id permesso obbligatorio per update");
        Permesso p = repo.findById(form.getId())
                .orElseThrow(() -> new RuntimeException("Permesso non trovato"));
        p.setNome(form.getNome());
        p.setAlias(form.getAlias());
        if (form.getGruppo() != null) {
            if (form.getGruppo().getId() == null) {
                p.setGruppo(null);
            } else {
                Gruppo g = gruppoRepo.findById(form.getGruppo().getId())
                        .orElseThrow(() -> new RuntimeException("Gruppo non trovato"));
                p.setGruppo(g);
            }
        }
        return DtoMapper.toPermessoDtoLight(repo.save(p));
    }

    /** Elimina permesso. */
    public void delete(Long id) { repo.deleteById(id); }

    /** Dettaglio permesso (light; se vuoi anche le associazioni usa toPermessoDtoWithAssoc). */
    public PermessoDto getById(Long id) {
        return repo.findById(id)
                .map(DtoMapper::toPermessoDtoLight)
                .orElseThrow(() -> new RuntimeException("Permesso non trovato"));
    }

    /** Lista paginata (light). */
    public PageResponse<PermessoDto> list(Pageable pageable) {
        return PageResponse.from(repo.findAll(pageable).map(DtoMapper::toPermessoDtoLight));
    }

    /** Lista completa non paginata (light). */
    public List<PermessoDto> listAll() {
        return repo.findAll().stream()
                .map(DtoMapper::toPermessoDtoLight)
                .collect(Collectors.toList());
    }
}
