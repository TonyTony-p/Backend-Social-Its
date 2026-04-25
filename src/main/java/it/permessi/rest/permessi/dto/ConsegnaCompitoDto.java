package it.permessi.rest.permessi.dto;

import java.time.Instant;

public class ConsegnaCompitoDto {
    private Long id;
    private Long compitoId;
    private String compitoTitolo;
    private String studenteUsername;
    private String contenuto;
    private String url;
    private Instant dataConsegna;
    private Integer voto;
    private String feedback;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCompitoId() { return compitoId; }
    public void setCompitoId(Long compitoId) { this.compitoId = compitoId; }
    public String getCompitoTitolo() { return compitoTitolo; }
    public void setCompitoTitolo(String compitoTitolo) { this.compitoTitolo = compitoTitolo; }
    public String getStudenteUsername() { return studenteUsername; }
    public void setStudenteUsername(String studenteUsername) { this.studenteUsername = studenteUsername; }
    public String getContenuto() { return contenuto; }
    public void setContenuto(String contenuto) { this.contenuto = contenuto; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public Instant getDataConsegna() { return dataConsegna; }
    public void setDataConsegna(Instant dataConsegna) { this.dataConsegna = dataConsegna; }
    public Integer getVoto() { return voto; }
    public void setVoto(Integer voto) { this.voto = voto; }
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
}
