package it.permessi.rest.permessi.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.permessi.rest.permessi.dto.CommentoDto;
import it.permessi.rest.permessi.dto.CommentoFormDto;
import it.permessi.rest.permessi.entity.Commento;
import it.permessi.rest.permessi.entity.Post;
import it.permessi.rest.permessi.entity.Utente;
import it.permessi.rest.permessi.mapper.DtoMapper;
import it.permessi.rest.permessi.repository.CommentoRepository;
import it.permessi.rest.permessi.repository.PostRepository;
import it.permessi.rest.permessi.repository.UtenteRepository;

@Service
public class CommentoService {

	@Autowired PostRepository postRepo;
	@Autowired UtenteRepository utenteRepo;
	@Autowired CommentoRepository commentoRepo;
	
	@Transactional
    public CommentoDto create(CommentoFormDto form, String username) {
        // Recupera l'utente dall'username (dal token)
        Utente utente = utenteRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato con username: " + username));

        // Verifica che il post esista
        Post post = postRepo.findById(form.getIdPost())
                .orElseThrow(() -> new RuntimeException("Post non trovato con ID: " + form.getIdPost()));

        // Crea il commento
        Commento commento = new Commento();
        commento.setTesto(form.getTesto());
        commento.setUtente(utente);
        commento.setPost(post);
        commento.setDataOra(LocalDateTime.now());

        Commento savedCommento = commentoRepo.save(commento);
        return DtoMapper.toCommentoDtoLight(savedCommento);
    }
	
	@Transactional
	public CommentoDto update(Integer idCommento, CommentoFormDto form, String username) {
	    // Recupera il commento
	    Commento commento = commentoRepo.findByIdCommento(idCommento)
	        .orElseThrow(() -> new RuntimeException("Commento non trovato con ID: " + idCommento));
	    
	    // Recupera l'utente che sta tentando di modificare
	    Utente utente = utenteRepo.findByUsername(username)
	        .orElseThrow(() -> new RuntimeException("Utente non trovato con username: " + username));
	    
	    // Verifica i permessi//da cambiare con prehautorized
	    boolean isAdmin = utente.getRuolo() != null && 
	                     utente.getRuolo().toString().equals("ADMIN");
	    boolean isProprietarioCommento = commento.getUtente().getId().equals(utente.getId());
	    boolean isProprietarioPost = commento.getPost().getUtente().equals(utente.getId());
	    
	    if (!isAdmin && !isProprietarioCommento && !isProprietarioPost) {
	        throw new RuntimeException("Non hai i permessi per modificare questo commento");
	    }
	    
	    // Aggiorna il commento
	    commento.setTesto(form.getTesto());
	    
	    Commento updatedCommento = commentoRepo.save(commento);
	    return DtoMapper.toCommentoDtoLight(updatedCommento);
	}

	
	//da cambiare e gestire i permessi con preautorized//rocprdarsi di cambiare i diagrammi
	@Transactional
	public void delete(Integer idCommento, String username) {
	    // Recupera il commento
	    Commento commento = commentoRepo.findByIdCommento(idCommento)
	        .orElseThrow(() -> new RuntimeException("Commento non trovato con ID: " + idCommento));
	    
	    // Recupera l'utente che sta tentando di eliminare
	    Utente utente = utenteRepo.findByUsername(username)
	        .orElseThrow(() -> new RuntimeException("Utente non trovato con username: " + username));
	    
	    // Verifica i permessi
	    boolean isAdmin = utente.getRuolo() != null && 
	                     utente.getRuolo().toString().equals("ADMIN");
	    boolean isProprietarioCommento = commento.getUtente().getId().equals(utente.getId());
	    boolean isProprietarioPost = commento.getPost().getUtente().equals(utente.getId());
	    
	    if (!isAdmin && !isProprietarioCommento && !isProprietarioPost) {
	        throw new RuntimeException("Non hai i permessi per eliminare questo commento");
	    }
	    
	    // Elimina il commento
	    commentoRepo.delete(commento);
	}
	
	public List<CommentoDto> listaMieiCommenti(String username) {//gestire con pageable?
	    // Recupera l'utente dall'email (dal token)
	    Utente utente = utenteRepo.findByUsername(username)
	        .orElseThrow(() -> new RuntimeException("Utente non trovato con username: " + username));
	    
	    // Recupera tutti i commenti dell'utente
	    List<Commento> commenti = commentoRepo.findByUtenteId(utente.getId());
	    
	    // Converte in DTO
	    return commenti.stream()
	        .map(DtoMapper::toCommentoDtoLight)
	        .collect(Collectors.toList());
	}
	
	//getCommentiByIdPost
	//cercaCommentoById
	//CercaCommentoByIdUtente
}
