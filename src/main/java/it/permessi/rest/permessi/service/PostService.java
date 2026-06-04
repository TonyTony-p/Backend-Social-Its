package it.permessi.rest.permessi.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
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

import it.permessi.rest.permessi.dto.PageResponse;
import it.permessi.rest.permessi.dto.PostDto;
import it.permessi.rest.permessi.dto.PostFormDto;
import it.permessi.rest.permessi.entity.Allegato;
import it.permessi.rest.permessi.entity.Post;
import it.permessi.rest.permessi.entity.Utente;
import it.permessi.rest.permessi.mapper.DtoMapper;
import it.permessi.rest.permessi.repository.AllegatoRepository;
import it.permessi.rest.permessi.repository.CommentoRepository;
import it.permessi.rest.permessi.repository.LikeRepository;
import it.permessi.rest.permessi.repository.OpzioneRepository;
import it.permessi.rest.permessi.repository.PostRepository;
import it.permessi.rest.permessi.repository.PostSalvatoRepository;
import it.permessi.rest.permessi.repository.SegnalazioneRepository;
import it.permessi.rest.permessi.repository.SondaggioRepository;
import it.permessi.rest.permessi.repository.UtenteRepository;
import it.permessi.rest.permessi.repository.VotoRepository;
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
    @Autowired SegueService segueService;
    @Autowired VotoRepository votoRepo;
    @Autowired SondaggioService sondaggioService;
    @Autowired PostSalvatoRepository postSalvatoRepo;
    @Autowired SegnalazioneRepository segnalazioneRepo;
    @Autowired SondaggioRepository sondaggioRepo;
    @Autowired OpzioneRepository opzioneRepo;
    @Autowired LikeRepository likeRepo;
    @Autowired CommentoRepository commentoRepo;

    @Transactional
    public PostDto create(String contenuto, MultipartFile[] files, String sondaggioJson, String username) {
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

        if (sondaggioJson != null && !sondaggioJson.isBlank()) {
            parsaECreaSondaggio(sondaggioJson, savedPost);
        }

        Post reloaded = postRepo.findById(savedPost.getIdPost()).orElse(savedPost);
        PostDto dto = DtoMapper.toPostDtoComplete(reloaded);
        if (reloaded.getSondaggio() != null) {
            dto.setSondaggio(DtoMapper.toSondaggioDto(reloaded.getSondaggio(), null));
        }
        return dto;
    }

    @Transactional(readOnly = true)
    public PageResponse<PostDto> listAll(String username, int page, int size) {
        Pageable pageable = PageRequest.of(page, Math.min(size, 50));
        return PageResponse.from(
            postRepo.findAllByOrderByDataOraDesc(pageable)
                    .map(p -> enrichWithSondaggio(p, username))
        );
    }

    private PostDto enrichWithSondaggio(Post p, String username) {
        PostDto dto = DtoMapper.toPostDtoComplete(p);
        if (p.getSondaggio() != null) {
            Long idVotato = username != null
                    ? votoRepo.findIdOpzioneByUsernameAndSondaggioId(username, p.getSondaggio().getIdSondaggio())
                    : null;
            dto.setSondaggio(DtoMapper.toSondaggioDto(p.getSondaggio(), idVotato));
        }
        return dto;
    }

    @SuppressWarnings("unchecked")
    private void parsaECreaSondaggio(String json, Post post) {
        try {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            java.util.Map<String, Object> data = mapper.readValue(json, java.util.Map.class);
            String domanda = (String) data.get("domanda");
            java.util.List<String> opzioni = (java.util.List<String>) data.get("opzioni");
            Integer durata = data.get("durataGiorni") != null ? (Integer) data.get("durataGiorni") : null;
            if (domanda != null && !domanda.isBlank() && opzioni != null && opzioni.size() >= 2) {
                sondaggioService.crea(post, domanda, opzioni, durata);
            }
        } catch (Exception e) {
            // invalid poll data, skip silently
        }
    }

    @Transactional
    public PostDto update(PostFormDto form, UserDetails userDetails) {
        if (form.getId() == null)
            throw new IllegalArgumentException("Id post obbligatorio per update");
        if (form.getContenuto() != null && form.getContenuto().trim().length() > 1000)
            throw new IllegalArgumentException("Il contenuto non può superare 1000 caratteri");

        Post existingPost = postRepo.findById(form.getId())
            .orElseThrow(() -> new EntityNotFoundException("Post non trovato con id: " + form.getId()));

        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));
        if (!existingPost.getUtente().getUsername().equals(userDetails.getUsername()) && !isAdmin)
            throw new SecurityException("Non sei autorizzato a modificare questo post");

        if (form.getContenuto() != null && !form.getContenuto().trim().isEmpty())
            existingPost.setContenuto(form.getContenuto().trim());

        return DtoMapper.toPostDtoLight(postRepo.save(existingPost));
    }

    @Transactional
    public void delete(Long id, UserDetails userDetails) {
        Post post = postRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Post non trovato con id: " + id));

        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));
        if (!post.getUtente().getUsername().equals(userDetails.getUsername()) && !isAdmin)
            throw new SecurityException("Non sei autorizzato a eliminare questo post");

        // Eliminazione esplicita nell'ordine corretto (nessuna cascata implicita)

        // 1. Voti sondaggio → dipende da OpzioneSondaggio e Sondaggio
        if (post.getSondaggio() != null) {
            votoRepo.bulkDeleteBySondaggio(post.getSondaggio());
            opzioneRepo.bulkDeleteBySondaggio(post.getSondaggio());
            sondaggioRepo.bulkDeleteByPost(post);
        }

        // 2. Like e commenti
        likeRepo.bulkDeleteByPost(post);
        commentoRepo.bulkDeleteByPost(post);

        // 3. Allegati: elimina file fisici poi record DB
        if (post.getAllegati() != null) {
            post.getAllegati().forEach(a -> eliminaFileFisico(a.getNomeFile()));
        }
        allegatoRepo.bulkDeleteByPost(post);

        // 4. PostSalvato e Segnalazioni (tabelle senza cascade)
        postSalvatoRepo.bulkDeleteByPost(post);
        segnalazioneRepo.bulkDeleteByPost(post);

        // 5. Post
        postRepo.deleteById(post.getIdPost());
    }

    @Transactional(readOnly = true)
    public PageResponse<PostDto> getTendenze(int size, int page, String username) {
        int safeSize = Math.min(Math.max(size, 1), 60);
        Pageable pageable = PageRequest.of(page, safeSize);
        LocalDateTime since = LocalDateTime.now().minusDays(30);
        return PageResponse.from(
            postRepo.findTrendingPostsSince(since, pageable)
                    .map(p -> enrichWithSondaggio(p, username))
        );
    }

    @Transactional
    public List<PostDto> allPostByUtente(Long idUtente) {
        if (!utenteRepo.existsById(idUtente))
            throw new EntityNotFoundException("Utente non trovato con id: " + idUtente);
        return postRepo.findByUtenteId(idUtente).stream()
                .map(DtoMapper::toPostDtoComplete)
                .collect(Collectors.toList());
    }

    public List<PostDto> getPostByUsername(String username) {
        return postRepo.findByUtenteUsernameOrderByDataOraDesc(username).stream()
                .map(DtoMapper::toPostDtoLight)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PageResponse<PostDto> getPostDaSeguiti(String username, int page, int size) {
        List<String> usernames = segueService.getSeguitiUsernames(username);
        int safeSize = Math.min(size, 50);
        if (usernames.isEmpty()) return PageResponse.empty(page, safeSize);
        Pageable pageable = PageRequest.of(page, safeSize);
        return PageResponse.from(
            postRepo.findByUtenteUsernameIn(usernames, pageable)
                    .map(p -> enrichWithSondaggio(p, username))
        );
    }

    @Transactional(readOnly = true)
    public PostDto postById(Long idPost, String username) {
        Post post = postRepo.findById(idPost)
            .orElseThrow(() -> new EntityNotFoundException("Post non trovato con id: " + idPost));
        return enrichWithSondaggio(post, username);
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
