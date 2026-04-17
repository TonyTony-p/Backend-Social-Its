package it.permessi.rest.permessi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

	import com.fasterxml.jackson.annotation.JsonFormat;
	import java.time.LocalDateTime;

	public class CommentoFormDto {
	    
	    
	    @NotNull(message = "L'ID post è obbligatorio")
	    private Long idPost;
	    
	    @NotBlank(message = "Il testo del commento è obbligatorio")
	    @Size(min = 1, max = 500, message = "Il commento deve essere tra 1 e 500 caratteri")
	    private String testo;
	    
	    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	    private LocalDateTime dataOra;

	    
	    public CommentoFormDto() {}
	    
	    public CommentoFormDto(Long idPost, String testo) {

	        this.idPost = idPost;
	        this.testo = testo;
	        this.dataOra = LocalDateTime.now();
	    }
	    
	    public CommentoFormDto(Long idUtente, Long idPost, String testo, LocalDateTime dataOra) {

	        this.idPost = idPost;
	        this.testo = testo;
	        this.dataOra = dataOra;
	    }

	    // Getter e Setter

	    public Long getIdPost() {
	        return idPost;
	    }
	    
	    public void setIdPost(Long idPost) {
	        this.idPost = idPost;
	    }
	    
	    public String getTesto() {
	        return testo;
	    }
	    
	    public void setTesto(String testo) {
	        this.testo = testo;
	    }
	    
	    public LocalDateTime getDataOra() {
	        return dataOra;
	    }
	    
	    public void setDataOra(LocalDateTime dataOra) {
	        this.dataOra = dataOra;
	    }
	    
	    // Metodi utili
	    public void setDataOraNow() {
	        this.dataOra = LocalDateTime.now();
	    }
	    

	}

