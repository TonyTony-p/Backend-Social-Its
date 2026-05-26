package it.permessi.rest.permessi.service;

import it.permessi.rest.permessi.dto.ConversazioneDto;
import it.permessi.rest.permessi.dto.MessaggioDto;
import it.permessi.rest.permessi.entity.Conversazione;
import it.permessi.rest.permessi.entity.Messaggio;
import it.permessi.rest.permessi.entity.Utente;
import it.permessi.rest.permessi.repository.ConversazioneRepository;
import it.permessi.rest.permessi.repository.MessaggioRepository;
import it.permessi.rest.permessi.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessaggioService {

    @Autowired private ConversazioneRepository convRepo;
    @Autowired private MessaggioRepository msgRepo;
    @Autowired private UtenteRepository utenteRepo;
    @Autowired private NotificaService notificaService;

    @Transactional(readOnly = true)
    public List<ConversazioneDto> getConversazioni(String username) {
        return convRepo.findAllByUsername(username).stream()
                .map(c -> toSummaryDto(c, username))
                .collect(Collectors.toList());
    }

    @Transactional
    public ConversazioneDto getOCreaConversazione(String currentUsername, String altroUsername, Long after) {
        Utente current = utenteRepo.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Utente non trovato: " + currentUsername));
        Utente altro = utenteRepo.findByUsername(altroUsername)
                .orElseThrow(() -> new RuntimeException("Utente non trovato: " + altroUsername));

        Conversazione conv = convRepo.findBetween(currentUsername, altroUsername)
                .orElseGet(() -> {
                    Conversazione nuova = new Conversazione();
                    if (currentUsername.compareTo(altroUsername) <= 0) {
                        nuova.setUtente1(current);
                        nuova.setUtente2(altro);
                    } else {
                        nuova.setUtente1(altro);
                        nuova.setUtente2(current);
                    }
                    return convRepo.save(nuova);
                });

        msgRepo.segnaComeLetti(conv.getId(), currentUsername);

        ConversazioneDto dto = toSummaryDto(conv, currentUsername);
        dto.setMessaggi(conv.getMessaggi().stream()
                .filter(m -> after == null || m.getId() > after)
                .map(this::toMsgDto)
                .collect(Collectors.toList()));
        return dto;
    }

    /** Invia un messaggio, con supporto opzionale per risposta a un messaggio esistente. */
    @Transactional
    public MessaggioDto invia(String mittente, String destinatarioUsername, String testo, Long replyToId) {
        if (testo == null || testo.isBlank()) throw new IllegalArgumentException("Testo vuoto");
        if (mittente.equals(destinatarioUsername)) throw new IllegalArgumentException("Non puoi scrivere a te stesso");

        Utente mittenteUtente = utenteRepo.findByUsername(mittente)
                .orElseThrow(() -> new RuntimeException("Utente non trovato: " + mittente));

        ConversazioneDto convDto = getOCreaConversazione(mittente, destinatarioUsername, null);
        Conversazione conv = convRepo.findById(convDto.getId())
                .orElseThrow(() -> new RuntimeException("Conversazione non trovata"));

        Messaggio msg = new Messaggio();
        msg.setConversazione(conv);
        msg.setMittente(mittenteUtente);
        msg.setTesto(testo.trim());

        // Valida e imposta la risposta (deve appartenere alla stessa conversazione)
        if (replyToId != null) {
            msgRepo.findById(replyToId).ifPresent(replied -> {
                if (replied.getConversazione().getId().equals(conv.getId())) {
                    msg.setReplyToId(replyToId);
                }
            });
        }

        Messaggio saved = msgRepo.save(msg);
        conv.setUpdatedAt(saved.getCreatedAt());
        convRepo.save(conv);

        String nomeDisplay = mittenteUtente.getNome() + " " + mittenteUtente.getCognome();
        String preview = testo.length() > 60 ? testo.substring(0, 60) + "…" : testo;
        notificaService.crea(destinatarioUsername, "MESSAGGIO", mittente, nomeDisplay,
                conv.getId(), "CONVERSAZIONE",
                nomeDisplay + " ti ha inviato un messaggio: \"" + preview + "\"");

        return toMsgDto(saved);
    }

    @Transactional
    public void segnaComeLetti(String currentUsername, String altroUsername) {
        convRepo.findBetween(currentUsername, altroUsername)
                .ifPresent(c -> msgRepo.segnaComeLetti(c.getId(), currentUsername));
    }

    @Transactional(readOnly = true)
    public long contaTotaleNonLetti(String username) {
        return msgRepo.countTotaleNonLetti(username);
    }

    /** Eliminazione soft (solo il mittente). */
    @Transactional
    public void elimina(Long id, String username) {
        int updated = msgRepo.eliminaById(id, username);
        if (updated == 0) throw new RuntimeException("Messaggio non trovato o non autorizzato");
    }

    /** Fissa/desfissa un messaggio. Se durationHours > 0 imposta una scadenza. */
    @Transactional
    public MessaggioDto toggleFissa(Long id, String username, Integer durationHours) {
        if (!msgRepo.isPartecipante(id, username))
            throw new RuntimeException("Non autorizzato");
        Messaggio m = msgRepo.findById(id).orElseThrow(() -> new RuntimeException("Messaggio non trovato"));
        if (m.isFissato()) {
            m.setFissato(false);
            m.setPinnedUntil(null);
        } else {
            m.setFissato(true);
            if (durationHours != null && durationHours > 0) {
                m.setPinnedUntil(LocalDateTime.now().plusHours(durationHours));
            } else {
                m.setPinnedUntil(null);
            }
        }
        return toMsgDto(msgRepo.save(m));
    }

    /** Segna/desegna come importante (entrambi i partecipanti). */
    @Transactional
    public MessaggioDto toggleImportante(Long id, String username) {
        if (!msgRepo.isPartecipante(id, username))
            throw new RuntimeException("Non autorizzato");
        Messaggio m = msgRepo.findById(id).orElseThrow(() -> new RuntimeException("Messaggio non trovato"));
        m.setImportante(!m.isImportante());
        return toMsgDto(msgRepo.save(m));
    }

    private ConversazioneDto toSummaryDto(Conversazione c, String currentUsername) {
        ConversazioneDto dto = new ConversazioneDto();
        dto.setId(c.getId());
        dto.setUpdatedAt(c.getUpdatedAt());

        Utente altro = c.getUtente1().getUsername().equals(currentUsername) ? c.getUtente2() : c.getUtente1();
        dto.setAltroUsername(altro.getUsername());
        dto.setAltroNome(altro.getNome());
        dto.setAltroCognome(altro.getCognome());
        dto.setAltroLastSeen(altro.getLastSeen());

        List<Messaggio> msgs = c.getMessaggi();
        msgs.stream().filter(m -> !m.isEliminato()).reduce((a, b) -> b).ifPresent(ultimo -> {
            dto.setUltimoMessaggio(ultimo.getTesto());
            dto.setUltimoMittenteUsername(ultimo.getMittente().getUsername());
        });

        dto.setNonLetti(msgRepo.countNonLetti(c.getId(), currentUsername));
        return dto;
    }

    private MessaggioDto toMsgDto(Messaggio m) {
        MessaggioDto dto = new MessaggioDto();
        dto.setId(m.getId());
        dto.setMittenteUsername(m.getMittente().getUsername());
        dto.setTesto(m.isEliminato() ? null : m.getTesto());
        dto.setEliminato(m.isEliminato());
        // fissato effettivo: true solo se non è scaduto
        boolean effectiveFissato = m.isFissato() &&
                (m.getPinnedUntil() == null || m.getPinnedUntil().isAfter(LocalDateTime.now()));
        dto.setFissato(effectiveFissato);
        dto.setPinnedUntil(m.getPinnedUntil());
        dto.setImportante(m.isImportante());
        dto.setLetto(m.isLetto());
        dto.setCreatedAt(m.getCreatedAt());

        // Risolve la citazione (reply)
        if (m.getReplyToId() != null) {
            dto.setReplyToId(m.getReplyToId());
            msgRepo.findById(m.getReplyToId()).ifPresent(replied -> {
                dto.setReplyToMittente(replied.getMittente().getUsername());
                dto.setReplyToTesto(replied.isEliminato() ? "Messaggio eliminato"
                        : (replied.getTesto() != null && replied.getTesto().length() > 80
                                ? replied.getTesto().substring(0, 80) + "…"
                                : replied.getTesto()));
            });
        }

        return dto;
    }
}
