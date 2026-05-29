package it.permessi.rest.permessi.service;

import it.permessi.rest.permessi.dto.PostDto;
import it.permessi.rest.permessi.entity.Post;
import it.permessi.rest.permessi.entity.PostSalvato;
import it.permessi.rest.permessi.entity.Utente;
import it.permessi.rest.permessi.mapper.DtoMapper;
import it.permessi.rest.permessi.repository.PostRepository;
import it.permessi.rest.permessi.repository.PostSalvatoRepository;
import it.permessi.rest.permessi.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalvataggioService {

    @Autowired private PostSalvatoRepository repo;
    @Autowired private UtenteRepository utenteRepo;
    @Autowired private PostRepository postRepo;

    public void salva(Long postId, String username) {
        Utente utente = utenteRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        Post post = postRepo.findById(postId != null ? postId : 0L)
                .orElseThrow(() -> new RuntimeException("Post non trovato"));
        if (repo.existsByUtenteAndPost(utente, post)) return;
        repo.save(new PostSalvato(utente, post));
    }

    public void rimuovi(Long postId, String username) {
        Utente utente = utenteRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        Post post = postRepo.findById(postId != null ? postId : 0L)
                .orElseThrow(() -> new RuntimeException("Post non trovato"));
        repo.findByUtenteAndPost(utente, post).ifPresent(repo::delete);
    }

    public List<Long> mieiSalvataggi(String username) {
        Utente utente = utenteRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        return repo.findByUtente(utente).stream()
                .map(s -> s.getPost().getIdPost() != null ? s.getPost().getIdPost() : 0L)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostDto> mieiSalvataggiPosts(String username) {
        Utente utente = utenteRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        return repo.findByUtenteOrderBySalvatoAtDesc(utente).stream()
                .filter(s -> s.getPost() != null)
                .map(s -> {
                    try {
                        return DtoMapper.toPostDtoComplete(s.getPost());
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(dto -> dto != null)
                .collect(Collectors.toList());
    }
}
