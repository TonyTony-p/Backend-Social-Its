package it.permessi.rest.permessi.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Table(name = "segue", uniqueConstraints = @UniqueConstraint(columnNames = {"seguace_id", "seguito_id"}))
@EntityListeners(AuditingEntityListener.class)
public class Segue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seguace_id", nullable = false)
    private Utente seguace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seguito_id", nullable = false)
    private Utente seguito;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Utente getSeguace() { return seguace; }
    public void setSeguace(Utente seguace) { this.seguace = seguace; }
    public Utente getSeguito() { return seguito; }
    public void setSeguito(Utente seguito) { this.seguito = seguito; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
