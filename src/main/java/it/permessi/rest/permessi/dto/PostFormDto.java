package it.permessi.rest.permessi.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PostFormDto {
    
	
	private Long id;
	
    @NotBlank(message = "Il contenuto non può essere vuoto")
    @Size(max = 1000, message = "Il contenuto non può superare i 1000 caratteri")
    private String contenuto;

    // Costruttori
    public PostFormDto() {}

    public PostFormDto(String contenuto, Long id) {
    	this.id = id;
        this.contenuto = contenuto;
    }

    // Getter e Setter
    
    public Long getId() { return id; }
    
    public void setId(Long id) { this.id = id; }
    

    public String getContenuto() {
        return contenuto;
    }

    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }

}