package it.permessi.rest.permessi.dto;

import java.time.Instant;

public class CompitoDto {
    private Long id;
    private Long classeId;
    private String titolo;
    private String descrizione;
    private Instant scadenza;
    private Integer puntiMax;
    private Instant createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClasseId() { return classeId; }
    public void setClasseId(Long classeId) { this.classeId = classeId; }
    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    public Instant getScadenza() { return scadenza; }
    public void setScadenza(Instant scadenza) { this.scadenza = scadenza; }
    public Integer getPuntiMax() { return puntiMax; }
    public void setPuntiMax(Integer puntiMax) { this.puntiMax = puntiMax; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
