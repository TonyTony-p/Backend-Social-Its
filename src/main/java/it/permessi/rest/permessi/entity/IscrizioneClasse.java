package it.permessi.rest.permessi.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Table(
    name = "iscrizioni_classe",
    uniqueConstraints = @UniqueConstraint(columnNames = {"studente_id", "classe_id"})
)
@EntityListeners(AuditingEntityListener.class)
public class IscrizioneClasse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studente_id", nullable = false)
    private Utente studente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_id", nullable = false)
    private ClasseCorso classe;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatoIscrizione stato;

    @CreatedDate
    @Column(name = "data_richiesta", nullable = false, updatable = false)
    private Instant dataRichiesta;

    @Column(name = "data_risposta")
    private Instant dataRisposta;

    public enum StatoIscrizione { IN_ATTESA, APPROVATA, RIFIUTATA }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Utente getStudente() { return studente; }
    public void setStudente(Utente studente) { this.studente = studente; }
    public ClasseCorso getClasse() { return classe; }
    public void setClasse(ClasseCorso classe) { this.classe = classe; }
    public StatoIscrizione getStato() { return stato; }
    public void setStato(StatoIscrizione stato) { this.stato = stato; }
    public Instant getDataRichiesta() { return dataRichiesta; }
    public void setDataRichiesta(Instant dataRichiesta) { this.dataRichiesta = dataRichiesta; }
    public Instant getDataRisposta() { return dataRisposta; }
    public void setDataRisposta(Instant dataRisposta) { this.dataRisposta = dataRisposta; }
}
