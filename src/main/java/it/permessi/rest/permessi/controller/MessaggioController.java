package it.permessi.rest.permessi.controller;

import it.permessi.rest.permessi.dto.ConversazioneDto;
import it.permessi.rest.permessi.dto.MessaggioDto;
import it.permessi.rest.permessi.service.ChatSseService;
import it.permessi.rest.permessi.service.MessaggioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/messaggi")
@PreAuthorize("isAuthenticated()")
public class MessaggioController {

    @Autowired private MessaggioService service;
    @Autowired private ChatSseService sseService;

    @GetMapping("/conversazioni")
    public ResponseEntity<List<ConversazioneDto>> getConversazioni(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(service.getConversazioni(user.getUsername()));
    }

    @GetMapping("/conversazioni/{username}")
    public ResponseEntity<ConversazioneDto> getConversazione(
            @PathVariable String username,
            @RequestParam(required = false) Long after,
            @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(service.getOCreaConversazione(user.getUsername(), username, after));
    }

    /** Invia un messaggio; il body può contenere testo e replyToId opzionale. */
    @PostMapping("/conversazioni/{username}")
    public ResponseEntity<MessaggioDto> invia(
            @PathVariable String username,
            @RequestBody Map<String, Object> body,
            @AuthenticationPrincipal UserDetails user) {
        String testo = (String) body.get("testo");
        Long replyToId = body.get("replyToId") != null
                ? Long.parseLong(body.get("replyToId").toString()) : null;
        MessaggioDto dto = service.invia(user.getUsername(), username, testo, replyToId);
        sseService.notifica(username, dto);
        return ResponseEntity.ok(dto);
    }

    /** Stream SSE: il client si iscrive per ricevere nuovi messaggi in push. */
    @GetMapping(value = "/stream/{otherUsername}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream(
            @PathVariable String otherUsername,
            @AuthenticationPrincipal UserDetails user) {
        return sseService.subscribe(user.getUsername());
    }

    @PutMapping("/conversazioni/{username}/letti")
    public ResponseEntity<Void> segnaComeLetti(
            @PathVariable String username, @AuthenticationPrincipal UserDetails user) {
        service.segnaComeLetti(user.getUsername(), username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/nonletti")
    public ResponseEntity<Map<String, Long>> nonLetti(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(Map.of("nonLetti", service.contaTotaleNonLetti(user.getUsername())));
    }

    /** Elimina (soft) un proprio messaggio. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> elimina(
            @PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        service.elimina(id, user.getUsername());
        return ResponseEntity.noContent().build();
    }

    /** Fissa / desfissa un messaggio. Body opzionale: { "durationHours": 24 | 168 | 720 } */
    @PutMapping("/{id}/fissa")
    public ResponseEntity<MessaggioDto> fissa(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, Object> body,
            @AuthenticationPrincipal UserDetails user) {
        Integer durationHours = null;
        if (body != null && body.get("durationHours") != null) {
            durationHours = Integer.parseInt(body.get("durationHours").toString());
        }
        return ResponseEntity.ok(service.toggleFissa(id, user.getUsername(), durationHours));
    }

    /** Segna / desegna come importante. */
    @PutMapping("/{id}/importante")
    public ResponseEntity<MessaggioDto> importante(
            @PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(service.toggleImportante(id, user.getUsername()));
    }
}
