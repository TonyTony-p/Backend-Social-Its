package it.permessi.rest.permessi.dto;

import java.time.Instant;

public class AnnuncioDto {
    private Long id;
    private Long classeId;
    private String autoreUsername;
    private String autoreNome;
    private String titolo;
    private String contenuto;
    private Instant createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClasseId() { return classeId; }
    public void setClasseId(Long classeId) { this.classeId = classeId; }
    public String getAutoreUsername() { return autoreUsername; }
    public void setAutoreUsername(String autoreUsername) { this.autoreUsername = autoreUsername; }
    public String getAutoreNome() { return autoreNome; }
    public void setAutoreNome(String autoreNome) { this.autoreNome = autoreNome; }
    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }
    public String getContenuto() { return contenuto; }
    public void setContenuto(String contenuto) { this.contenuto = contenuto; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
