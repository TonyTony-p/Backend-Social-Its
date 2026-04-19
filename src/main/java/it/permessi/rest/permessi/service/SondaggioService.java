package it.permessi.rest.permessi.service;

import it.permessi.rest.permessi.dto.SondaggioDto;
import it.permessi.rest.permessi.entity.*;
import it.permessi.rest.permessi.mapper.DtoMapper;
import it.permessi.rest.permessi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class SondaggioService {

    @Autowired private SondaggioRepository sondaggioRepo;
    @Autowired private OpzioneRepository opzioneRepo;
    @Autowired private VotoRepository votoRepo;
    @Autowired private UtenteRepository utenteRepo;

    @Transactional
    public Sondaggio crea(Post post, String domanda, List<String> testiOpzioni, Integer durataGiorni) {
        Sondaggio s = new Sondaggio();
        s.setPost(post);
        s.setDomanda(domanda.trim());
        if (durataGiorni != null && durataGiorni > 0) {
            s.setScadenza(Instant.now().plus(durataGiorni, ChronoUnit.DAYS));
        }
        Sondaggio saved = sondaggioRepo.save(s);
        for (String testo : testiOpzioni) {
            if (testo != null && !testo.isBlank()) {
                OpzioneSondaggio o = new OpzioneSondaggio();
                o.setSondaggio(saved);
                o.setTesto(testo.trim());
                saved.getOpzioni().add(opzioneRepo.save(o));
            }
        }
        post.setSondaggio(saved);
        return saved;
    }

    @Transactional
    public SondaggioDto vota(Long idOpzione, String username) {
        OpzioneSondaggio opzione = opzioneRepo.findById(idOpzione)
                .orElseThrow(() -> new RuntimeException("Opzione non trovata"));
        Sondaggio sondaggio = opzione.getSondaggio();

        if (sondaggio.isScaduto())
            throw new RuntimeException("Il sondaggio è scaduto");

        Utente utente = utenteRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        votoRepo.findBySondaggioAndUtente(sondaggio, utente).ifPresentOrElse(
            votoEsistente -> {
                votoEsistente.setOpzione(opzione);
                votoRepo.save(votoEsistente);
            },
            () -> {
                VotoSondaggio nuovoVoto = new VotoSondaggio();
                nuovoVoto.setOpzione(opzione);
                nuovoVoto.setSondaggio(sondaggio);
                nuovoVoto.setUtente(utente);
                votoRepo.save(nuovoVoto);
            }
        );

        Sondaggio aggiornato = sondaggioRepo.findById(sondaggio.getIdSondaggio()).orElse(sondaggio);
        return DtoMapper.toSondaggioDto(aggiornato, idOpzione);
    }
}
