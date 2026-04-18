package it.permessi.rest.permessi.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.permessi.rest.permessi.dto.PostDto;
import it.permessi.rest.permessi.dto.PostFormDto;
import it.permessi.rest.permessi.entity.Allegato;
import it.permessi.rest.permessi.entity.Post;
import it.permessi.rest.permessi.entity.Utente;
import it.permessi.rest.permessi.mapper.DtoMapper;
import it.permessi.rest.permessi.repository.AllegatoRepository;
import it.permessi.rest.permessi.repository.PostRepository;
import it.permessi.rest.permessi.repository.UtenteRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PostService {

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10 MB
    private static final List<String> ALLOWED_MIME_TYPES = List.of(
        "image/jpeg", "image/png", "image/gif", "image/webp",
        "application/pdf", "application/msword",
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
        "application/vnd.ms-excel",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        "application/vnd.ms-powerpoint",
        "application/vnd.openxmlformats-officedocument.presentationml.presentation",
        "text/plain"
    );

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @Autowired PostRepository postRepo;
    @Autowired UtenteRepository utenteRepo;
    @Autowired AllegatoRepository allegatoRepo;

    @Transactional
    public PostDto create(String contenuto, MultipartFile[] files, String username) {
        if (contenuto == null || contenuto.isBlank())
            throw new IllegalArgumentException("Il contenuto non può essere vuoto");
        if (contenuto.length() > 1000)
            throw new IllegalArgumentException("Il contenuto non può superare 1000 caratteri");

        Utente u = utenteRepo.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato: " + username));

        Post p = new Post();
        p.setContenuto(contenuto.trim());
        p.setUtente(u);
        Post savedPost = postRepo.save(p);

        if (files != null) {
            List<Allegato> allegati = salvaFiles(files, savedPost);
            savedPost.setAllegati(allegati);
        }

        return DtoMapper.toPostDtoComplete(savedPost);
    }

    @Transactional(readOnly = true)
    public List<PostDto> listAll() {
        List<Post> posts = postRepo.findAll();
        return posts.stream()
                .map(DtoMapper::toPostDtoComplete)
                .collect(Collectors.toList());
    }

    @Transactional
    public PostDto update(PostFormDto form, UserDetails userDetails) {
        if (form.getId() == null)
            throw new IllegalArgumentException("Id post obbligatorio per update");
        if (form.getContenuto() != null && form.getContenuto().trim().length() > 1000)
            throw new IllegalArgumentException("Il contenuto non può superare 1000 caratteri");

        Post existingPost = postRepo.findById(form.getId())
            .orElseThrow(() -> new EntityNotFoundException("Post non trovato con id: " + form.getId()));

        if (!existingPost.getUtente().getUsername().equals(userDetails.getUsername()))
            throw new SecurityException("Non sei autorizzato a modificare questo post");

        if (form.getContenuto() != null && !form.getContenuto().trim().isEmpty())
            existingPost.setContenuto(form.getContenuto().trim());

        return DtoMapper.toPostDtoLight(postRepo.save(existingPost));
    }

    @Transactional
    public void delete(Long id, UserDetails userDetails) {
        Post post = postRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Post non trovato con id: " + id));

        if (!post.getUtente().getUsername().equals(userDetails.getUsername()))
            throw new SecurityException("Non sei autorizzato a eliminare questo post");

        // Elimina i file fisici degli allegati
        if (post.getAllegati() != null) {
            post.getAllegati().forEach(a -> eliminaFileFisico(a.getNomeFile()));
        }

        postRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<PostDto> getTendenze(int limit) {
        if (limit <= 0 || limit > 60)
            throw new IllegalArgumentException("Il limite deve essere tra 1 e 60");
        Pageable pageable = PageRequest.of(0, limit);
        return postRepo.findPostsOrderByLikesDesc(pageable).stream()
                .map(DtoMapper::toPostDtoForTendenze)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PostDto> allPostByUtente(Long idUtente) {
        if (!utenteRepo.existsById(idUtente))
            throw new EntityNotFoundException("Utente non trovato con id: " + idUtente);
        return postRepo.findByUtenteId(idUtente).stream()
                .map(DtoMapper::toPostDtoComplete)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostDto postById(Long idPost) {
        Post post = postRepo.findById(idPost)
            .orElseThrow(() -> new EntityNotFoundException("Post non trovato con id: " + idPost));
        return DtoMapper.toPostDtoComplete(post);
    }

    private List<Allegato> salvaFiles(MultipartFile[] files, Post post) {
        List<Allegato> allegati = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            String mimeType = file.getContentType();
            if (mimeType == null || !ALLOWED_MIME_TYPES.contains(mimeType))
                throw new IllegalArgumentException("Tipo file non supportato: " + file.getOriginalFilename());
            if (file.getSize() > MAX_FILE_SIZE)
                throw new IllegalArgumentException("File troppo grande (max 10MB): " + file.getOriginalFilename());

            String ext = getEstensione(file.getOriginalFilename());
            String nomeFile = UUID.randomUUID().toString() + ext;

            try {
                Path uploadPath = Paths.get(uploadDir).toAbsolutePath();
                Files.createDirectories(uploadPath);
                Files.copy(file.getInputStream(), uploadPath.resolve(nomeFile));
            } catch (IOException e) {
                throw new RuntimeException("Errore nel salvataggio del file: " + file.getOriginalFilename(), e);
            }

            Allegato a = new Allegato();
            a.setNomeOriginale(file.getOriginalFilename());
            a.setNomeFile(nomeFile);
            a.setUrl("/uploads/" + nomeFile);
            a.setMimeType(mimeType);
            a.setTipo(mimeType.startsWith("image/") ? "IMAGE" : "DOCUMENT");
            a.setPost(post);
            allegati.add(allegatoRepo.save(a));
        }
        return allegati;
    }

    private void eliminaFileFisico(String nomeFile) {
        if (nomeFile == null) return;
        try {
            Files.deleteIfExists(Paths.get(uploadDir).toAbsolutePath().resolve(nomeFile));
        } catch (IOException ignored) {}
    }

    private String getEstensione(String nomeOriginale) {
        if (nomeOriginale == null) return "";
        int dot = nomeOriginale.lastIndexOf('.');
        return dot >= 0 ? nomeOriginale.substring(dot) : "";
    }
}
