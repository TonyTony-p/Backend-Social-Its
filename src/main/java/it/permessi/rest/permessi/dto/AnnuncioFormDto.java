package it.permessi.rest.permessi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AnnuncioFormDto {
    @NotBlank(message = "Il titolo è obbligatorio")
    @Size(max = 300, message = "Titolo max 300 caratteri")
    private String titolo;

    @NotBlank(message = "Il contenuto è obbligatorio")
    @Size(max = 3000, message = "Contenuto max 3000 caratteri")
    private String contenuto;

    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }
    public String getContenuto() { return contenuto; }
    public void setContenuto(String contenuto) { this.contenuto = contenuto; }
}
