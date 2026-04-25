package it.permessi.rest.permessi.dto;

import it.permessi.rest.permessi.entity.IscrizioneClasse.StatoIscrizione;
import jakarta.validation.constraints.NotNull;

public class IscrizioneUpdateDto {
    @NotNull(message = "Lo stato è obbligatorio")
    private StatoIscrizione stato;

    public StatoIscrizione getStato() { return stato; }
    public void setStato(StatoIscrizione stato) { this.stato = stato; }
}
