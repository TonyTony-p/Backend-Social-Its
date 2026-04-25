package it.permessi.rest.permessi.dto;

import it.permessi.rest.permessi.entity.IscrizioneClasse.StatoIscrizione;
import java.time.Instant;

public class IscrizioneClasseDto {
    private Long id;
    private Long classeId;
    private String classeNome;
    private String professoreNome;
    private String studenteUsername;
    private String studenteNome;
    private StatoIscrizione stato;
    private Instant dataRichiesta;
    private Instant dataRisposta;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClasseId() { return classeId; }
    public void setClasseId(Long classeId) { this.classeId = classeId; }
    public String getClasseNome() { return classeNome; }
    public void setClasseNome(String classeNome) { this.classeNome = classeNome; }
    public String getProfessoreNome() { return professoreNome; }
    public void setProfessoreNome(String professoreNome) { this.professoreNome = professoreNome; }
    public String getStudenteUsername() { return studenteUsername; }
    public void setStudenteUsername(String studenteUsername) { this.studenteUsername = studenteUsername; }
    public String getStudenteNome() { return studenteNome; }
    public void setStudenteNome(String studenteNome) { this.studenteNome = studenteNome; }
    public StatoIscrizione getStato() { return stato; }
    public void setStato(StatoIscrizione stato) { this.stato = stato; }
    public Instant getDataRichiesta() { return dataRichiesta; }
    public void setDataRichiesta(Instant dataRichiesta) { this.dataRichiesta = dataRichiesta; }
    public Instant getDataRisposta() { return dataRisposta; }
    public void setDataRisposta(Instant dataRisposta) { this.dataRisposta = dataRisposta; }
}
