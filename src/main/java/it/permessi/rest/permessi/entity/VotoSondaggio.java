package it.permessi.rest.permessi.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Table(name = "voti_sondaggio",
    uniqueConstraints = @UniqueConstraint(columnNames = {"sondaggio_id", "utente_id"}))
@EntityListeners(AuditingEntityListener.class)
public class VotoSondaggio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVoto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opzione_id", nullable = false)
    private OpzioneSondaggio opzione;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sondaggio_id", nullable = false)
    private Sondaggio sondaggio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    public Long getIdVoto() { return idVoto; }
    public void setIdVoto(Long idVoto) { this.idVoto = idVoto; }
    public OpzioneSondaggio getOpzione() { return opzione; }
    public void setOpzione(OpzioneSondaggio opzione) { this.opzione = opzione; }
    public Sondaggio getSondaggio() { return sondaggio; }
    public void setSondaggio(Sondaggio sondaggio) { this.sondaggio = sondaggio; }
    public Utente getUtente() { return utente; }
    public void setUtente(Utente utente) { this.utente = utente; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
