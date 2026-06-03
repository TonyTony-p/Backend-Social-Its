package it.permessi.rest.permessi.dto;

import java.time.Instant;

public class SegnalazioneDto {
    private Long id;
    private Long idPost;
    private String usernameUtente;
    private String motivo;
    private Instant createdAt;

    public SegnalazioneDto(Long id, Long idPost, String usernameUtente, String motivo, Instant createdAt) {
        this.id = id;
        this.idPost = idPost;
        this.usernameUtente = usernameUtente;
        this.motivo = motivo;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public Long getIdPost() { return idPost; }
    public String getUsernameUtente() { return usernameUtente; }
    public String getMotivo() { return motivo; }
    public Instant getCreatedAt() { return createdAt; }
}
