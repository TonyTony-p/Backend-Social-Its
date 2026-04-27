package it.permessi.rest.permessi.dto;

public class AllegatoDto {

    private Long id;
    private String nomeOriginale;
    private String url;
    private String mimeType;
    private String tipo;

    public AllegatoDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNomeOriginale() { return nomeOriginale; }
    public void setNomeOriginale(String nomeOriginale) { this.nomeOriginale = nomeOriginale; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
