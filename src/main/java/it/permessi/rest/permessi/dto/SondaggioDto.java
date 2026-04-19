package it.permessi.rest.permessi.dto;

import java.util.List;

public class SondaggioDto {
    private Long idSondaggio;
    private String domanda;
    private String scadenza;
    private boolean scaduto;
    private int totaleVoti;
    private List<OpzioneDto> opzioni;
    private Long idOpzioneVotata;

    public Long getIdSondaggio() { return idSondaggio; }
    public void setIdSondaggio(Long idSondaggio) { this.idSondaggio = idSondaggio; }
    public String getDomanda() { return domanda; }
    public void setDomanda(String domanda) { this.domanda = domanda; }
    public String getScadenza() { return scadenza; }
    public void setScadenza(String scadenza) { this.scadenza = scadenza; }
    public boolean isScaduto() { return scaduto; }
    public void setScaduto(boolean scaduto) { this.scaduto = scaduto; }
    public int getTotaleVoti() { return totaleVoti; }
    public void setTotaleVoti(int totaleVoti) { this.totaleVoti = totaleVoti; }
    public List<OpzioneDto> getOpzioni() { return opzioni; }
    public void setOpzioni(List<OpzioneDto> opzioni) { this.opzioni = opzioni; }
    public Long getIdOpzioneVotata() { return idOpzioneVotata; }
    public void setIdOpzioneVotata(Long idOpzioneVotata) { this.idOpzioneVotata = idOpzioneVotata; }
}
