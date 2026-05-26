package it.permessi.rest.permessi.controller;

import it.permessi.rest.permessi.dto.ProfiloDto;
import it.permessi.rest.permessi.service.SegueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/segui")
public class SegueController {

    @Autowired private SegueService segueService;

    @PostMapping("/{username}")
    @PreAuthorize("hasAuthority('SEGUE_CREATE')")
    public ResponseEntity<Void> segui(
            @PathVariable String username,
            @AuthenticationPrincipal UserDetails userDetails) {
        segueService.segui(userDetails.getUsername(), username);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasAuthority('SEGUE_DELETE')")
    public ResponseEntity<Void> smettiDiSeguire(
            @PathVariable String username,
            @AuthenticationPrincipal UserDetails userDetails) {
        segueService.smettiDiSeguire(userDetails.getUsername(), username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{username}/seguaci")
    public ResponseEntity<List<ProfiloDto>> getSeguaci(@PathVariable String username) {
        return ResponseEntity.ok(segueService.getSeguaci(username));
    }

    @GetMapping("/{username}/seguiti")
    public ResponseEntity<List<ProfiloDto>> getSeguiti(@PathVariable String username) {
        return ResponseEntity.ok(segueService.getSeguiti(username));
    }
}
