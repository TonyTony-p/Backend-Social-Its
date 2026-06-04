package it.permessi.rest.permessi.controller;

import it.permessi.rest.permessi.dto.SegnalazioneDto;
import it.permessi.rest.permessi.dto.SegnalazioneFormDto;
import it.permessi.rest.permessi.service.SegnalazioneService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4201", "http://localhost:3000"})
@RequestMapping("/api/segnalazioni")
public class SegnalazioneController {

    @Autowired private SegnalazioneService service;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> segnala(
            @RequestBody SegnalazioneFormDto form,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            SegnalazioneDto dto = service.segnala(form, userDetails);
            return ResponseEntity.status(201).body(dto);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
