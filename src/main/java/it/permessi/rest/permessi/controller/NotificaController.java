package it.permessi.rest.permessi.controller;

import it.permessi.rest.permessi.dto.NotificaDto;
import it.permessi.rest.permessi.service.NotificaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifiche")
public class NotificaController {

    @Autowired private NotificaService service;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<NotificaDto>> getNotifiche(
            @AuthenticationPrincipal UserDetails user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(service.getNotifiche(user.getUsername(), page, size));
    }

    @GetMapping("/contatore")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, Long>> contatore(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(Map.of("nonLette", service.contaNonLette(user.getUsername())));
    }

    @PutMapping("/{id}/letta")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> segnaComeLetta(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails user) {
        service.segnaComeLetta(id, user.getUsername());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/lette-tutte")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> segnaComeLetteTutte(@AuthenticationPrincipal UserDetails user) {
        service.segnaComeLetteTutte(user.getUsername());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> elimina(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails user) {
        service.elimina(id, user.getUsername());
        return ResponseEntity.noContent().build();
    }
}
