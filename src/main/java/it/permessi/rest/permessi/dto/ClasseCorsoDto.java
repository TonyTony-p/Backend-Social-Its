package it.permessi.rest.permessi.dto;

import it.permessi.rest.permessi.entity.ClasseCorso.TipoClasse;
import java.time.Instant;

public class ClasseCorsoDto {
    private Long id;
    private String nome;
    private String descrizione;
    private String codiceInvito;
    private TipoClasse tipo;
    private String professoreUsername;
    private String professoreNome;
    private long numeroStudenti;
    private Long istitutoId;
    private String istitutoNome;
    private Instant createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    public String getCodiceInvito() { return codiceInvito; }
    public void setCodiceInvito(String codiceInvito) { this.codiceInvito = codiceInvito; }
    public TipoClasse getTipo() { return tipo; }
    public void setTipo(TipoClasse tipo) { this.tipo = tipo; }
    public String getProfessoreUsername() { return professoreUsername; }
    public void setProfessoreUsername(String professoreUsername) { this.professoreUsername = professoreUsername; }
    public String getProfessoreNome() { return professoreNome; }
    public void setProfessoreNome(String professoreNome) { this.professoreNome = professoreNome; }
    public long getNumeroStudenti() { return numeroStudenti; }
    public void setNumeroStudenti(long numeroStudenti) { this.numeroStudenti = numeroStudenti; }
    public Long getIstitutoId() { return istitutoId; }
    public void setIstitutoId(Long istitutoId) { this.istitutoId = istitutoId; }
    public String getIstitutoNome() { return istitutoNome; }
    public void setIstitutoNome(String istitutoNome) { this.istitutoNome = istitutoNome; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
