package it.permessi.rest.permessi.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Table(name = "compiti")
@EntityListeners(AuditingEntityListener.class)
public class Compito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_id", nullable = false)
    private ClasseCorso classe;

    @Column(nullable = false, length = 300)
    private String titolo;

    @Column(length = 3000)
    private String descrizione;

    @Column(name = "scadenza")
    private Instant scadenza;

    @Column(name = "punti_max")
    private Integer puntiMax;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public ClasseCorso getClasse() { return classe; }
    public void setClasse(ClasseCorso classe) { this.classe = classe; }
    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    public Instant getScadenza() { return scadenza; }
    public void setScadenza(Instant scadenza) { this.scadenza = scadenza; }
    public Integer getPuntiMax() { return puntiMax; }
    public void setPuntiMax(Integer puntiMax) { this.puntiMax = puntiMax; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
