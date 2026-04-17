package it.permessi.rest.permessi.controller;

import it.permessi.rest.permessi.dto.PageResponse;
import it.permessi.rest.permessi.dto.GruppoFormDto;
import it.permessi.rest.permessi.dto.GruppoPermessiRequest;
import it.permessi.rest.permessi.dto.IdRequest;
import it.permessi.rest.permessi.dto.GruppoDto;
import it.permessi.rest.permessi.service.GruppoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** API REST per Gruppi. */
@RestController
@RequestMapping("/api/gruppi")
public class GruppoController {

    @Autowired private GruppoService service;

    /** Crea gruppo. */
    @PostMapping
    @PreAuthorize("hasAuthority('GRUPPO_CREATE')")
    public GruppoDto create(@Valid @RequestBody GruppoFormDto form) {
        return service.create(form);
    }

    /** Aggiorna gruppo (id nel body). */
    @PutMapping
    @PreAuthorize("hasAuthority('GRUPPO_UPDATE')")
    public GruppoDto update(@Valid @RequestBody GruppoFormDto form) {
        return service.update(form);
    }

    /** Elimina gruppo (id nel body). */
    @DeleteMapping
    @PreAuthorize("hasAuthority('GRUPPO_DELETE')")
    public void delete(@Valid @RequestBody IdRequest req) {
        service.delete(req.getId());
    }

    /** Dettaglio gruppo (withPermessi=true include i permessi). */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('GRUPPO_READ')")
    public GruppoDto get(@PathVariable Long id,
                         @RequestParam(defaultValue = "false") boolean withPermessi) {
        return service.getById(id, withPermessi);
    }

    /** Lista paginata gruppi. */
    @GetMapping
    @PreAuthorize("hasAuthority('GRUPPO_READ')")
    public PageResponse<GruppoDto> list(Pageable pageable,
                                        @RequestParam(defaultValue = "false") boolean withPermessi) {
        return service.list(pageable, withPermessi);
    }

    /** Lista completa (non paginata). */
    @GetMapping("/tutti")
    @PreAuthorize("hasAuthority('GRUPPO_READ')")
    public List<GruppoDto> listAll(@RequestParam(defaultValue = "false") boolean withPermessi) {
        return service.listAll(withPermessi);
    }

    /** Associa permessi ad un gruppo. */
    @PostMapping("/associa-permessi")
    @PreAuthorize("hasAuthority('GRUPPO_UPDATE')")
    public GruppoDto associaPermessi(@Valid @RequestBody GruppoPermessiRequest req) {
        return service.associaPermessi(req);
    }

    /** Dissocia permessi da un gruppo. */
    @PostMapping("/dissocia-permessi")
    @PreAuthorize("hasAuthority('GRUPPO_UPDATE')")
    public GruppoDto dissociaPermessi(@Valid @RequestBody GruppoPermessiRequest req) {
        return service.dissociaPermessi(req);
    }
}
