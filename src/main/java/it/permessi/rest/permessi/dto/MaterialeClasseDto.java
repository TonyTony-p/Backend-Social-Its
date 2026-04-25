package it.permessi.rest.permessi.dto;

import it.permessi.rest.permessi.entity.MaterialeClasse.TipoMateriale;
import java.time.Instant;

public class MaterialeClasseDto {
    private Long id;
    private Long classeId;
    private String caricatoDaUsername;
    private String nome;
    private String url;
    private TipoMateriale tipo;
    private Instant dataCaricamento;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClasseId() { return classeId; }
    public void setClasseId(Long classeId) { this.classeId = classeId; }
    public String getCaricatoDaUsername() { return caricatoDaUsername; }
    public void setCaricatoDaUsername(String caricatoDaUsername) { this.caricatoDaUsername = caricatoDaUsername; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public TipoMateriale getTipo() { return tipo; }
    public void setTipo(TipoMateriale tipo) { this.tipo = tipo; }
    public Instant getDataCaricamento() { return dataCaricamento; }
    public void setDataCaricamento(Instant dataCaricamento) { this.dataCaricamento = dataCaricamento; }
}
