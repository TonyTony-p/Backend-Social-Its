package it.permessi.rest.permessi.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ConversazioneDto {
    private Long id;
    private String altroUsername;
    private String altroNome;
    private String altroCognome;
    private String ultimoMessaggio;
    private String ultimoMittenteUsername;
    private LocalDateTime updatedAt;
    private int nonLetti;
    private LocalDateTime altroLastSeen;
    private List<MessaggioDto> messaggi;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAltroUsername() { return altroUsername; }
    public void setAltroUsername(String altroUsername) { this.altroUsername = altroUsername; }
    public String getAltroNome() { return altroNome; }
    public void setAltroNome(String altroNome) { this.altroNome = altroNome; }
    public String getAltroCognome() { return altroCognome; }
    public void setAltroCognome(String altroCognome) { this.altroCognome = altroCognome; }
    public String getUltimoMessaggio() { return ultimoMessaggio; }
    public void setUltimoMessaggio(String ultimoMessaggio) { this.ultimoMessaggio = ultimoMessaggio; }
    public String getUltimoMittenteUsername() { return ultimoMittenteUsername; }
    public void setUltimoMittenteUsername(String ultimoMittenteUsername) { this.ultimoMittenteUsername = ultimoMittenteUsername; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public int getNonLetti() { return nonLetti; }
    public void setNonLetti(int nonLetti) { this.nonLetti = nonLetti; }
    public LocalDateTime getAltroLastSeen() { return altroLastSeen; }
    public void setAltroLastSeen(LocalDateTime altroLastSeen) { this.altroLastSeen = altroLastSeen; }
    public List<MessaggioDto> getMessaggi() { return messaggi; }
    public void setMessaggi(List<MessaggioDto> messaggi) { this.messaggi = messaggi; }
}
