package it.permessi.rest.permessi.service;

import it.permessi.rest.permessi.dto.ProfiloDto;
import it.permessi.rest.permessi.entity.Segue;
import it.permessi.rest.permessi.entity.Utente;
import it.permessi.rest.permessi.mapper.DtoMapper;
import it.permessi.rest.permessi.repository.SegueRepository;
import it.permessi.rest.permessi.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SegueService {

    @Autowired private SegueRepository segueRepo;
    @Autowired private UtenteRepository utenteRepo;
    @Autowired private NotificaService notificaService;

    @Transactional
    public void segui(String seguaceUsername, String seguitoUsername) {
        if (seguaceUsername.equals(seguitoUsername))
            throw new IllegalArgumentException("Non puoi seguire te stesso");
        if (segueRepo.existsBySeguaceUsernameAndSeguitoUsername(seguaceUsername, seguitoUsername))
            return;

        Utente seguace = utenteRepo.findByUsername(seguaceUsername)
                .orElseThrow(() -> new RuntimeException("Utente non trovato: " + seguaceUsername));
        Utente seguito = utenteRepo.findByUsername(seguitoUsername)
                .orElseThrow(() -> new RuntimeException("Utente non trovato: " + seguitoUsername));

        Segue s = new Segue();
        s.setSeguace(seguace);
        s.setSeguito(seguito);
        segueRepo.save(s);

        notificaService.crea(
            seguitoUsername, "FOLLOW",
            seguaceUsername, seguace.getNome() + " " + seguace.getCognome(),
            seguace.getId(), "UTENTE",
            seguace.getNome() + " ha iniziato a seguirti"
        );
    }

    @Transactional
    public void smettiDiSeguire(String seguaceUsername, String seguitoUsername) {
        segueRepo.findBySeguaceUsernameAndSeguitoUsername(seguaceUsername, seguitoUsername)
                .ifPresent(segueRepo::delete);
    }

    public boolean staSeguendo(String seguaceUsername, String seguitoUsername) {
        return segueRepo.existsBySeguaceUsernameAndSeguitoUsername(seguaceUsername, seguitoUsername);
    }

    public long countSeguaci(String username) {
        return segueRepo.countBySeguitoUsername(username);
    }

    public long countSeguiti(String username) {
        return segueRepo.countBySeguaceUsername(username);
    }

    public List<String> getSeguitiUsernames(String username) {
        return segueRepo.findSeguitiUsernamesBySeguace(username);
    }

    public List<ProfiloDto> getSeguaci(String username) {
        return segueRepo.findSeguaciBySeguitoUsername(username).stream()
                .map(DtoMapper::toProfiloDtoLight)
                .collect(Collectors.toList());
    }

    public List<ProfiloDto> getSeguiti(String username) {
        return segueRepo.findSeguitiBySeguaceUsername(username).stream()
                .map(DtoMapper::toProfiloDtoLight)
                .collect(Collectors.toList());
    }
}
