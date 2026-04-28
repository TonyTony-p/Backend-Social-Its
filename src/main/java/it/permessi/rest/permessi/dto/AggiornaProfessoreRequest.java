package it.permessi.rest.permessi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AggiornaProfessoreRequest {

    @NotNull(message = "L'id è obbligatorio")
    private Long id;

    @NotBlank(message = "Il nome è obbligatorio")
    private String nome;

    @NotBlank(message = "Il cognome è obbligatorio")
    private String cognome;

    @Email(message = "Email non valida")
    @NotBlank(message = "L'email è obbligatoria")
    private String email;

    @NotBlank(message = "L'username è obbligatorio")
    private String username;

    @Size(min = 8, message = "La password deve avere almeno 8 caratteri")
    private String password;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
