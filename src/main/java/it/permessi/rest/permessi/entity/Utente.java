package it.permessi.rest.permessi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entità Utente: appartiene ad un Ruolo.
 */
@Entity
@Table(name = "utenti")
@EntityListeners(AuditingEntityListener.class)
public class Utente {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) 
    private String nome;
    
    @Column(nullable = false) 
    private String cognome;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false) 
    private String password;

    @Column(name = "data_nascita")
    private LocalDate dataNascita;

    @Pattern(regexp = "^[0-9+\\s()-]{7,15}$", message = "Formato telefono non valido")
    @Column(length = 15)
    private String telefono;

    private String indirizzo;

    @Column(length = 160)
    private String bio;

    @Column(name = "foto_profilo")
    private String fotoProfilo;

    /** Ruolo assegnato all'utente. */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ruolo_id")
    private Ruolo ruolo;

    /** Lista dei post creati dall'utente. */
    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    /** Lista dei like dell'utente. */
    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    /** Lista dei commenti dell'utente. */
    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Commento> commenti = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "last_seen")
    private LocalDateTime lastSeen;

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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getFotoProfilo() {
        return fotoProfilo;
    }

    public void setFotoProfilo(String fotoProfilo) {
        this.fotoProfilo = fotoProfilo;
    }

    public Ruolo getRuolo() { 
        return ruolo; 
    }
    
    public void setRuolo(Ruolo ruolo) { 
        this.ruolo = ruolo; 
    }
    
    public List<Post> getPosts() { 
        return posts; 
    }
    
    public void setPosts(List<Post> posts) { 
        this.posts = posts; 
    }
    
    public List<Like> getLikes() { 
        return likes; 
    }
    
    public void setLikes(List<Like> likes) { 
        this.likes = likes; 
    }
    
    public List<Commento> getCommenti() { 
        return commenti; 
    }
    
    public void setCommenti(List<Commento> commenti) { 
        this.commenti = commenti; 
    }
    
    public Instant getCreatedAt() { 
        return createdAt; 
    }
    
    public void setCreatedAt(Instant createdAt) { 
        this.createdAt = createdAt; 
    }
    
    public Instant getUpdatedAt() { 
        return updatedAt; 
    }
    
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getLastSeen() { return lastSeen; }
    public void setLastSeen(LocalDateTime lastSeen) { this.lastSeen = lastSeen; }

}