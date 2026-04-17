package it.permessi.rest.permessi.controller;

import it.permessi.rest.permessi.dto.PageResponse;
import it.permessi.rest.permessi.dto.IdRequest;
import it.permessi.rest.permessi.dto.PermessoFormDto;
import it.permessi.rest.permessi.dto.PermessoDto;
import it.permessi.rest.permessi.service.PermessoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** API REST per Permessi. */
@RestController
@RequestMapping("/api/permessi")
public class PermessoController {

    @Autowired private PermessoService service;

    /** Crea permesso. */
    @PostMapping
    @PreAuthorize("hasAuthority('PERMESSO_CREATE')")
    public PermessoDto create(@Valid @RequestBody PermessoFormDto form) {
        return service.create(form);
    }

    /** Aggiorna permesso (id nel body). */
    @PutMapping
    @PreAuthorize("hasAuthority('PERMESSO_UPDATE')")
    public PermessoDto update(@Valid @RequestBody PermessoFormDto form) {
        return service.update(form);
    }

    /** Elimina permesso (id nel body). */
    @DeleteMapping
    @PreAuthorize("hasAuthority('PERMESSO_DELETE')")
    public void delete(@Valid @RequestBody IdRequest req) {
        service.delete(req.getId());
    }

    /** Dettaglio permesso. */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PERMESSO_READ')")
    public PermessoDto get(@PathVariable Long id) {
        return service.getById(id);
    }

    /** Lista paginata permessi. */
    @GetMapping
    @PreAuthorize("hasAuthority('PERMESSO_READ')")
    public PageResponse<PermessoDto> list(Pageable pageable) {
        return service.list(pageable);
    }

    /** Lista completa (non paginata). */
    @GetMapping("/tutti")
    @PreAuthorize("hasAuthority('PERMESSO_READ')")
    public List<PermessoDto> listAll() {
        return service.listAll();
    }
}
