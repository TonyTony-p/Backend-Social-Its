package it.permessi.rest.permessi.controller;

import it.permessi.rest.permessi.dto.SondaggioDto;
import it.permessi.rest.permessi.service.SondaggioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sondaggi")
public class SondaggioController {

    @Autowired private SondaggioService sondaggioService;

    @PostMapping("/vota/{idOpzione}")
    public ResponseEntity<SondaggioDto> vota(
            @PathVariable Long idOpzione,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(sondaggioService.vota(idOpzione, userDetails.getUsername()));
    }
}
