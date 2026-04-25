package it.permessi.rest.permessi.controller;

import it.permessi.rest.permessi.dto.PageResponse;
import it.permessi.rest.permessi.dto.IdRequest;
import it.permessi.rest.permessi.dto.RuoloFormDto;
import it.permessi.rest.permessi.dto.RuoloDto;
import it.permessi.rest.permessi.service.RuoloService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** API REST per Ruoli (CRUD; associazioni gestite in RuoloPermessoController). */
@RestController
@RequestMapping("/api/ruoli")
public class RuoloController {

    @Autowired private RuoloService service;

    /** Crea ruolo. */
    @PostMapping
    @PreAuthorize("hasAuthority('RUOLO_CREATE')")
    public RuoloDto create(@Valid @RequestBody RuoloFormDto form) {
        return service.create(form);
    }

    /** Aggiorna ruolo (id nel body). */
    @PutMapping
    @PreAuthorize("hasAuthority('RUOLO_UPDATE')")
    public RuoloDto update(@Valid @RequestBody RuoloFormDto form) {
        return service.update(form);
    }

    /** Elimina ruolo (id nel body). */
    @DeleteMapping
    @PreAuthorize("hasAuthority('RUOLO_DELETE')")
    public void delete(@Valid @RequestBody IdRequest req) {
        service.delete(req.getId());
    }

    /** Dettaglio ruolo. */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('RUOLO_READ')")
    public RuoloDto get(@PathVariable Long id) {
        return service.getById(id);
    }

    /** Lista paginata ruoli. */
    @GetMapping
    @PreAuthorize("hasAuthority('RUOLO_READ')")
    public PageResponse<RuoloDto> list(Pageable pageable) {
        return service.list(pageable);
    }

    /** Lista completa (non paginata). */
    @GetMapping("/tutti")
    @PreAuthorize("hasAuthority('RUOLO_READ')")
    public List<RuoloDto> listAll() {
        return service.listAll();
    }
}
