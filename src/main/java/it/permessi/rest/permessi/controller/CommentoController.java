package it.permessi.rest.permessi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.permessi.rest.permessi.dto.CommentoDto;
import it.permessi.rest.permessi.dto.CommentoFormDto;
import it.permessi.rest.permessi.service.CommentoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/commenti")
public class CommentoController {

    @Autowired
    private CommentoService commentoService;

    @PostMapping
    public ResponseEntity<CommentoDto> create(@RequestBody @Valid CommentoFormDto form,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        CommentoDto commento = commentoService.create(form, userDetails.getUsername());
        return ResponseEntity.ok(commento);
    }
    
    @PutMapping("/{idCommento}")
    public ResponseEntity<CommentoDto> update(@PathVariable Integer idCommento,
                                              @RequestBody @Valid CommentoFormDto form,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        CommentoDto commento = commentoService.update(idCommento, form, userDetails.getUsername());
        return ResponseEntity.ok(commento);
    }

    @DeleteMapping("/{idCommento}")
    public ResponseEntity<Void> delete(@PathVariable Integer idCommento,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        commentoService.delete(idCommento, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/miei")
    public ResponseEntity<List<CommentoDto>> listaMieiCommenti(@AuthenticationPrincipal UserDetails userDetails) {
        List<CommentoDto> commenti = commentoService.listaMieiCommenti(userDetails.getUsername());
        return ResponseEntity.ok(commenti);
    }
    
}