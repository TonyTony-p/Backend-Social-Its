package it.permessi.rest.permessi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class IstitutoFormDto {

    @NotBlank(message = "Il nome è obbligatorio")
    @Size(max = 200, message = "Il nome non può superare 200 caratteri")
    private String nome;

    @Size(max = 1000, message = "La descrizione non può superare 1000 caratteri")
    private String descrizione;

    @Size(max = 100)
    private String citta;

    @Size(max = 500)
    private String url;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    public String getCitta() { return citta; }
    public void setCitta(String citta) { this.citta = citta; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}
