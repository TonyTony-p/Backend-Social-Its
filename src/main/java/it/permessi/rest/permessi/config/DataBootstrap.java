package it.permessi.rest.permessi.config;

import it.permessi.rest.permessi.entity.Permesso;
import it.permessi.rest.permessi.entity.Ruolo;
import it.permessi.rest.permessi.entity.RuoloPermesso;
import it.permessi.rest.permessi.entity.Utente;
import it.permessi.rest.permessi.repository.PermessoRepository;
import it.permessi.rest.permessi.repository.RuoloPermessoRepository;
import it.permessi.rest.permessi.repository.RuoloRepository;
import it.permessi.rest.permessi.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Popola il database al primo avvio con permessi, ruoli predefiniti e l'utente admin.
 * Idempotente: non duplica dati se eseguito più volte.
 */
@Component
public class DataBootstrap implements ApplicationRunner {

    @Autowired private PermessoRepository permessoRepository;
    @Autowired private RuoloRepository ruoloRepository;
    @Autowired private RuoloPermessoRepository ruoloPermessoRepository;
    @Autowired private UtenteRepository utenteRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    /** Tutti i permessi derivati dall'analisi dei controller. */
    private static final List<String[]> PERMESSI = List.of(
        // [alias, nome]
        new String[]{"UTENTE_CREATE",    "Crea Utente"},
        new String[]{"UTENTE_READ",      "Leggi Utente"},
        new String[]{"UTENTE_UPDATE",    "Aggiorna Utente"},
        new String[]{"UTENTE_DELETE",    "Elimina Utente"},
        new String[]{"POST_CREATE",      "Crea Post"},
        new String[]{"POST_READ",        "Leggi Post"},
        new String[]{"POST_UPDATE",      "Aggiorna Post"},
        new String[]{"POST_DELETE",      "Elimina Post"},
        new String[]{"COMMENTO_CREATE",  "Crea Commento"},
        new String[]{"COMMENTO_READ",    "Leggi Commento"},
        new String[]{"COMMENTO_UPDATE",  "Aggiorna Commento"},
        new String[]{"COMMENTO_DELETE",  "Elimina Commento"},
        new String[]{"LIKE_CREATE",      "Metti Like"},
        new String[]{"LIKE_READ",        "Leggi Like"},
        new String[]{"LIKE_DELETE",      "Rimuovi Like"},
        new String[]{"SEGUE_CREATE",     "Segui Utente"},
        new String[]{"SEGUE_DELETE",     "Smetti di Seguire"},
        new String[]{"RUOLO_CREATE",     "Crea Ruolo"},
        new String[]{"RUOLO_READ",       "Leggi Ruolo"},
        new String[]{"RUOLO_UPDATE",     "Aggiorna Ruolo"},
        new String[]{"RUOLO_DELETE",     "Elimina Ruolo"},
        new String[]{"GRUPPO_CREATE",    "Crea Gruppo"},
        new String[]{"GRUPPO_READ",      "Leggi Gruppo"},
        new String[]{"GRUPPO_UPDATE",    "Aggiorna Gruppo"},
        new String[]{"GRUPPO_DELETE",    "Elimina Gruppo"},
        new String[]{"SONDAGGIO_VOTE",   "Vota Sondaggio"},
        // Permessi sistema classi
        new String[]{"CLASSE_CREATE",    "Crea Classe"},
        new String[]{"CLASSE_READ",      "Leggi Classe"},
        new String[]{"CLASSE_UPDATE",    "Aggiorna Classe"},
        new String[]{"CLASSE_DELETE",    "Elimina Classe"},
        new String[]{"ISCRIZIONE_READ",  "Leggi Iscrizioni"},
        new String[]{"ISCRIZIONE_UPDATE","Approva/Rifiuta Iscrizione"},
        new String[]{"ANNUNCIO_CREATE",  "Crea Annuncio"},
        new String[]{"ANNUNCIO_READ",    "Leggi Annuncio"},
        new String[]{"ANNUNCIO_DELETE",  "Elimina Annuncio"},
        new String[]{"MATERIALE_CREATE", "Carica Materiale"},
        new String[]{"MATERIALE_READ",   "Leggi Materiale"},
        new String[]{"MATERIALE_DELETE", "Elimina Materiale"},
        new String[]{"COMPITO_CREATE",   "Crea Compito"},
        new String[]{"COMPITO_READ",     "Leggi Compito"},
        new String[]{"COMPITO_UPDATE",   "Aggiorna Compito"},
        new String[]{"COMPITO_DELETE",   "Elimina Compito"},
        new String[]{"CONSEGNA_CREATE",  "Consegna Compito"},
        new String[]{"CONSEGNA_READ",    "Leggi Consegna"},
        new String[]{"CONSEGNA_UPDATE",  "Valuta Consegna"},
        // Permesso specifico ISTITUTO
        new String[]{"PROFESSORE_CREATE","Crea Professore"}
    );

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        seedPermessi();
        Ruolo admin       = seedRuolo("ADMIN",      "admin",      true);
        Ruolo istituto    = seedRuolo("ISTITUTO",   "istituto",   false);
        Ruolo professore  = seedRuolo("PROFESSORE", "professore", false);
        Ruolo user        = seedRuolo("USER",        "user",       false);

        assegnaPermessiIstituto(istituto);
        assegnaPermessiProfessore(professore);
        assegnaPermessiUser(user);

        seedAdminUtente(admin);
    }

    private void seedPermessi() {
        for (String[] p : PERMESSI) {
            if (!permessoRepository.existsByAlias(p[0])) {
                Permesso permesso = new Permesso();
                permesso.setAlias(p[0]);
                permesso.setNome(p[1]);
                permessoRepository.save(permesso);
            }
        }
    }

    /** Crea ruolo se non esiste. Se isAdmin=true assegna TUTTI i permessi. */
    private Ruolo seedRuolo(String nome, String alias, boolean isAdmin) {
        Ruolo ruolo = ruoloRepository.findByNome(nome).orElseGet(() -> {
            Ruolo r = new Ruolo();
            r.setNome(nome);
            r.setAlias(alias);
            return ruoloRepository.save(r);
        });

        if (isAdmin) {
            List<Permesso> tutti = permessoRepository.findAll();
            for (Permesso p : tutti) {
                if (!ruoloPermessoRepository.existsByRuolo_IdAndPermesso_Id(ruolo.getId(), p.getId())) {
                    ruoloPermessoRepository.save(new RuoloPermesso(ruolo, p));
                }
            }
        }
        return ruolo;
    }

    /** ISTITUTO: può creare professori e leggere utenti. */
    private void assegnaPermessiIstituto(Ruolo istituto) {
        assegnaSeAssente(istituto, "PROFESSORE_CREATE");
        assegnaSeAssente(istituto, "UTENTE_READ");
    }

    /** PROFESSORE: gestione completa del sistema classi. */
    private void assegnaPermessiProfessore(Ruolo professore) {
        List<String> aliases = List.of(
            "CLASSE_CREATE", "CLASSE_READ", "CLASSE_UPDATE", "CLASSE_DELETE",
            "ISCRIZIONE_READ", "ISCRIZIONE_UPDATE",
            "ANNUNCIO_CREATE", "ANNUNCIO_READ", "ANNUNCIO_DELETE",
            "MATERIALE_CREATE", "MATERIALE_READ", "MATERIALE_DELETE",
            "COMPITO_CREATE", "COMPITO_READ", "COMPITO_UPDATE", "COMPITO_DELETE",
            "CONSEGNA_READ", "CONSEGNA_UPDATE",
            "POST_CREATE", "POST_READ", "POST_UPDATE", "POST_DELETE",
            "COMMENTO_CREATE", "COMMENTO_READ", "COMMENTO_UPDATE", "COMMENTO_DELETE",
            "LIKE_CREATE", "LIKE_READ", "LIKE_DELETE",
            "SEGUE_CREATE", "SEGUE_DELETE",
            "SONDAGGIO_VOTE"
        );
        for (String alias : aliases) assegnaSeAssente(professore, alias);
    }

    /** USER (studente): operazioni social + iscrizione classi. */
    private void assegnaPermessiUser(Ruolo user) {
        List<String> aliases = List.of(
            "POST_CREATE", "POST_READ", "POST_UPDATE", "POST_DELETE",
            "COMMENTO_CREATE", "COMMENTO_READ", "COMMENTO_UPDATE", "COMMENTO_DELETE",
            "LIKE_CREATE", "LIKE_READ", "LIKE_DELETE",
            "SEGUE_CREATE", "SEGUE_DELETE",
            "SONDAGGIO_VOTE",
            "CLASSE_READ",
            "ANNUNCIO_READ",
            "MATERIALE_READ",
            "COMPITO_READ",
            "CONSEGNA_CREATE", "CONSEGNA_READ"
        );
        for (String alias : aliases) assegnaSeAssente(user, alias);
    }

    private void assegnaSeAssente(Ruolo ruolo, String alias) {
        permessoRepository.findByAlias(alias).ifPresent(p -> {
            if (!ruoloPermessoRepository.existsByRuolo_IdAndPermesso_Id(ruolo.getId(), p.getId())) {
                ruoloPermessoRepository.save(new RuoloPermesso(ruolo, p));
            }
        });
    }

    private void seedAdminUtente(Ruolo adminRuolo) {
        if (utenteRepository.findByEmail("admin@admin.com").isPresent()) return;
        if (utenteRepository.findByUsername("admin").isPresent()) return;

        Utente admin = new Utente();
        admin.setUsername("admin");
        admin.setEmail("admin@admin.com");
        admin.setPassword(passwordEncoder.encode("Admin1234!"));
        admin.setNome("Admin");
        admin.setCognome("Sistema");
        admin.setRuolo(adminRuolo);
        utenteRepository.save(admin);
    }
}
