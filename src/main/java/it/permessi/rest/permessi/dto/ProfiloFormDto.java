package it.permessi.rest.permessi.dto;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/** Form per aggiornamento del proprio profilo (campi modificabili dall'utente). */
public class ProfiloFormDto {

    private String nome;
    private String cognome;

    @Size(max = 160, message = "La bio non può superare 160 caratteri")
    private String bio;

    private String fotoProfilo;

    @Past(message = "La data di nascita deve essere nel passato")
    private LocalDate dataNascita;

    @Pattern(regexp = "^[0-9+\\s()-]{7,15}$", message = "Formato telefono non valido")
    private String telefono;

    private String indirizzo;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public String getFotoProfilo() { return fotoProfilo; }
    public void setFotoProfilo(String fotoProfilo) { this.fotoProfilo = fotoProfilo; }
    public LocalDate getDataNascita() { return dataNascita; }
    public void setDataNascita(LocalDate dataNascita) { this.dataNascita = dataNascita; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getIndirizzo() { return indirizzo; }
    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }
}
