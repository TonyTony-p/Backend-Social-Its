package it.permessi.rest.permessi.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * Form create/update per Utente (id nullo = create).
 */
public class UtenteFormDto {
    private Long id;

    @NotBlank(message = "Il nome è obbligatorio")
    private String nome;

    @NotBlank(message = "Il cognome è obbligatorio")
    private String cognome;

    @NotBlank(message = "L'email è obbligatoria")
    @Email(message = "Formato email non valido")
    private String email;


    @NotBlank(message = "L'username è obbligatorio")
    private String username;

    @NotBlank(message = "La password è obbligatoria in creazione")
    @Size(min = 8, message = "La password deve contenere almeno 8 caratteri")
    private String password;

    @Past(message = "La data di nascita deve essere nel passato")
    private LocalDate dataNascita;

    @Pattern(regexp = "^[0-9]{9,10}$", 
             message = "Il telefono deve contenere solo numeri (9-10 cifre)")
    private String telefono;

    private String indirizzo;

    @NotNull(message = "Il ruolo è obbligatorio e deve avere almeno l'id")
    private RuoloDto ruolo;

    // Getter e Setter
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public String getNome() { 
        return nome; 
    }
    
    public void setNome(String nome) { 
        this.nome = nome; 
    }
    
    public String getCognome() { 
        return cognome; 
    }
    
    public void setCognome(String cognome) { 
        this.cognome = cognome; 
    }
    
    public String getEmail() { 
        return email; 
    }
    
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    public String getUsername() { 
        return username; 
    }
    
    public void setUsername(String username) { 
        this.username = username; 
    }
    
    public String getPassword() { 
        return password; 
    }
    
    public void setPassword(String password) { 
        this.password = password; 
    }
    
    public LocalDate getDataNascita() { 
        return dataNascita; 
    }
    
    public void setDataNascita(LocalDate dataNascita) { 
        this.dataNascita = dataNascita; 
    }
    
    public String getTelefono() { 
        return telefono; 
    }
    
    public void setTelefono(String telefono) { 
        this.telefono = telefono; 
    }
    
    public String getIndirizzo() { 
        return indirizzo; 
    }
    
    public void setIndirizzo(String indirizzo) { 
        this.indirizzo = indirizzo; 
    }
    
    public RuoloDto getRuolo() { 
        return ruolo; 
    }
    
    public void setRuolo(RuoloDto ruolo) { 
        this.ruolo = ruolo; 
    }
}