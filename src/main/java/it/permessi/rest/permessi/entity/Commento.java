package it.permessi.rest.permessi.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDateTime;


//commento puo avere un commento

@Entity
@Table(name = "commento")
@EntityListeners(AuditingEntityListener.class)
public class Commento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commento")
    private Long idCommento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utente", nullable = false)
    private Utente utente;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_post", nullable = true)
    private Post post;

    @Column(name = "data_ora")
    @CreatedDate
    private LocalDateTime dataOra;

    @Column(name = "testo", length = 500)
    private String testo;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;

    // Costruttori
    public Commento() {}

    public Commento(Utente utente, Post post, String testo) {
        this.utente = utente;
        this.post = post;
        this.testo = testo;
    }

    // Getter e Setter
    public Long getIdCommento() {
        return idCommento;
    }

    public void setIdCommento(Long idCommento) {
        this.idCommento = idCommento;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public LocalDateTime getDataOra() {
        return dataOra;
    }

    public void setDataOra(LocalDateTime dataOra) {
        this.dataOra = dataOra;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }
    
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }


    @Override
    public String toString() {
        return "Commento{" +
                "idCommento=" + idCommento +
                ", dataOra=" + dataOra +
                ", testo='" + testo + '\'' +
                '}';
    }
}