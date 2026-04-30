package it.permessi.rest.permessi.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifiche")
public class Notifica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destinatario_id", nullable = false)
    private Utente destinatario;

    @Column(nullable = false, length = 50)
    private String tipo; // LIKE, COMMENTO, FOLLOW, ISCRIZIONE_RICHIESTA, ISCRIZIONE_APPROVATA, ISCRIZIONE_RIFIUTATA, ANNUNCIO

    private String attoreUsername;
    private String attoreNome;

    private Long idRiferimento;       // id del post / classe / annuncio
    private String tipoRiferimento;   // POST, CLASSE, ANNUNCIO

    @Column(length = 300)
    private String messaggio;

    @Column(nullable = false)
    private boolean letta = false;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Utente getDestinatario() { return destinatario; }
    public void setDestinatario(Utente destinatario) { this.destinatario = destinatario; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getAttoreUsername() { return attoreUsername; }
    public void setAttoreUsername(String attoreUsername) { this.attoreUsername = attoreUsername; }
    public String getAttoreNome() { return attoreNome; }
    public void setAttoreNome(String attoreNome) { this.attoreNome = attoreNome; }
    public Long getIdRiferimento() { return idRiferimento; }
    public void setIdRiferimento(Long idRiferimento) { this.idRiferimento = idRiferimento; }
    public String getTipoRiferimento() { return tipoRiferimento; }
    public void setTipoRiferimento(String tipoRiferimento) { this.tipoRiferimento = tipoRiferimento; }
    public String getMessaggio() { return messaggio; }
    public void setMessaggio(String messaggio) { this.messaggio = messaggio; }
    public boolean isLetta() { return letta; }
    public void setLetta(boolean letta) { this.letta = letta; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
