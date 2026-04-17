package it.permessi.rest.permessi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class VerificaCodiceRequest {
    @NotBlank(message = "Il codice è obbligatorio")
    @Pattern(regexp = "\\d{4}", message = "Il codice deve essere di 4 cifre")
    private String codice;
    
    public String getCodice() { return codice; }
    public void setCodice(String codice) { this.codice = codice; }
}
