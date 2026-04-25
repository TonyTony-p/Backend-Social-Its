package it.permessi.rest.permessi.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sondaggi")
@EntityListeners(AuditingEntityListener.class)
public class Sondaggio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSondaggio;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false, unique = true)
    private Post post;

    @Column(nullable = false, length = 300)
    private String domanda;

    @Column(name = "scadenza")
    private Instant scadenza;

    @OneToMany(mappedBy = "sondaggio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OpzioneSondaggio> opzioni = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    public boolean isScaduto() {
        return scadenza != null && Instant.now().isAfter(scadenza);
    }

    public Long getIdSondaggio() { return idSondaggio; }
    public void setIdSondaggio(Long idSondaggio) { this.idSondaggio = idSondaggio; }
    public Post getPost() { return post; }
    public void setPost(Post post) { this.post = post; }
    public String getDomanda() { return domanda; }
    public void setDomanda(String domanda) { this.domanda = domanda; }
    public Instant getScadenza() { return scadenza; }
    public void setScadenza(Instant scadenza) { this.scadenza = scadenza; }
    public List<OpzioneSondaggio> getOpzioni() { return opzioni; }
    public void setOpzioni(List<OpzioneSondaggio> opzioni) { this.opzioni = opzioni; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
