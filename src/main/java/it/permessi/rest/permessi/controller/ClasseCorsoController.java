package it.permessi.rest.permessi.controller;

import it.permessi.rest.permessi.dto.*;
import it.permessi.rest.permessi.dto.CommentoAnnuncioDto;
import it.permessi.rest.permessi.service.ClasseCorsoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/classi")
public class ClasseCorsoController {

    @Autowired private ClasseCorsoService service;

    // ── ClasseCorso ──────────────────────────────────────────────

    @PostMapping
    @PreAuthorize("hasAuthority('CLASSE_CREATE')")
    public ResponseEntity<ClasseCorsoDto> crea(
            @Valid @RequestBody ClasseCorsoFormDto form,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(201).body(service.creaClasse(form, userDetails.getUsername()));
    }

    @GetMapping
    public ResponseEntity<Page<ClasseCorsoDto>> listaClassiPubbliche(Pageable pageable) {
        return ResponseEntity.ok(service.listaClassiPubbliche(pageable));
    }

    @GetMapping("/top")
    public ResponseEntity<List<ClasseCorsoDto>> topClassi(
            @RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(service.topClassiPubbliche(limit));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CLASSE_READ')")
    public ResponseEntity<ClasseCorsoDto> dettaglio(@PathVariable Long id) {
        return ResponseEntity.ok(service.dettaglioClasse(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CLASSE_UPDATE')")
    public ResponseEntity<ClasseCorsoDto> aggiorna(
            @PathVariable Long id,
            @Valid @RequestBody ClasseCorsoFormDto form,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.aggiornaClasse(id, form, userDetails.getUsername()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CLASSE_DELETE')")
    public ResponseEntity<Void> elimina(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        service.eliminaClasse(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mie")
    @PreAuthorize("hasAuthority('PROFESSORE') or hasAuthority('ADMIN')")
    public ResponseEntity<List<ClasseCorsoDto>> mieClassi(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.mieclassi(userDetails.getUsername()));
    }

    @GetMapping("/admin/tutte")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<ClasseCorsoDto>> tutteLeClassi() {
        return ResponseEntity.ok(service.listaTutte());
    }

    // ── Iscrizioni ───────────────────────────────────────────────

    @PostMapping("/{id}/iscriviti")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<IscrizioneClasseDto> iscriviti(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(201).body(service.iscriviti(id, userDetails.getUsername()));
    }

    @PostMapping("/iscriviti-con-codice/{codice}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<IscrizioneClasseDto> iscrivitiConCodice(
            @PathVariable String codice,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(201).body(service.iscrivitiConCodice(codice, userDetails.getUsername()));
    }

    @GetMapping("/{id}/studenti")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<IscrizioneClasseDto>> listaStudenti(@PathVariable Long id) {
        return ResponseEntity.ok(service.listaStudentiApprovati(id));
    }

    @GetMapping("/{id}/iscrizioni")
    @PreAuthorize("hasAuthority('PROFESSORE') or hasAuthority('ADMIN') or hasAuthority('ISCRIZIONE_READ')")
    public ResponseEntity<List<IscrizioneClasseDto>> listaIscrizioni(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.listaIscrizioni(id, userDetails.getUsername()));
    }

    @PutMapping("/{id}/iscrizioni/{iscrizioneId}")
    @PreAuthorize("hasAuthority('PROFESSORE') or hasAuthority('ADMIN') or hasAuthority('ISCRIZIONE_UPDATE')")
    public ResponseEntity<IscrizioneClasseDto> aggiornaIscrizione(
            @PathVariable Long id,
            @PathVariable Long iscrizioneId,
            @Valid @RequestBody IscrizioneUpdateDto dto,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.aggiornaIscrizione(id, iscrizioneId, dto, userDetails.getUsername()));
    }

    @DeleteMapping("/{id}/abbandona")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> abbandona(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        service.abbandonaClasse(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mie-iscrizioni")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<IscrizioneClasseDto>> miIscrizioni(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.miIscrizioni(userDetails.getUsername()));
    }

    // ── Annunci ──────────────────────────────────────────────────

    @PostMapping("/{id}/annunci")
    @PreAuthorize("hasAuthority('ANNUNCIO_CREATE')")
    public ResponseEntity<AnnuncioDto> creaAnnuncio(
            @PathVariable Long id,
            @Valid @RequestBody AnnuncioFormDto form,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(201).body(service.creaAnnuncio(id, form, userDetails.getUsername()));
    }

    @GetMapping("/{id}/annunci")
    @PreAuthorize("hasAuthority('ANNUNCIO_READ')")
    public ResponseEntity<List<AnnuncioDto>> listaAnnunci(@PathVariable Long id) {
        return ResponseEntity.ok(service.listaAnnunci(id));
    }

    @PostMapping(value = "/{id}/annunci/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ANNUNCIO_CREATE')")
    public ResponseEntity<Map<String, String>> uploadAllegatoAnnuncio(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(service.uploadAllegatoAnnuncio(file));
    }

    @DeleteMapping("/{id}/annunci/{annuncioId}")
    @PreAuthorize("hasAuthority('ANNUNCIO_DELETE')")
    public ResponseEntity<Void> eliminaAnnuncio(
            @PathVariable Long id,
            @PathVariable Long annuncioId,
            @AuthenticationPrincipal UserDetails userDetails) {
        service.eliminaAnnuncio(id, annuncioId, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/annunci/{annuncioId}/commenti")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CommentoAnnuncioDto>> listaCommenti(
            @PathVariable Long id,
            @PathVariable Long annuncioId) {
        return ResponseEntity.ok(service.listaCommenti(id, annuncioId));
    }

    @PostMapping("/{id}/annunci/{annuncioId}/commenti")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommentoAnnuncioDto> aggiungiCommento(
            @PathVariable Long id,
            @PathVariable Long annuncioId,
            @RequestBody Map<String, String> body,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(201).body(
            service.aggiungiCommento(id, annuncioId, body.get("testo"), userDetails.getUsername()));
    }

    @DeleteMapping("/{id}/annunci/{annuncioId}/commenti/{commentoId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> eliminaCommento(
            @PathVariable Long id,
            @PathVariable Long annuncioId,
            @PathVariable Long commentoId,
            @AuthenticationPrincipal UserDetails userDetails) {
        service.eliminaCommento(id, annuncioId, commentoId, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    // ── Materiali ────────────────────────────────────────────────

    @PostMapping("/{id}/materiali")
    @PreAuthorize("hasAuthority('MATERIALE_CREATE')")
    public ResponseEntity<MaterialeClasseDto> caricaMateriale(
            @PathVariable Long id,
            @Valid @RequestBody MaterialeClasseFormDto form,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(201).body(service.caricaMateriale(id, form, userDetails.getUsername()));
    }

    @GetMapping("/{id}/materiali")
    @PreAuthorize("hasAuthority('MATERIALE_READ')")
    public ResponseEntity<List<MaterialeClasseDto>> listaMateriali(@PathVariable Long id) {
        return ResponseEntity.ok(service.listaMateriali(id));
    }

    @DeleteMapping("/{id}/materiali/{materialeId}")
    @PreAuthorize("hasAuthority('MATERIALE_DELETE')")
    public ResponseEntity<Void> eliminaMateriale(
            @PathVariable Long id,
            @PathVariable Long materialeId,
            @AuthenticationPrincipal UserDetails userDetails) {
        service.eliminaMateriale(id, materialeId, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    // ── Compiti ──────────────────────────────────────────────────

    @PostMapping("/{id}/compiti")
    @PreAuthorize("hasAuthority('COMPITO_CREATE')")
    public ResponseEntity<CompitoDto> creaCompito(
            @PathVariable Long id,
            @Valid @RequestBody CompitoFormDto form,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(201).body(service.creaCompito(id, form, userDetails.getUsername()));
    }

    @GetMapping("/{id}/compiti")
    @PreAuthorize("hasAuthority('COMPITO_READ')")
    public ResponseEntity<List<CompitoDto>> listaCompiti(@PathVariable Long id) {
        return ResponseEntity.ok(service.listaCompiti(id));
    }

    @PutMapping("/{id}/compiti/{compitoId}")
    @PreAuthorize("hasAuthority('COMPITO_UPDATE')")
    public ResponseEntity<CompitoDto> aggiornaCompito(
            @PathVariable Long id,
            @PathVariable Long compitoId,
            @Valid @RequestBody CompitoFormDto form,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.aggiornaCompito(id, compitoId, form, userDetails.getUsername()));
    }

    @DeleteMapping("/{id}/compiti/{compitoId}")
    @PreAuthorize("hasAuthority('COMPITO_DELETE')")
    public ResponseEntity<Void> eliminaCompito(
            @PathVariable Long id,
            @PathVariable Long compitoId,
            @AuthenticationPrincipal UserDetails userDetails) {
        service.eliminaCompito(id, compitoId, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    // ── Consegne ─────────────────────────────────────────────────

    @PostMapping("/{id}/compiti/{compitoId}/consegna")
    @PreAuthorize("hasAuthority('CONSEGNA_CREATE')")
    public ResponseEntity<ConsegnaCompitoDto> consegna(
            @PathVariable Long id,
            @PathVariable Long compitoId,
            @Valid @RequestBody ConsegnaCompitoFormDto form,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(201).body(service.consegna(id, compitoId, form, userDetails.getUsername()));
    }

    @GetMapping("/{id}/compiti/{compitoId}/consegne")
    @PreAuthorize("hasAuthority('CONSEGNA_READ')")
    public ResponseEntity<List<ConsegnaCompitoDto>> listaConsegne(
            @PathVariable Long id,
            @PathVariable Long compitoId,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.listaConsegne(id, compitoId, userDetails.getUsername()));
    }

    @PutMapping("/{id}/consegne/{consegnaId}/valuta")
    @PreAuthorize("hasAuthority('CONSEGNA_UPDATE')")
    public ResponseEntity<ConsegnaCompitoDto> valuta(
            @PathVariable Long id,
            @PathVariable Long consegnaId,
            @Valid @RequestBody ValutazioneFormDto form,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.valutaConsegna(id, consegnaId, form, userDetails.getUsername()));
    }
}
