package it.permessi.rest.permessi.dto;

import it.permessi.rest.permessi.entity.AllegatoAnnuncioInfo;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class AnnuncioDto {
    private Long id;
    private Long classeId;
    private String autoreUsername;
    private String autoreNome;
    private String titolo;
    private String contenuto;
    private List<AllegatoAnnuncioInfo> allegati = new ArrayList<>();
    private int numeroCommenti;
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
    public List<AllegatoAnnuncioInfo> getAllegati() { return allegati; }
    public void setAllegati(List<AllegatoAnnuncioInfo> allegati) { this.allegati = allegati; }
    public int getNumeroCommenti() { return numeroCommenti; }
    public void setNumeroCommenti(int numeroCommenti) { this.numeroCommenti = numeroCommenti; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
