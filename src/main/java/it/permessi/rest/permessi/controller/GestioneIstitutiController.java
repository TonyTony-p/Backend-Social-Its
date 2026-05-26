package it.permessi.rest.permessi.controller;

import it.permessi.rest.permessi.dto.IstitutoDto;
import it.permessi.rest.permessi.dto.IstitutoFormDto;
import it.permessi.rest.permessi.service.GestioneIstitutiService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/istituti")
public class GestioneIstitutiController {

    @Autowired private GestioneIstitutiService service;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<IstitutoDto>> lista() {
        return ResponseEntity.ok(service.listaTutti());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<IstitutoDto> dettaglio(@PathVariable Long id) {
        return ResponseEntity.ok(service.dettaglio(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<IstitutoDto> crea(@Valid @RequestBody IstitutoFormDto form) {
        return ResponseEntity.status(201).body(service.crea(form));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<IstitutoDto> aggiorna(
            @PathVariable Long id,
            @Valid @RequestBody IstitutoFormDto form) {
        return ResponseEntity.ok(service.aggiorna(id, form));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> elimina(@PathVariable Long id) {
        service.elimina(id);
        return ResponseEntity.noContent().build();
    }
}
