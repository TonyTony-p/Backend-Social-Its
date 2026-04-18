package it.permessi.rest.permessi.controller;

import it.permessi.rest.permessi.dto.PageResponse;
import it.permessi.rest.permessi.dto.IdRequest;
import it.permessi.rest.permessi.dto.ProfiloDto;
import it.permessi.rest.permessi.dto.ProfiloFormDto;
import it.permessi.rest.permessi.dto.UtenteFormDto;
import it.permessi.rest.permessi.dto.UtenteDto;
import it.permessi.rest.permessi.service.UtenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** API REST per Utenti. */
@RestController
@RequestMapping("/api/utenti")
public class UtenteController {

    @Autowired private UtenteService service;

    /** Crea utente. */
    @PostMapping
    @PreAuthorize("hasAuthority('UTENTE_CREATE')")
    public UtenteDto create(@Valid @RequestBody UtenteFormDto form) {
        return service.create(form);
    }

    /** Aggiorna utente (id nel body). */
    @PutMapping
    @PreAuthorize("hasAuthority('UTENTE_UPDATE')")
    public UtenteDto update(@Valid @RequestBody UtenteFormDto form) {
        return service.update(form);
    }

    /** Elimina utente (id nel body). */
    @DeleteMapping
    @PreAuthorize("hasAuthority('UTENTE_DELETE')")
    public void delete(@Valid @RequestBody IdRequest req) {
        service.delete(req.getId());
    }

    /** Dettaglio utente. */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('UTENTE_READ')")
    public UtenteDto get(@PathVariable Long id) {
        return service.getById(id);
    }

    /** Lista paginata. */
    @GetMapping
    @PreAuthorize("hasAuthority('UTENTE_READ')")
    public PageResponse<UtenteDto> list(Pageable pageable) {
        return service.list(pageable);
    }
    
    @GetMapping("/my-profile")
    public ResponseEntity<UtenteDto> getMyProfile(@AuthenticationPrincipal UserDetails userDetails) {
        UtenteDto profile = service.getMyProfile(userDetails.getUsername());
        return ResponseEntity.ok(profile);
    }
    
    @GetMapping("/utentiConPost/{id}")
    public ResponseEntity<UtenteDto> getUtenteConPost(@PathVariable Long id){
    	UtenteDto profile = service.getUtenteConPost(id);
    	return ResponseEntity.ok(profile);
    }
    
    
    @GetMapping("/my-profile/con-post")
    public ResponseEntity<UtenteDto> getMyProfileConPost(@AuthenticationPrincipal UserDetails userDetails) {
        UtenteDto profile = service.getMyProfileConPost(userDetails.getUsername());
        return ResponseEntity.ok(profile);
    }
    


    /** Lista completa (non paginata). */
    @GetMapping("/tutti")
    @PreAuthorize("hasAuthority('UTENTE_READ')")
    public List<UtenteDto> listAll() {
        return service.listAll();
    }

    /** Profilo pubblico di un utente (per username). */
    @GetMapping("/profilo/{username}")
    public ResponseEntity<ProfiloDto> getProfilo(@PathVariable String username) {
        return ResponseEntity.ok(service.getProfilo(username));
    }

    /** Aggiorna il proprio profilo (bio, foto, dati personali). */
    @PutMapping("/my-profile")
    public ResponseEntity<ProfiloDto> updateMyProfilo(
            @Valid @RequestBody ProfiloFormDto form,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.updateMyProfilo(userDetails.getUsername(), form));
    }
}
