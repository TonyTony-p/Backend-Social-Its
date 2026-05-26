package it.permessi.rest.permessi.dto;

public class CambiaPasswordDto {
    private String vecchiaPassword;
    private String nuovaPassword;

    public String getVecchiaPassword() { return vecchiaPassword; }
    public void setVecchiaPassword(String vecchiaPassword) { this.vecchiaPassword = vecchiaPassword; }
    public String getNuovaPassword() { return nuovaPassword; }
    public void setNuovaPassword(String nuovaPassword) { this.nuovaPassword = nuovaPassword; }
}
