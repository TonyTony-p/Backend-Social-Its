package it.permessi.rest.permessi.dto;

import java.time.Instant;

public class CommentoAnnuncioDto {
    private Long id;
    private Long annuncioId;
    private String autoreUsername;
    private String autoreNome;
    private String testo;
    private Instant createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getAnnuncioId() { return annuncioId; }
    public void setAnnuncioId(Long annuncioId) { this.annuncioId = annuncioId; }
    public String getAutoreUsername() { return autoreUsername; }
    public void setAutoreUsername(String autoreUsername) { this.autoreUsername = autoreUsername; }
    public String getAutoreNome() { return autoreNome; }
    public void setAutoreNome(String autoreNome) { this.autoreNome = autoreNome; }
    public String getTesto() { return testo; }
    public void setTesto(String testo) { this.testo = testo; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
