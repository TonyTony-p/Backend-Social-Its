package it.permessi.rest.permessi.dto;

public class OpzioneDto {
    private Long idOpzione;
    private String testo;
    private int numVoti;
    private int percentuale;

    public Long getIdOpzione() { return idOpzione; }
    public void setIdOpzione(Long idOpzione) { this.idOpzione = idOpzione; }
    public String getTesto() { return testo; }
    public void setTesto(String testo) { this.testo = testo; }
    public int getNumVoti() { return numVoti; }
    public void setNumVoti(int numVoti) { this.numVoti = numVoti; }
    public int getPercentuale() { return percentuale; }
    public void setPercentuale(int percentuale) { this.percentuale = percentuale; }
}
