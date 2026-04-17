package it.permessi.rest.permessi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.permessi.rest.permessi.dto.PostDto;
import it.permessi.rest.permessi.dto.PostFormDto;
import it.permessi.rest.permessi.entity.Post;
import it.permessi.rest.permessi.entity.Utente;
import it.permessi.rest.permessi.mapper.DtoMapper;
import it.permessi.rest.permessi.repository.PostRepository;
import it.permessi.rest.permessi.repository.UtenteRepository;
import jakarta.persistence.EntityNotFoundException;



//da aggiungere i pageable

@Service
public class PostService {
	
	@Autowired PostRepository postRepo;
	@Autowired UtenteRepository utenteRepo;

	public PostDto create(PostFormDto dto, String username) {
	    
	    // Recupera l'utente tramite username (email) dal token
	    Utente u = utenteRepo.findByUsername(username)
	            .orElseThrow(() -> new IllegalArgumentException("Utente non trovato: " + username));
	    
	    Post p = new Post();
	    p.setContenuto(dto.getContenuto());
	    p.setUtente(u);
	    
	    Post savedPost = postRepo.save(p);
	    
	    return DtoMapper.toPostDtoLight(savedPost);
	}
	
	
	public List<PostDto> listAll() {//da aggiungere pageable
	    List<Post> posts = postRepo.findAll();
	    
	    return posts.stream()
	            .map(DtoMapper::toPostDtoComplete)
	            .collect(Collectors.toList());
	}
	
	@Transactional
	public PostDto update(PostFormDto form, UserDetails userDetails) {
	    // Validazione input
	    if (form.getId() == null) {
	        throw new IllegalArgumentException("Id post obbligatorio per update");
	    }

	    if (form.getContenuto() != null && form.getContenuto().trim().length() > 1000) {
	        throw new IllegalArgumentException("Il contenuto non può superare 1000 caratteri");
	    }

	    // Verifica esistenza post e proprietà
	    Post existingPost = postRepo.findById(form.getId())
	        .orElseThrow(() -> new EntityNotFoundException("Post non trovato con id: " + form.getId()));

	    // Verifica che l'utente sia il proprietario del post
	    if (!existingPost.getUtente().getUsername().equals(userDetails.getUsername())) {
	        throw new SecurityException("Non sei autorizzato a modificare questo post");
	    }

	    // Aggiornamento
	    if (form.getContenuto() != null && !form.getContenuto().trim().isEmpty()) {
	        existingPost.setContenuto(form.getContenuto().trim());
	    }

	    Post updatedPost = postRepo.save(existingPost);
	    return DtoMapper.toPostDtoLight(updatedPost);
	}

	@Transactional
	public void delete(Long id, UserDetails userDetails) {
	    Post post = postRepo.findById(id)
	        .orElseThrow(() -> new EntityNotFoundException("Post non trovato con id: " + id));

	    // Verifica che l'utente sia il proprietario del post
	    if (!post.getUtente().getUsername().equals(userDetails.getUsername())) {
	        throw new SecurityException("Non sei autorizzato a eliminare questo post");
	    }

	    postRepo.deleteById(id);
	}
	


	@Transactional(readOnly = true)
	public List<PostDto> getTendenze(int limit) {
	    // Validazione del limite
	    if (limit <= 0 || limit > 60) {
	        throw new IllegalArgumentException("Il limite deve essere tra 1 e 60");
	    }
	
	    // Recupera i post ordinati per numero di like
	    Pageable pageable = PageRequest.of(0, limit);
	    List<Post> posts = postRepo.findPostsOrderByLikesDesc(pageable);
	
	    // USA IL NUOVO MAPPER PER LE TENDENZE
	    return posts.stream()
	        .map(DtoMapper::toPostDtoForTendenze) // CAMBIATO QUI!
	        .collect(Collectors.toList());
	}
	//aggiungere miei post, che restituisce la lista di post dell utente loggato
	//aggiungere service delle tendenze
	
	

    @Transactional
    public List<PostDto> allPostByUtente(Long idUtente) {//da aggiungere pageable?
        
        if (!utenteRepo.existsById(idUtente)) {
            throw new EntityNotFoundException("Utente non trovato con id: " + idUtente);
        }
        
        // Recupera tutti i post dell'utente
        List<Post> posts = postRepo.findByUtenteId(idUtente);
        
        return posts.stream()
            .map(DtoMapper::toPostDtoComplete)
            .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public PostDto postById(Long idPost) {
        Post post = postRepo.findById(idPost)
            .orElseThrow(() -> new EntityNotFoundException("Post non trovato con id: " + idPost));
        
        return DtoMapper.toPostDtoComplete(post);
    }


}
