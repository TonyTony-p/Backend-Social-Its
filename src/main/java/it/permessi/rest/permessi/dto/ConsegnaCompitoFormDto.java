package it.permessi.rest.permessi.dto;

import jakarta.validation.constraints.Size;

public class ConsegnaCompitoFormDto {
    @Size(max = 5000)
    private String contenuto;

    @Size(max = 2000)
    private String url;

    public String getContenuto() { return contenuto; }
    public void setContenuto(String contenuto) { this.contenuto = contenuto; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}
