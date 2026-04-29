package it.permessi.rest.permessi.controller;

import it.permessi.rest.permessi.dto.PostDto;
import it.permessi.rest.permessi.service.SalvataggioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salvati")
public class SalvataggioController {

    @Autowired private SalvataggioService service;

    @PostMapping("/{postId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> salva(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetails userDetails) {
        service.salva(postId, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> rimuovi(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetails userDetails) {
        service.rimuovi(postId, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/miei")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Long>> mieiSalvataggi(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.mieiSalvataggi(userDetails.getUsername()));
    }

    @GetMapping("/miei/posts")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PostDto>> mieiSalvataggiPosts(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.mieiSalvataggiPosts(userDetails.getUsername()));
    }
}
