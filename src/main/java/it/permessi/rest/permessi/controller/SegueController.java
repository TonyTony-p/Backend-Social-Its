package it.permessi.rest.permessi.controller;

import it.permessi.rest.permessi.service.SegueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
}
