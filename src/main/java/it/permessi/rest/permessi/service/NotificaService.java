package it.permessi.rest.permessi.service;

import it.permessi.rest.permessi.dto.NotificaDto;
import it.permessi.rest.permessi.entity.Notifica;
import it.permessi.rest.permessi.entity.Utente;
import it.permessi.rest.permessi.repository.NotificaRepository;
import it.permessi.rest.permessi.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificaService {

    @Autowired private NotificaRepository notificaRepo;
    @Autowired private UtenteRepository utenteRepo;

    /**
     * Crea una notifica. Non genera nulla se l'attore coincide con il destinatario.
     */
    @Transactional
    public void crea(String destinatarioUsername, String tipo,
                     String attoreUsername, String attoreNome,
                     Long idRiferimento, String tipoRiferimento, String messaggio) {
        if (destinatarioUsername == null || destinatarioUsername.equals(attoreUsername)) return;

        Utente destinatario = utenteRepo.findByUsername(destinatarioUsername).orElse(null);
        if (destinatario == null) return;

        Notifica n = new Notifica();
        n.setDestinatario(destinatario);
        n.setTipo(tipo);
        n.setAttoreUsername(attoreUsername);
        n.setAttoreNome(attoreNome);
        n.setIdRiferimento(idRiferimento);
        n.setTipoRiferimento(tipoRiferimento);
        n.setMessaggio(messaggio);
        notificaRepo.save(n);
    }

    @Transactional(readOnly = true)
    public List<NotificaDto> getNotifiche(String username, int page, int size) {
        PageRequest pr = PageRequest.of(page, Math.min(size, 50));
        return notificaRepo.findByDestinatario_UsernameOrderByCreatedAtDesc(username, pr)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public long contaNonLette(String username) {
        return notificaRepo.countByDestinatario_UsernameAndLettaFalse(username);
    }

    @Transactional
    public void segnaComeLetta(Long id, String username) {
        notificaRepo.findById(id).ifPresent(n -> {
            if (n.getDestinatario().getUsername().equals(username)) {
                n.setLetta(true);
                notificaRepo.save(n);
            }
        });
    }

    @Transactional
    public void segnaComeLetteTutte(String username) {
        notificaRepo.segnaComeLetteTutte(username);
    }

    @Transactional
    public void elimina(Long id, String username) {
        notificaRepo.findById(id).ifPresent(n -> {
            if (n.getDestinatario().getUsername().equals(username))
                notificaRepo.delete(n);
        });
    }

    @Scheduled(cron = "0 0 3 * * ?") // ogni notte alle 3:00
    @Transactional
    public void eliminaVecchie() {
        notificaRepo.deleteByCreatedAtBefore(java.time.LocalDateTime.now().minusDays(10));
    }

    private NotificaDto toDto(Notifica n) {
        NotificaDto dto = new NotificaDto();
        dto.setId(n.getId());
        dto.setTipo(n.getTipo());
        dto.setAttoreUsername(n.getAttoreUsername());
        dto.setAttoreNome(n.getAttoreNome());
        dto.setIdRiferimento(n.getIdRiferimento());
        dto.setTipoRiferimento(n.getTipoRiferimento());
        dto.setMessaggio(n.getMessaggio());
        dto.setLetta(n.isLetta());
        dto.setCreatedAt(n.getCreatedAt());
        return dto;
    }
}
