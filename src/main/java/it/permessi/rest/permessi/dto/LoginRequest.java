package it.permessi.rest.permessi.dto;

import jakarta.validation.constraints.NotBlank;

/** Body del login (email/password). */
public class LoginRequest {
    @NotBlank(message = "L'username è obbligatorio")
    private String username;

    @NotBlank(message = "La password è obbligatoria")
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
