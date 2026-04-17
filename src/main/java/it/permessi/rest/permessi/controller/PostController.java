package it.permessi.rest.permessi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.RestController;

import it.permessi.rest.permessi.dto.PostDto;
import it.permessi.rest.permessi.dto.PostFormDto;
import it.permessi.rest.permessi.service.PostService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/post")
public class PostController {

	
	@Autowired PostService service;
	
	
	@PostMapping
	public ResponseEntity<PostDto> creaPost(
	        @Valid @RequestBody PostFormDto form,
	        @AuthenticationPrincipal UserDetails userDetails) {
	    
	    var created = service.create(form, userDetails.getUsername());
	    return ResponseEntity.status(201).body(created);
	}
	
	@GetMapping
	public ResponseEntity<List<PostDto>> listaPosts() {
	    var posts = service.listAll();
	    return ResponseEntity.ok(posts);
	}
	
	@PutMapping
	public ResponseEntity<PostDto> update(@RequestBody @Valid PostFormDto form, 
	                                     @AuthenticationPrincipal UserDetails userDetails) {
	    var update = service.update(form, userDetails);
	    return ResponseEntity.ok(update);
	}

	@DeleteMapping("/elimina/{id}")
	public ResponseEntity<Void> elimina(@PathVariable Long id, 
	                                   @AuthenticationPrincipal UserDetails userDetails) {
	    service.delete(id, userDetails);
	    return ResponseEntity.noContent().build();
	}
	
	//aggiungere rotta miei post, li prende dal token
    
    @GetMapping("/all/{id}")
    public ResponseEntity<List<PostDto>> allPostByUtente(@PathVariable Long id){
    	var posts = service.allPostByUtente(id);
    	return ResponseEntity.ok(posts);
    }
    
    @GetMapping("{id}")
    public ResponseEntity<PostDto> postById(@PathVariable Long id){
    	var post = service.postById(id);
    	return ResponseEntity.ok(post);
    }
    
    
    @GetMapping("/tendenze")
    public ResponseEntity<List<PostDto>> getTendenze(
            @RequestParam(defaultValue = "10") int limit) {
        var posts = service.getTendenze(limit);
        return ResponseEntity.ok(posts);
    }
}
