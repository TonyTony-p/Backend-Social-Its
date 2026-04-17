package it.permessi.rest.permessi.controller;

import it.permessi.rest.permessi.dto.RuoloPermessoRequest;
import it.permessi.rest.permessi.dto.PermessoDto;
import it.permessi.rest.permessi.dto.RuoloDto;
import it.permessi.rest.permessi.service.RuoloPermessoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * API REST per entità di join RuoloPermesso:
 * - create = associa permesso a ruolo;
 * - delete = dissocia permesso da ruolo;
 * - read lista permessi di un ruolo (non paginata).
 */
@RestController
@RequestMapping("/api/ruoli-permessi")
public class RuoloPermessoController {

    @Autowired private RuoloPermessoService service;

    /** Restituisce tutti i permessi di un ruolo (non paginato). */
    @GetMapping("/ruolo/{ruoloId}")
    @PreAuthorize("hasAuthority('RUOLO_READ')")
    public List<PermessoDto> listByRuolo(@PathVariable Long ruoloId) {
        return service.listPermessiByRuolo(ruoloId);
    }

    /** Crea l'associazione ruolo-permesso. */
    @PostMapping
    @PreAuthorize("hasAuthority('RUOLO_UPDATE')")
    public RuoloDto create(@Valid @RequestBody RuoloPermessoRequest req) {
        return service.creaAssociazione(req);
    }

    /** Elimina l'associazione ruolo-permesso. */
    @DeleteMapping
    @PreAuthorize("hasAuthority('RUOLO_UPDATE')")
    public RuoloDto delete(@Valid @RequestBody RuoloPermessoRequest req) {
        return service.eliminaAssociazione(req);
    }
}
