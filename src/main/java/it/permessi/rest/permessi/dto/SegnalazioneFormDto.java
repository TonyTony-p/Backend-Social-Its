package it.permessi.rest.permessi.dto;

public class SegnalazioneFormDto {
    private Long idPost;
    private String motivo;

    public Long getIdPost() { return idPost; }
    public void setIdPost(Long idPost) { this.idPost = idPost; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}
