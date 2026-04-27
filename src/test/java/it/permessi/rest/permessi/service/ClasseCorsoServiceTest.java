package it.permessi.rest.permessi.service;

import it.permessi.rest.permessi.dto.ClasseCorsoDto;
import it.permessi.rest.permessi.dto.ClasseCorsoFormDto;
import it.permessi.rest.permessi.dto.IscrizioneClasseDto;
import it.permessi.rest.permessi.entity.ClasseCorso;
import it.permessi.rest.permessi.entity.ClasseCorso.TipoClasse;
import it.permessi.rest.permessi.entity.IscrizioneClasse;
import it.permessi.rest.permessi.entity.IscrizioneClasse.StatoIscrizione;
import it.permessi.rest.permessi.entity.Utente;
import it.permessi.rest.permessi.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClasseCorsoServiceTest {

    @Mock private ClasseCorsoRepository classeRepo;
    @Mock private IscrizioneClasseRepository iscrizioneRepo;
    @Mock private AnnuncioRepository annuncioRepo;
    @Mock private MaterialeClasseRepository materialeRepo;
    @Mock private CompitoRepository compitoRepo;
    @Mock private ConsegnaCompitoRepository consegnaRepo;
    @Mock private UtenteRepository utenteRepo;

    @InjectMocks private ClasseCorsoService service;

    private Utente professore;
    private Utente studente;
    private ClasseCorso classe;

    @BeforeEach
    void setUp() {
        professore = new Utente();
        professore.setId(1L);
        professore.setUsername("prof1");
        professore.setNome("Mario");
        professore.setCognome("Rossi");

        studente = new Utente();
        studente.setId(2L);
        studente.setUsername("studente1");
        studente.setNome("Luca");
        studente.setCognome("Bianchi");

        classe = new ClasseCorso();
        classe.setId(10L);
        classe.setNome("Matematica");
        classe.setTipo(TipoClasse.PUBBLICA);
        classe.setProfessore(professore);
        classe.setCodiceInvito("ABCD1234");
    }

    @Test
    void creaClasse_salvaNuovaClasse() {
        ClasseCorsoFormDto form = new ClasseCorsoFormDto();
        form.setNome("Matematica");
        form.setDescrizione("Corso base");
        form.setTipo(TipoClasse.PUBBLICA);

        when(utenteRepo.findByUsername("prof1")).thenReturn(Optional.of(professore));
        when(classeRepo.save(any(ClasseCorso.class))).thenAnswer(inv -> {
            ClasseCorso c = inv.getArgument(0);
            c.setId(10L);
            return c;
        });
        when(iscrizioneRepo.countByClasse_IdAndStato(anyLong(), any())).thenReturn(0L);

        ClasseCorsoDto dto = service.creaClasse(form, "prof1");

        assertThat(dto.getNome()).isEqualTo("Matematica");
        assertThat(dto.getTipo()).isEqualTo(TipoClasse.PUBBLICA);
        verify(classeRepo).save(any(ClasseCorso.class));
    }

    @Test
    void creaClasse_utenteInesistente_lanceRuntimeException() {
        ClasseCorsoFormDto form = new ClasseCorsoFormDto();
        form.setNome("Test");
        form.setTipo(TipoClasse.PUBBLICA);

        when(utenteRepo.findByUsername("ghost")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.creaClasse(form, "ghost"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Utente non trovato");
    }

    @Test
    void aggiornaClasse_nonProprieta_lanceEccezione() {
        when(classeRepo.findById(10L)).thenReturn(Optional.of(classe));

        ClasseCorsoFormDto form = new ClasseCorsoFormDto();
        form.setNome("Nuovo nome");
        form.setTipo(TipoClasse.PRIVATA);

        assertThatThrownBy(() -> service.aggiornaClasse(10L, form, "altroUtente"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Non autorizzato");
    }

    @Test
    void iscriviti_classePubblica_statoApprovato() {
        when(classeRepo.findById(10L)).thenReturn(Optional.of(classe));
        when(iscrizioneRepo.existsByStudente_UsernameAndClasse_Id("studente1", 10L)).thenReturn(false);
        when(utenteRepo.findByUsername("studente1")).thenReturn(Optional.of(studente));
        when(iscrizioneRepo.save(any(IscrizioneClasse.class))).thenAnswer(inv -> {
            IscrizioneClasse i = inv.getArgument(0);
            i.setId(1L);
            return i;
        });

        IscrizioneClasseDto dto = service.iscriviti(10L, "studente1");

        assertThat(dto.getStato()).isEqualTo(StatoIscrizione.APPROVATA);
    }

    @Test
    void iscriviti_classePrivata_statoInAttesa() {
        classe.setTipo(TipoClasse.PRIVATA);
        when(classeRepo.findById(10L)).thenReturn(Optional.of(classe));
        when(iscrizioneRepo.existsByStudente_UsernameAndClasse_Id("studente1", 10L)).thenReturn(false);
        when(utenteRepo.findByUsername("studente1")).thenReturn(Optional.of(studente));
        when(iscrizioneRepo.save(any(IscrizioneClasse.class))).thenAnswer(inv -> {
            IscrizioneClasse i = inv.getArgument(0);
            i.setId(1L);
            return i;
        });

        IscrizioneClasseDto dto = service.iscriviti(10L, "studente1");

        assertThat(dto.getStato()).isEqualTo(StatoIscrizione.IN_ATTESA);
    }

    @Test
    void iscriviti_giàIscritto_lanceEccezione() {
        when(classeRepo.findById(10L)).thenReturn(Optional.of(classe));
        when(iscrizioneRepo.existsByStudente_UsernameAndClasse_Id("studente1", 10L)).thenReturn(true);

        assertThatThrownBy(() -> service.iscriviti(10L, "studente1"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("già iscritto");
    }

    @Test
    void eliminaClasse_nonProprieta_lanceEccezione() {
        when(classeRepo.findById(10L)).thenReturn(Optional.of(classe));

        assertThatThrownBy(() -> service.eliminaClasse(10L, "altroUtente"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Non autorizzato");

        verify(classeRepo, never()).delete(any());
    }
}
