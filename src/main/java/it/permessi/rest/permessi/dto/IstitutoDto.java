package it.permessi.rest.permessi.dto;

import java.time.Instant;

public class IstitutoDto {
    private Long id;
    private String nome;
    private String descrizione;
    private String citta;
    private long numeroClassi;
    private Instant createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    public String getCitta() { return citta; }
    public void setCitta(String citta) { this.citta = citta; }
    public long getNumeroClassi() { return numeroClassi; }
    public void setNumeroClassi(long numeroClassi) { this.numeroClassi = numeroClassi; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
