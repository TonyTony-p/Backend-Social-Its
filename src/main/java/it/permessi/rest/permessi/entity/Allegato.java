package it.permessi.rest.permessi.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Table(name = "allegato")
@EntityListeners(AuditingEntityListener.class)
public class Allegato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_allegato")
    private Long idAllegato;

    @Column(name = "nome_originale")
    private String nomeOriginale;

    @Column(name = "nome_file")
    private String nomeFile;

    @Column(name = "url")
    private String url;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "tipo")
    private String tipo; // "IMAGE" o "DOCUMENT"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_post", nullable = false)
    private Post post;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    public Allegato() {}

    public Long getIdAllegato() { return idAllegato; }
    public void setIdAllegato(Long idAllegato) { this.idAllegato = idAllegato; }
    public String getNomeOriginale() { return nomeOriginale; }
    public void setNomeOriginale(String nomeOriginale) { this.nomeOriginale = nomeOriginale; }
    public String getNomeFile() { return nomeFile; }
    public void setNomeFile(String nomeFile) { this.nomeFile = nomeFile; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Post getPost() { return post; }
    public void setPost(Post post) { this.post = post; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
