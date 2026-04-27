package it.permessi.rest.permessi.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class AllegatoAnnuncioInfo {

    private String nome;
    private String url;
    private String tipo; // "IMAGE", "DOCUMENT", "LINK"

    public AllegatoAnnuncioInfo() {}

    public AllegatoAnnuncioInfo(String nome, String url, String tipo) {
        this.nome = nome;
        this.url = url;
        this.tipo = tipo;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
