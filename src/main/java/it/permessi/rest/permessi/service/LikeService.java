package it.permessi.rest.permessi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.permessi.rest.permessi.dto.LikeDto;
import it.permessi.rest.permessi.dto.LikeFormDto;
import it.permessi.rest.permessi.dto.PostDto;
import it.permessi.rest.permessi.entity.Like;
import it.permessi.rest.permessi.entity.Post;
import it.permessi.rest.permessi.entity.Utente;
import it.permessi.rest.permessi.mapper.DtoMapper;
import it.permessi.rest.permessi.repository.LikeRepository;
import it.permessi.rest.permessi.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;
import it.permessi.rest.permessi.repository.UtenteRepository;

@Service

public class LikeService {
	
    @Autowired
    private LikeRepository likeRepo;

    @Autowired
    private UtenteRepository utenteRepo;

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private NotificaService notificaService;

    public LikeDto create(LikeFormDto form, String username) {
        
        // Usa direttamente l'username passato dal controller
        
        Utente utente = utenteRepo.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Utente non trovato con username: " + username));

        // Verifica che il post esista
        Post post = postRepo.findById(form.getIdPost())
            .orElseThrow(() -> new RuntimeException("Post non trovato con ID: " + form.getIdPost()));

        // Verifica che non esista già un like
        boolean likeEsistente = likeRepo.existsByUtenteAndPost(utente, post);
        if (likeEsistente) {
            throw new RuntimeException("Hai già messo like a questo post");
        }

        // Crea e salva il like
        Like like = new Like(utente, post);
        LikeDto dto = DtoMapper.toLikeDtoLight(likeRepo.save(like));

        notificaService.crea(
            post.getUtente().getUsername(), "LIKE",
            utente.getUsername(), utente.getNome() + " " + utente.getCognome(),
            post.getIdPost(), "POST",
            utente.getNome() + " ha messo ★ al tuo post"
        );
        return dto;
    }
    
    //mostra solo i miei like
    public Page<LikeDto> getLikesByUser(String username, Pageable pageable) {
        
        Utente utente = utenteRepo.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Utente non trovato con username: " + username));

        Page<Like> likesPage = likeRepo.findByUtente(utente, pageable);

    
        return likesPage.map(like -> DtoMapper.toLikeDtoLight(like));
    }
    
    public Page<LikeDto> getAllLikesByPost(Long postId, Pageable pageable) {
        // Verifica che il post esista
        Post post = postRepo.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post non trovato con ID: " + postId));

        // Recupera tutti i like del post
        Page<Like> likesPage = likeRepo.findByPost(post, pageable);

        return likesPage.map(like -> DtoMapper.toLikeDtoLight(like));
    }
    
    public List<PostDto> getLikedPostsByUsername(String username) {
        return likeRepo.findPostsLikedByUsername(username).stream()
                .map(DtoMapper::toPostDtoLight)
                .collect(Collectors.toList());
    }

    //rimuovi like
    
    public void rimuovi(LikeFormDto form, String username) {
        // Recupera l'utente dall'username (dall'autenticazione)
        Utente utente = utenteRepo.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Utente non trovato con username: " + username));

        // Verifica che il post esista
        Post post = postRepo.findById(form.getIdPost())
            .orElseThrow(() -> new RuntimeException("Post non trovato con ID: " + form.getIdPost()));

        // Cerca il like esistente - SOLO per questo utente e post
        // Questo garantisce che solo l'utente che ha messo il like può rimuoverlo
        Like like = likeRepo.findByUtenteAndPost(utente, post)
            .orElseThrow(() -> new RuntimeException("Non hai messo like a questo post"));

        // Elimina il like
        likeRepo.delete(like);
    }
}
