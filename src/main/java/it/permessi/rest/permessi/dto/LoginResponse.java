package it.permessi.rest.permessi.dto;

import java.util.List;

/** Risposta login: token + info utente + liste di ruoli/permessi/gruppi. */
public class LoginResponse {
    private String token;
    private String username;
    private String nome;      // NEW
    private String cognome;   // NEW
    private List<RuoloDto> ruoli;
    private List<PermessoDto> permessi;
    private List<GruppoDto> gruppi;

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public List<RuoloDto> getRuoli() { return ruoli; }
    public void setRuoli(List<RuoloDto> ruoli) { this.ruoli = ruoli; }

    public List<PermessoDto> getPermessi() { return permessi; }
    public void setPermessi(List<PermessoDto> permessi) { this.permessi = permessi; }

    public List<GruppoDto> getGruppi() { return gruppi; }
    public void setGruppi(List<GruppoDto> gruppi) { this.gruppi = gruppi; }
}
