package it.permessi.rest.permessi.dto;

import java.time.Instant;
import java.time.LocalDateTime;

public class CommentoDto {
	
	private Long idCommento;
	private UtenteDto utente;
	private PostDto post;
	private LocalDateTime dataOra;
	private String testo;
    private Instant createdAt;
    private Instant updatedAt;
    
	
	public Long getIdCommento() {
		return idCommento;
	}
	public void setIdCommento(Long idCommento) {
		this.idCommento = idCommento;
	}
	public UtenteDto getUtente() {
		return utente;
	}
	public void setUtente(UtenteDto utente) {
		this.utente = utente;
	}
	public PostDto getPost() {
		return post;
	}
	public void setPost(PostDto post) {
		this.post = post;
	}
	public LocalDateTime getDataOra() {
		return dataOra;
	}
	public void setDataOra(LocalDateTime dataOra) {
		this.dataOra = dataOra;
	}
	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
	public Instant getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
	public Instant getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
}
