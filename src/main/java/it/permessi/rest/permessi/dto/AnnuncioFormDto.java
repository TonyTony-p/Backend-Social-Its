package it.permessi.rest.permessi.dto;

import it.permessi.rest.permessi.entity.AllegatoAnnuncioInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class AnnuncioFormDto {
    @NotBlank(message = "Il titolo è obbligatorio")
    @Size(max = 300, message = "Titolo max 300 caratteri")
    private String titolo;

    @Size(max = 3000, message = "Contenuto max 3000 caratteri")
    private String contenuto;

    private List<AllegatoAnnuncioInfo> allegati = new ArrayList<>();

    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }
    public String getContenuto() { return contenuto; }
    public void setContenuto(String contenuto) { this.contenuto = contenuto; }
    public List<AllegatoAnnuncioInfo> getAllegati() { return allegati; }
    public void setAllegati(List<AllegatoAnnuncioInfo> allegati) { this.allegati = allegati; }
}
