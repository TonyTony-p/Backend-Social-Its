package it.permessi.rest.permessi.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "annunci")
@EntityListeners(AuditingEntityListener.class)
public class Annuncio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_id", nullable = false)
    private ClasseCorso classe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autore_id", nullable = false)
    private Utente autore;

    @Column(nullable = false, length = 300)
    private String titolo;

    @Column(nullable = false, length = 3000)
    private String contenuto;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "annuncio_allegati", joinColumns = @JoinColumn(name = "annuncio_id"))
    private List<AllegatoAnnuncioInfo> allegati = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public ClasseCorso getClasse() { return classe; }
    public void setClasse(ClasseCorso classe) { this.classe = classe; }
    public Utente getAutore() { return autore; }
    public void setAutore(Utente autore) { this.autore = autore; }
    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }
    public String getContenuto() { return contenuto; }
    public void setContenuto(String contenuto) { this.contenuto = contenuto; }
    public List<AllegatoAnnuncioInfo> getAllegati() { return allegati; }
    public void setAllegati(List<AllegatoAnnuncioInfo> allegati) { this.allegati = allegati; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
