package it.permessi.rest.permessi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.permessi.rest.permessi.dto.LikeDto;
import it.permessi.rest.permessi.dto.LikeFormDto;
import it.permessi.rest.permessi.dto.PostDto;
import it.permessi.rest.permessi.service.LikeService;
import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/likes")

public class LikeController {

	@Autowired LikeService service;
	
	@PostMapping
	@PreAuthorize("hasAuthority('LIKE_CREATE')")
	public ResponseEntity<LikeDto> creaLike(@Valid @RequestBody LikeFormDto form,
	                                       @AuthenticationPrincipal UserDetails userDetails) {
	    LikeDto created = service.create(form, userDetails.getUsername());
	    return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}
	

	@GetMapping("/miei")
	@PreAuthorize("hasAuthority('LIKE_READ')")
	public ResponseEntity<Page<LikeDto>> getLikesByUser(
	        @AuthenticationPrincipal UserDetails userDetails,
	        Pageable pageable) {  
		Page<LikeDto> likes = service.getLikesByUser(userDetails.getUsername(), pageable);
	    return ResponseEntity.ok(likes);
	}
	
    @GetMapping("/post/{postId}")
    public ResponseEntity<Page<LikeDto>> getLikesByPost(@PathVariable Long postId, 
                                                          Pageable pageable) {
        Page<LikeDto> likes = service.getAllLikesByPost(postId, pageable);
        return ResponseEntity.ok(likes);
    }
	
	
    @GetMapping("/utente/{username}")
    public ResponseEntity<List<PostDto>> getLikedPostsByUsername(@PathVariable String username) {
        return ResponseEntity.ok(service.getLikedPostsByUsername(username));
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('LIKE_DELETE')")
    public ResponseEntity<Void> rimuoviLike(@Valid @RequestBody LikeFormDto form,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        service.rimuovi(form, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
