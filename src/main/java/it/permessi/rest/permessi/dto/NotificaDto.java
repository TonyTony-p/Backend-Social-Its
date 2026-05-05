package it.permessi.rest.permessi.dto;

import java.time.LocalDateTime;

public class NotificaDto {
    private Long id;
    private String tipo;
    private String attoreUsername;
    private String attoreNome;
    private Long idRiferimento;
    private String tipoRiferimento;
    private String messaggio;
    private boolean letta;
    private LocalDateTime createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getAttoreUsername() { return attoreUsername; }
    public void setAttoreUsername(String attoreUsername) { this.attoreUsername = attoreUsername; }
    public String getAttoreNome() { return attoreNome; }
    public void setAttoreNome(String attoreNome) { this.attoreNome = attoreNome; }
    public Long getIdRiferimento() { return idRiferimento; }
    public void setIdRiferimento(Long idRiferimento) { this.idRiferimento = idRiferimento; }
    public String getTipoRiferimento() { return tipoRiferimento; }
    public void setTipoRiferimento(String tipoRiferimento) { this.tipoRiferimento = tipoRiferimento; }
    public String getMessaggio() { return messaggio; }
    public void setMessaggio(String messaggio) { this.messaggio = messaggio; }
    public boolean isLetta() { return letta; }
    public void setLetta(boolean letta) { this.letta = letta; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
