package it.permessi.rest.permessi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.permessi.rest.permessi.dto.PostDto;
import it.permessi.rest.permessi.dto.PostFormDto;
import it.permessi.rest.permessi.service.PostService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/post")
public class PostController {

    @Autowired PostService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('POST_CREATE')")
    public ResponseEntity<PostDto> creaPost(
            @RequestPart("contenuto") String contenuto,
            @RequestPart(value = "files", required = false) MultipartFile[] files,
            @RequestPart(value = "sondaggioJson", required = false) String sondaggioJson,
            @AuthenticationPrincipal UserDetails userDetails) {
        var created = service.create(contenuto, files, sondaggioJson, userDetails.getUsername());
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> listaPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails != null ? userDetails.getUsername() : null;
        var posts = service.listAll(username, page, size);
        return ResponseEntity.ok(posts);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('POST_UPDATE')")
    public ResponseEntity<PostDto> update(@RequestBody @Valid PostFormDto form,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        var update = service.update(form, userDetails);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/elimina/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> elimina(@PathVariable Long id,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        service.delete(id, userDetails);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<PostDto>> allPostByUtente(@PathVariable Long id) {
        var posts = service.allPostByUtente(id);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("{id}")
    public ResponseEntity<PostDto> postById(@PathVariable Long id) {
        var post = service.postById(id);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/tendenze")
    public ResponseEntity<List<PostDto>> getTendenze(
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "0") int page) {
        var posts = service.getTendenze(size, page);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/seguiti")
    @PreAuthorize("hasAuthority('POST_READ')")
    public ResponseEntity<List<PostDto>> getPostDaSeguiti(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @AuthenticationPrincipal UserDetails userDetails) {
        var posts = service.getPostDaSeguiti(userDetails.getUsername(), page, size);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/utente/{username}")
    public ResponseEntity<List<PostDto>> getPostByUsername(@PathVariable String username) {
        var posts = service.getPostByUsername(username);
        return ResponseEntity.ok(posts);
    }
}
