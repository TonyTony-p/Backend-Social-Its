package it.permessi.rest.permessi.service;

import it.permessi.rest.permessi.dto.*;
import it.permessi.rest.permessi.entity.*;
import it.permessi.rest.permessi.entity.ClasseCorso.TipoClasse;
import it.permessi.rest.permessi.entity.IscrizioneClasse.StatoIscrizione;
import it.permessi.rest.permessi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClasseCorsoService {

    @Autowired private ClasseCorsoRepository classeRepo;
    @Autowired private IscrizioneClasseRepository iscrizioneRepo;
    @Autowired private AnnuncioRepository annuncioRepo;
    @Autowired private MaterialeClasseRepository materialeRepo;
    @Autowired private CompitoRepository compitoRepo;
    @Autowired private ConsegnaCompitoRepository consegnaRepo;
    @Autowired private UtenteRepository utenteRepo;

    // ── ClasseCorso ──────────────────────────────────────────────

    @Transactional
    public ClasseCorsoDto creaClasse(ClasseCorsoFormDto form, String professorUsername) {
        Utente professore = utenteRepo.findByUsername(professorUsername)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        ClasseCorso classe = new ClasseCorso();
        classe.setNome(form.getNome());
        classe.setDescrizione(form.getDescrizione());
        classe.setTipo(form.getTipo());
        classe.setProfessore(professore);
        return toDto(classeRepo.save(classe));
    }

    @Transactional(readOnly = true)
    public Page<ClasseCorsoDto> listaClassiPubbliche(Pageable pageable) {
        return classeRepo.findByTipo(TipoClasse.PUBBLICA, pageable).map(this::toDto);
    }

    @Transactional(readOnly = true)
    public ClasseCorsoDto dettaglioClasse(Long id) {
        return toDto(findClasseOrThrow(id));
    }

    @Transactional
    public ClasseCorsoDto aggiornaClasse(Long id, ClasseCorsoFormDto form, String username) {
        ClasseCorso classe = findClasseOrThrow(id);
        verificaProprieta(classe, username);
        classe.setNome(form.getNome());
        classe.setDescrizione(form.getDescrizione());
        classe.setTipo(form.getTipo());
        return toDto(classeRepo.save(classe));
    }

    @Transactional
    public void eliminaClasse(Long id, String username) {
        ClasseCorso classe = findClasseOrThrow(id);
        verificaProprieta(classe, username);
        classeRepo.delete(classe);
    }

    @Transactional(readOnly = true)
    public List<ClasseCorsoDto> mieclassi(String username) {
        return classeRepo.findByProfessore_Username(username).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ── Iscrizioni ───────────────────────────────────────────────

    @Transactional
    public IscrizioneClasseDto iscriviti(Long classeId, String studenteUsername) {
        ClasseCorso classe = findClasseOrThrow(classeId);
        if (iscrizioneRepo.existsByStudente_UsernameAndClasse_Id(studenteUsername, classeId)) {
            throw new RuntimeException("Sei già iscritto a questa classe");
        }
        Utente studente = utenteRepo.findByUsername(studenteUsername)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        IscrizioneClasse iscrizione = new IscrizioneClasse();
        iscrizione.setStudente(studente);
        iscrizione.setClasse(classe);
        iscrizione.setStato(classe.getTipo() == TipoClasse.PUBBLICA
                ? StatoIscrizione.APPROVATA
                : StatoIscrizione.IN_ATTESA);
        return toIscrizioneDto(iscrizioneRepo.save(iscrizione));
    }

    @Transactional
    public IscrizioneClasseDto iscrivitiConCodice(String codiceInvito, String studenteUsername) {
        ClasseCorso classe = classeRepo.findByCodiceInvito(codiceInvito)
                .orElseThrow(() -> new RuntimeException("Codice invito non valido"));
        return iscriviti(classe.getId(), studenteUsername);
    }

    @Transactional(readOnly = true)
    public List<IscrizioneClasseDto> listaIscrizioni(Long classeId, String professorUsername) {
        verificaProprieta(findClasseOrThrow(classeId), professorUsername);
        return iscrizioneRepo.findByClasse_Id(classeId).stream()
                .map(this::toIscrizioneDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<IscrizioneClasseDto> listaStudentiApprovati(Long classeId) {
        return iscrizioneRepo.findByClasse_IdAndStato(classeId, StatoIscrizione.APPROVATA).stream()
                .map(this::toIscrizioneDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public IscrizioneClasseDto aggiornaIscrizione(Long classeId, Long iscrizioneId,
                                                   IscrizioneUpdateDto dto, String professorUsername) {
        verificaProprieta(findClasseOrThrow(classeId), professorUsername);
        IscrizioneClasse isc = iscrizioneRepo.findById(iscrizioneId)
                .orElseThrow(() -> new RuntimeException("Iscrizione non trovata"));
        if (!isc.getClasse().getId().equals(classeId)) {
            throw new RuntimeException("Iscrizione non appartiene a questa classe");
        }
        isc.setStato(dto.getStato());
        isc.setDataRisposta(Instant.now());
        return toIscrizioneDto(iscrizioneRepo.save(isc));
    }

    @Transactional
    public void abbandonaClasse(Long classeId, String studenteUsername) {
        IscrizioneClasse isc = iscrizioneRepo
                .findByStudente_UsernameAndClasse_Id(studenteUsername, classeId)
                .orElseThrow(() -> new RuntimeException("Non sei iscritto a questa classe"));
        iscrizioneRepo.delete(isc);
    }

    @Transactional(readOnly = true)
    public List<IscrizioneClasseDto> miIscrizioni(String username) {
        return iscrizioneRepo.findByStudente_Username(username).stream()
                .map(this::toIscrizioneDto)
                .collect(Collectors.toList());
    }

    // ── Annunci ──────────────────────────────────────────────────

    @Transactional
    public AnnuncioDto creaAnnuncio(Long classeId, AnnuncioFormDto form, String username) {
        ClasseCorso classe = findClasseOrThrow(classeId);
        Utente autore = utenteRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        Annuncio a = new Annuncio();
        a.setClasse(classe);
        a.setAutore(autore);
        a.setTitolo(form.getTitolo());
        a.setContenuto(form.getContenuto());
        return toAnnuncioDto(annuncioRepo.save(a));
    }

    @Transactional(readOnly = true)
    public List<AnnuncioDto> listaAnnunci(Long classeId) {
        return annuncioRepo.findByClasse_IdOrderByCreatedAtDesc(classeId).stream()
                .map(this::toAnnuncioDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void eliminaAnnuncio(Long classeId, Long annuncioId, String username) {
        Annuncio a = annuncioRepo.findById(annuncioId)
                .orElseThrow(() -> new RuntimeException("Annuncio non trovato"));
        if (!a.getClasse().getId().equals(classeId)) throw new RuntimeException("Annuncio non appartiene a questa classe");
        if (!a.getAutore().getUsername().equals(username) &&
                !a.getClasse().getProfessore().getUsername().equals(username)) {
            throw new RuntimeException("Non autorizzato");
        }
        annuncioRepo.delete(a);
    }

    // ── Materiali ────────────────────────────────────────────────

    @Transactional
    public MaterialeClasseDto caricaMateriale(Long classeId, MaterialeClasseFormDto form, String username) {
        ClasseCorso classe = findClasseOrThrow(classeId);
        Utente utente = utenteRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        MaterialeClasse m = new MaterialeClasse();
        m.setClasse(classe);
        m.setCaricatoDa(utente);
        m.setNome(form.getNome());
        m.setUrl(form.getUrl());
        m.setTipo(form.getTipo());
        return toMaterialeDto(materialeRepo.save(m));
    }

    @Transactional(readOnly = true)
    public List<MaterialeClasseDto> listaMateriali(Long classeId) {
        return materialeRepo.findByClasse_IdOrderByDataCaricamentoDesc(classeId).stream()
                .map(this::toMaterialeDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void eliminaMateriale(Long classeId, Long materialeId, String username) {
        MaterialeClasse m = materialeRepo.findById(materialeId)
                .orElseThrow(() -> new RuntimeException("Materiale non trovato"));
        if (!m.getClasse().getId().equals(classeId)) throw new RuntimeException("Materiale non appartiene a questa classe");
        if (!m.getCaricatoDa().getUsername().equals(username) &&
                !m.getClasse().getProfessore().getUsername().equals(username)) {
            throw new RuntimeException("Non autorizzato");
        }
        materialeRepo.delete(m);
    }

    // ── Compiti ──────────────────────────────────────────────────

    @Transactional
    public CompitoDto creaCompito(Long classeId, CompitoFormDto form, String username) {
        ClasseCorso classe = findClasseOrThrow(classeId);
        verificaProprieta(classe, username);
        Compito c = new Compito();
        c.setClasse(classe);
        c.setTitolo(form.getTitolo());
        c.setDescrizione(form.getDescrizione());
        c.setScadenza(form.getScadenza());
        c.setPuntiMax(form.getPuntiMax());
        return toCompitoDto(compitoRepo.save(c));
    }

    @Transactional(readOnly = true)
    public List<CompitoDto> listaCompiti(Long classeId) {
        return compitoRepo.findByClasse_IdOrderByCreatedAtDesc(classeId).stream()
                .map(this::toCompitoDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CompitoDto aggiornaCompito(Long classeId, Long compitoId, CompitoFormDto form, String username) {
        verificaProprieta(findClasseOrThrow(classeId), username);
        Compito c = compitoRepo.findById(compitoId)
                .orElseThrow(() -> new RuntimeException("Compito non trovato"));
        c.setTitolo(form.getTitolo());
        c.setDescrizione(form.getDescrizione());
        c.setScadenza(form.getScadenza());
        c.setPuntiMax(form.getPuntiMax());
        return toCompitoDto(compitoRepo.save(c));
    }

    @Transactional
    public void eliminaCompito(Long classeId, Long compitoId, String username) {
        verificaProprieta(findClasseOrThrow(classeId), username);
        compitoRepo.deleteById(compitoId);
    }

    // ── Consegne ─────────────────────────────────────────────────

    @Transactional
    public ConsegnaCompitoDto consegna(Long classeId, Long compitoId, ConsegnaCompitoFormDto form, String username) {
        Compito compito = compitoRepo.findById(compitoId)
                .orElseThrow(() -> new RuntimeException("Compito non trovato"));
        if (!compito.getClasse().getId().equals(classeId)) throw new RuntimeException("Compito non appartiene a questa classe");
        if (consegnaRepo.existsByCompito_IdAndStudente_Username(compitoId, username)) {
            throw new RuntimeException("Hai già consegnato questo compito");
        }
        Utente studente = utenteRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        ConsegnaCompito cc = new ConsegnaCompito();
        cc.setCompito(compito);
        cc.setStudente(studente);
        cc.setContenuto(form.getContenuto());
        cc.setUrl(form.getUrl());
        return toConsegnaDto(consegnaRepo.save(cc));
    }

    @Transactional(readOnly = true)
    public List<ConsegnaCompitoDto> listaConsegne(Long classeId, Long compitoId, String professorUsername) {
        verificaProprieta(findClasseOrThrow(classeId), professorUsername);
        return consegnaRepo.findByCompito_Id(compitoId).stream()
                .map(this::toConsegnaDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ConsegnaCompitoDto valutaConsegna(Long classeId, Long consegnaId, ValutazioneFormDto form, String professorUsername) {
        verificaProprieta(findClasseOrThrow(classeId), professorUsername);
        ConsegnaCompito cc = consegnaRepo.findById(consegnaId)
                .orElseThrow(() -> new RuntimeException("Consegna non trovata"));
        cc.setVoto(form.getVoto());
        cc.setFeedback(form.getFeedback());
        return toConsegnaDto(consegnaRepo.save(cc));
    }

    // ── Helpers ──────────────────────────────────────────────────

    private ClasseCorso findClasseOrThrow(Long id) {
        return classeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Classe non trovata"));
    }

    private void verificaProprieta(ClasseCorso classe, String username) {
        if (!classe.getProfessore().getUsername().equals(username)) {
            throw new RuntimeException("Non autorizzato: non sei il professore di questa classe");
        }
    }

    // ── Mapper ───────────────────────────────────────────────────

    private ClasseCorsoDto toDto(ClasseCorso c) {
        ClasseCorsoDto dto = toDtoLight(c);
        dto.setNumeroStudenti(iscrizioneRepo.countByClasse_IdAndStato(c.getId(), StatoIscrizione.APPROVATA));
        return dto;
    }

    private ClasseCorsoDto toDtoLight(ClasseCorso c) {
        ClasseCorsoDto dto = new ClasseCorsoDto();
        dto.setId(c.getId());
        dto.setNome(c.getNome());
        dto.setDescrizione(c.getDescrizione());
        dto.setCodiceInvito(c.getCodiceInvito());
        dto.setTipo(c.getTipo());
        dto.setProfessoreUsername(c.getProfessore().getUsername());
        dto.setProfessoreNome(c.getProfessore().getNome() + " " + c.getProfessore().getCognome());
        dto.setCreatedAt(c.getCreatedAt());
        return dto;
    }

    private IscrizioneClasseDto toIscrizioneDto(IscrizioneClasse i) {
        IscrizioneClasseDto dto = new IscrizioneClasseDto();
        dto.setId(i.getId());
        dto.setClasseId(i.getClasse().getId());
        dto.setClasseNome(i.getClasse().getNome());
        if (i.getClasse().getProfessore() != null) {
            dto.setProfessoreNome(i.getClasse().getProfessore().getNome() + " " + i.getClasse().getProfessore().getCognome());
        }
        dto.setStudenteUsername(i.getStudente().getUsername());
        dto.setStudenteNome(i.getStudente().getNome() + " " + i.getStudente().getCognome());
        dto.setStato(i.getStato());
        dto.setDataRichiesta(i.getDataRichiesta());
        dto.setDataRisposta(i.getDataRisposta());
        return dto;
    }

    private AnnuncioDto toAnnuncioDto(Annuncio a) {
        AnnuncioDto dto = new AnnuncioDto();
        dto.setId(a.getId());
        dto.setClasseId(a.getClasse().getId());
        dto.setAutoreUsername(a.getAutore().getUsername());
        dto.setAutoreNome(a.getAutore().getNome() + " " + a.getAutore().getCognome());
        dto.setTitolo(a.getTitolo());
        dto.setContenuto(a.getContenuto());
        dto.setCreatedAt(a.getCreatedAt());
        return dto;
    }

    private MaterialeClasseDto toMaterialeDto(MaterialeClasse m) {
        MaterialeClasseDto dto = new MaterialeClasseDto();
        dto.setId(m.getId());
        dto.setClasseId(m.getClasse().getId());
        dto.setCaricatoDaUsername(m.getCaricatoDa().getUsername());
        dto.setNome(m.getNome());
        dto.setUrl(m.getUrl());
        dto.setTipo(m.getTipo());
        dto.setDataCaricamento(m.getDataCaricamento());
        return dto;
    }

    private CompitoDto toCompitoDto(Compito c) {
        CompitoDto dto = new CompitoDto();
        dto.setId(c.getId());
        dto.setClasseId(c.getClasse().getId());
        dto.setTitolo(c.getTitolo());
        dto.setDescrizione(c.getDescrizione());
        dto.setScadenza(c.getScadenza());
        dto.setPuntiMax(c.getPuntiMax());
        dto.setCreatedAt(c.getCreatedAt());
        return dto;
    }

    private ConsegnaCompitoDto toConsegnaDto(ConsegnaCompito cc) {
        ConsegnaCompitoDto dto = new ConsegnaCompitoDto();
        dto.setId(cc.getId());
        dto.setCompitoId(cc.getCompito().getId());
        dto.setCompitoTitolo(cc.getCompito().getTitolo());
        dto.setStudenteUsername(cc.getStudente().getUsername());
        dto.setContenuto(cc.getContenuto());
        dto.setUrl(cc.getUrl());
        dto.setDataConsegna(cc.getDataConsegna());
        dto.setVoto(cc.getVoto());
        dto.setFeedback(cc.getFeedback());
        return dto;
    }
}
