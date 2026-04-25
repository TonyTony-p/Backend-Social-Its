package it.permessi.rest.permessi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.Instant;

public class CompitoFormDto {
    private Long id;

    @NotBlank(message = "Il titolo è obbligatorio")
    @Size(max = 300)
    private String titolo;

    @Size(max = 3000)
    private String descrizione;

    private Instant scadenza;
    private Integer puntiMax;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    public Instant getScadenza() { return scadenza; }
    public void setScadenza(Instant scadenza) { this.scadenza = scadenza; }
    public Integer getPuntiMax() { return puntiMax; }
    public void setPuntiMax(Integer puntiMax) { this.puntiMax = puntiMax; }
}
