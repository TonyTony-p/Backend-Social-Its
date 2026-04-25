package it.permessi.rest.permessi.dto;

import it.permessi.rest.permessi.entity.ClasseCorso.TipoClasse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ClasseCorsoFormDto {
    private Long id;

    @NotBlank(message = "Il nome è obbligatorio")
    @Size(max = 200, message = "Il nome non può superare 200 caratteri")
    private String nome;

    @Size(max = 1000, message = "La descrizione non può superare 1000 caratteri")
    private String descrizione;

    @NotNull(message = "Il tipo è obbligatorio")
    private TipoClasse tipo;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    public TipoClasse getTipo() { return tipo; }
    public void setTipo(TipoClasse tipo) { this.tipo = tipo; }
}
