package it.permessi.rest.permessi.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Table(name = "post_salvati",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_salvato_utente_post", columnNames = {"id_utente", "id_post"})
       })
@EntityListeners(AuditingEntityListener.class)
public class PostSalvato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utente", nullable = false)
    private Utente utente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_post", nullable = false)
    private Post post;

    @CreatedDate
    @Column(name = "salvato_at", nullable = false, updatable = false)
    private Instant salvatoAt;

    public PostSalvato() {}

    public PostSalvato(Utente utente, Post post) {
        this.utente = utente;
        this.post = post;
    }

    public Long getId() { return id; }
    public Utente getUtente() { return utente; }
    public void setUtente(Utente utente) { this.utente = utente; }
    public Post getPost() { return post; }
    public void setPost(Post post) { this.post = post; }
    public Instant getSalvatoAt() { return salvatoAt; }
}
