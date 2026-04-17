package it.permessi.rest.permessi.dto;


import jakarta.validation.constraints.NotNull;

public class LikeFormDto {
    

    @NotNull(message = "L'ID post è obbligatorio")
    private Long idPost;

    // Costruttori
    public LikeFormDto() {}

    public LikeFormDto(Long idPost) {
   
        this.idPost = idPost;
    }


    public Long getIdPost() {
        return idPost;
    }

    public void setIdPost(Long idPost) {
        this.idPost = idPost;
    }

}