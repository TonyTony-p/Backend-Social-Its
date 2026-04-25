package it.permessi.rest.permessi.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "classi_corso")
@EntityListeners(AuditingEntityListener.class)
public class ClasseCorso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(length = 1000)
    private String descrizione;

    @Column(name = "codice_invito", nullable = false, unique = true)
    private String codiceInvito;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoClasse tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professore_id", nullable = false)
    private Utente professore;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;

    @PrePersist
    private void generaCodiceInvito() {
        if (this.codiceInvito == null) {
            this.codiceInvito = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        }
    }

    public enum TipoClasse { PUBBLICA, PRIVATA }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    public String getCodiceInvito() { return codiceInvito; }
    public void setCodiceInvito(String codiceInvito) { this.codiceInvito = codiceInvito; }
    public TipoClasse getTipo() { return tipo; }
    public void setTipo(TipoClasse tipo) { this.tipo = tipo; }
    public Utente getProfessore() { return professore; }
    public void setProfessore(Utente professore) { this.professore = professore; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
