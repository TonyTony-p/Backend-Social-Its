package it.permessi.rest.permessi.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class LikeDto {
    private Long idLike;
    private Long idUtente;
    private String nomeUtente; 
    private Long idPost;
    private LocalDateTime dataOra;

    // Costruttori
    public LikeDto() {}

    public LikeDto(Long idLike, Long idUtente, String nomeUtente, Long idPost, LocalDateTime dataOra) {
        this.idLike = idLike;
        this.idUtente = idUtente;
        this.nomeUtente = nomeUtente;
        this.idPost = idPost;
        this.dataOra = dataOra;
    }

    // Getter e Setter
    public Long getIdLike() { return idLike; }
    public void setIdLike(Long idLike) { this.idLike = idLike; }

    public Long getIdUtente() { return idUtente; }
    public void setIdUtente(Long idUtente) { this.idUtente = idUtente; }

    public String getNomeUtente() { return nomeUtente; }
    public void setNomeUtente(String nomeUtente) { this.nomeUtente = nomeUtente; }

    public Long getIdPost() { return idPost; }
    public void setIdPost(Long idPost) { this.idPost = idPost; }

    public LocalDateTime getDataOra() { return dataOra; }
    public void setDataOra(LocalDateTime dataOra) { this.dataOra = dataOra; }

    @Override
    public String toString() {
        return "LikeDto{" +
                "idLike=" + idLike +
                ", idUtente=" + idUtente +
                ", nomeUtente='" + nomeUtente + '\'' +
                ", idPost=" + idPost +
                ", dataOra=" + dataOra +
                '}';
    }
}