package it.permessi.rest.permessi.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Table(
    name = "consegne_compito",
    uniqueConstraints = @UniqueConstraint(columnNames = {"compito_id", "studente_id"})
)
@EntityListeners(AuditingEntityListener.class)
public class ConsegnaCompito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compito_id", nullable = false)
    private Compito compito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studente_id", nullable = false)
    private Utente studente;

    @Column(length = 5000)
    private String contenuto;

    @Column(length = 2000)
    private String url;

    @CreatedDate
    @Column(name = "data_consegna", nullable = false, updatable = false)
    private Instant dataConsegna;

    private Integer voto;

    @Column(length = 1000)
    private String feedback;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Compito getCompito() { return compito; }
    public void setCompito(Compito compito) { this.compito = compito; }
    public Utente getStudente() { return studente; }
    public void setStudente(Utente studente) { this.studente = studente; }
    public String getContenuto() { return contenuto; }
    public void setContenuto(String contenuto) { this.contenuto = contenuto; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public Instant getDataConsegna() { return dataConsegna; }
    public void setDataConsegna(Instant dataConsegna) { this.dataConsegna = dataConsegna; }
    public Integer getVoto() { return voto; }
    public void setVoto(Integer voto) { this.voto = voto; }
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
}
