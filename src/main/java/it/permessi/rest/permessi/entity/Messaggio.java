package it.permessi.rest.permessi.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messaggi")
public class Messaggio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversazione_id", nullable = false)
    private Conversazione conversazione;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mittente_id", nullable = false)
    private Utente mittente;

    @Column(nullable = false, length = 2000)
    private String testo;

    /** ID del messaggio a cui si sta rispondendo (null se non è una risposta). */
    @Column(name = "reply_to_id")
    private Long replyToId;

    @Column(nullable = false)
    private boolean letto = false;

    @Column(nullable = false)
    private boolean eliminato = false;

    @Column(nullable = false)
    private boolean fissato = false;

    @Column(nullable = false)
    private boolean importante = false;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    /** Scadenza del pin (null = permanente oppure non fissato). */
    @Column(name = "pinned_until")
    private LocalDateTime pinnedUntil;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Conversazione getConversazione() { return conversazione; }
    public void setConversazione(Conversazione conversazione) { this.conversazione = conversazione; }
    public Utente getMittente() { return mittente; }
    public void setMittente(Utente mittente) { this.mittente = mittente; }
    public String getTesto() { return testo; }
    public void setTesto(String testo) { this.testo = testo; }
    public Long getReplyToId() { return replyToId; }
    public void setReplyToId(Long replyToId) { this.replyToId = replyToId; }
    public boolean isLetto() { return letto; }
    public void setLetto(boolean letto) { this.letto = letto; }
    public boolean isEliminato() { return eliminato; }
    public void setEliminato(boolean eliminato) { this.eliminato = eliminato; }
    public boolean isFissato() { return fissato; }
    public void setFissato(boolean fissato) { this.fissato = fissato; }
    public boolean isImportante() { return importante; }
    public void setImportante(boolean importante) { this.importante = importante; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getPinnedUntil() { return pinnedUntil; }
    public void setPinnedUntil(LocalDateTime pinnedUntil) { this.pinnedUntil = pinnedUntil; }
}
