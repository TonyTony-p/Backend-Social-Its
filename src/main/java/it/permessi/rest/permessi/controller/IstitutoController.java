package it.permessi.rest.permessi.controller;

import it.permessi.rest.permessi.dto.AggiornaProfessoreRequest;
import it.permessi.rest.permessi.dto.CreaProfessoreRequest;
import it.permessi.rest.permessi.dto.UtenteDto;
import it.permessi.rest.permessi.service.IstitutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/istituto")
public class IstitutoController {

    @Autowired private IstitutoService service;

    @PostMapping("/professori")
    @PreAuthorize("hasAuthority('PROFESSORE_CREATE')")
    public ResponseEntity<UtenteDto> creaProfessore(@Valid @RequestBody CreaProfessoreRequest req) {
        return ResponseEntity.status(201).body(service.creaProfessore(req));
    }

    @GetMapping("/professori")
    @PreAuthorize("hasAuthority('PROFESSORE_CREATE') or hasAuthority('UTENTE_READ')")
    public ResponseEntity<List<UtenteDto>> listaProfessori() {
        return ResponseEntity.ok(service.listaProfessori());
    }

    @PutMapping("/professori/{id}")
    @PreAuthorize("hasAuthority('PROFESSORE_CREATE') or hasAuthority('UTENTE_UPDATE')")
    public ResponseEntity<UtenteDto> aggiornaProfessore(
            @PathVariable Long id,
            @RequestBody AggiornaProfessoreRequest req) {
        req.setId(id);
        return ResponseEntity.ok(service.aggiornaProfessore(id, req));
    }
}
