package it.permessi.rest.permessi.dto;

import jakarta.validation.constraints.Size;

public class ValutazioneFormDto {
    private Integer voto;

    @Size(max = 1000)
    private String feedback;

    public Integer getVoto() { return voto; }
    public void setVoto(Integer voto) { this.voto = voto; }
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
}
