package it.permessi.rest.permessi.dto;


import java.time.LocalDateTime;
import java.util.List;

public class PostDto {
	private Long id;
	private Long idUtente;
	private String nomeUtente;
	private String usernameUtente;
	private String ruoloUtente;
	private LocalDateTime dataOra;
	private String contenuto;
	private List <CommentoDto> commenti;
	private List <LikeDto> like;
	private Integer numeroLike;
	private Integer numeroCommenti;
	private List<AllegatoDto> allegati;
	private SondaggioDto sondaggio;



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
	}
	
	public String getNomeUtente() {
		return nomeUtente;
	}
	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}
	public String getUsernameUtente() {
		return usernameUtente;
	}
	public void setUsernameUtente(String usernameUtente) {
		this.usernameUtente = usernameUtente;
	}
	
	public LocalDateTime getDataOra() {
		return dataOra;
	}
	public void setDataOra(LocalDateTime dataOra) {
		this.dataOra = dataOra;
	}
	public String getContenuto() {
		return contenuto;
	}
	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}
	public List<CommentoDto> getCommenti() {
		return commenti;
	}
	public void setCommenti(List<CommentoDto> commenti) {
		this.commenti = commenti;
	}
	public List<LikeDto> getLike() {
		return like;
	}
	public void setLike(List<LikeDto> like) {
		this.like = like;
	}

	
	//contiamo quanti like ha un post
    public Integer getNumeroLike() { 
    	return numeroLike; 
    }
    public void setNumeroLike(Integer numeroLike) {
    	this.numeroLike = numeroLike;
    }

    public Integer getNumeroCommenti() { return numeroCommenti; }
    public void setNumeroCommenti(Integer numeroCommenti) { this.numeroCommenti = numeroCommenti; }

    public List<AllegatoDto> getAllegati() { return allegati; }
    public void setAllegati(List<AllegatoDto> allegati) { this.allegati = allegati; }
    public SondaggioDto getSondaggio() { return sondaggio; }
    public void setSondaggio(SondaggioDto sondaggio) { this.sondaggio = sondaggio; }
    public String getRuoloUtente() { return ruoloUtente; }
    public void setRuoloUtente(String ruoloUtente) { this.ruoloUtente = ruoloUtente; }
}
