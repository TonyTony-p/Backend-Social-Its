// NuovaPasswordRequest.java
package it.permessi.rest.permessi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NuovaPasswordRequest {
    @NotBlank(message = "Il codice è obbligatorio")
    private String codice;
    
    @NotBlank(message = "La password è obbligatoria")
    @Size(min = 6, message = "La password deve essere di almeno 6 caratteri")
    private String nuovaPassword;
    
    public String getCodice() { return codice; }
    public void setCodice(String codice) { this.codice = codice; }
    
    public String getNuovaPassword() { return nuovaPassword; }
    public void setNuovaPassword(String nuovaPassword) { this.nuovaPassword = nuovaPassword; }
}
