package it.permessi.rest.permessi.dto;

import it.permessi.rest.permessi.entity.MaterialeClasse.TipoMateriale;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MaterialeClasseFormDto {
    @NotBlank(message = "Il nome è obbligatorio")
    @Size(max = 200)
    private String nome;

    @NotBlank(message = "L'URL è obbligatorio")
    @Size(max = 2000)
    private String url;

    @NotNull(message = "Il tipo è obbligatorio")
    private TipoMateriale tipo;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public TipoMateriale getTipo() { return tipo; }
    public void setTipo(TipoMateriale tipo) { this.tipo = tipo; }
}
