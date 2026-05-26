package it.permessi.rest.permessi.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "conversazioni", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"utente1_id", "utente2_id"})
})
public class Conversazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utente1_id", nullable = false)
    private Utente utente1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utente2_id", nullable = false)
    private Utente utente2;

    @OneToMany(mappedBy = "conversazione", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createdAt ASC")
    private List<Messaggio> messaggi = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    protected void touch() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Utente getUtente1() { return utente1; }
    public void setUtente1(Utente utente1) { this.utente1 = utente1; }
    public Utente getUtente2() { return utente2; }
    public void setUtente2(Utente utente2) { this.utente2 = utente2; }
    public List<Messaggio> getMessaggi() { return messaggi; }
    public void setMessaggi(List<Messaggio> messaggi) { this.messaggi = messaggi; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
