package it.permessi.rest.permessi.dto;

import java.time.LocalDateTime;

public class MessaggioDto {
    private Long id;
    private String mittenteUsername;
    private String testo;
    private boolean letto;
    private boolean eliminato;
    private boolean fissato;
    private boolean importante;
    /** ID del messaggio a cui si risponde */
    private Long replyToId;
    /** Testo anteprima del messaggio citato (già risolto lato server) */
    private String replyToTesto;
    /** Mittente del messaggio citato */
    private String replyToMittente;
    private LocalDateTime createdAt;
    private LocalDateTime pinnedUntil;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMittenteUsername() { return mittenteUsername; }
    public void setMittenteUsername(String mittenteUsername) { this.mittenteUsername = mittenteUsername; }
    public String getTesto() { return testo; }
    public void setTesto(String testo) { this.testo = testo; }
    public boolean isLetto() { return letto; }
    public void setLetto(boolean letto) { this.letto = letto; }
    public boolean isEliminato() { return eliminato; }
    public void setEliminato(boolean eliminato) { this.eliminato = eliminato; }
    public boolean isFissato() { return fissato; }
    public void setFissato(boolean fissato) { this.fissato = fissato; }
    public boolean isImportante() { return importante; }
    public void setImportante(boolean importante) { this.importante = importante; }
    public Long getReplyToId() { return replyToId; }
    public void setReplyToId(Long replyToId) { this.replyToId = replyToId; }
    public String getReplyToTesto() { return replyToTesto; }
    public void setReplyToTesto(String replyToTesto) { this.replyToTesto = replyToTesto; }
    public String getReplyToMittente() { return replyToMittente; }
    public void setReplyToMittente(String replyToMittente) { this.replyToMittente = replyToMittente; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getPinnedUntil() { return pinnedUntil; }
    public void setPinnedUntil(LocalDateTime pinnedUntil) { this.pinnedUntil = pinnedUntil; }
}
