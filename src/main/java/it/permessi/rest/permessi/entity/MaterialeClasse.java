package it.permessi.rest.permessi.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Table(name = "materiali_classe")
@EntityListeners(AuditingEntityListener.class)
public class MaterialeClasse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_id", nullable = false)
    private ClasseCorso classe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caricato_da_id", nullable = false)
    private Utente caricatoDa;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, length = 2000)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMateriale tipo;

    @CreatedDate
    @Column(name = "data_caricamento", nullable = false, updatable = false)
    private Instant dataCaricamento;

    public enum TipoMateriale { LINK, FILE }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public ClasseCorso getClasse() { return classe; }
    public void setClasse(ClasseCorso classe) { this.classe = classe; }
    public Utente getCaricatoDa() { return caricatoDa; }
    public void setCaricatoDa(Utente caricatoDa) { this.caricatoDa = caricatoDa; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public TipoMateriale getTipo() { return tipo; }
    public void setTipo(TipoMateriale tipo) { this.tipo = tipo; }
    public Instant getDataCaricamento() { return dataCaricamento; }
    public void setDataCaricamento(Instant dataCaricamento) { this.dataCaricamento = dataCaricamento; }
}
