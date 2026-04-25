package it.permessi.rest.permessi.dto;

import java.time.Instant;
import java.util.List;

/** DTO per la pagina profilo utente (include statistiche aggregate). */
public class ProfiloDto {
    private Long id;
    private String nome;
    private String cognome;
    private String username;
    private String bio;
    private String fotoProfilo;
    private int numPost;
    private int numLike;
    private int numSeguaci;
    private int numSeguiti;
    private boolean seguito;
    private List<PostDto> posts;
    private Instant memberDal;
    private String ruolo;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public String getFotoProfilo() { return fotoProfilo; }
    public void setFotoProfilo(String fotoProfilo) { this.fotoProfilo = fotoProfilo; }
    public int getNumPost() { return numPost; }
    public void setNumPost(int numPost) { this.numPost = numPost; }
    public int getNumLike() { return numLike; }
    public void setNumLike(int numLike) { this.numLike = numLike; }
    public int getNumSeguaci() { return numSeguaci; }
    public void setNumSeguaci(int numSeguaci) { this.numSeguaci = numSeguaci; }
    public int getNumSeguiti() { return numSeguiti; }
    public void setNumSeguiti(int numSeguiti) { this.numSeguiti = numSeguiti; }
    public boolean isSeguito() { return seguito; }
    public void setSeguito(boolean seguito) { this.seguito = seguito; }
    public List<PostDto> getPosts() { return posts; }
    public void setPosts(List<PostDto> posts) { this.posts = posts; }
    public Instant getMemberDal() { return memberDal; }
    public void setMemberDal(Instant memberDal) { this.memberDal = memberDal; }
    public String getRuolo() { return ruolo; }
    public void setRuolo(String ruolo) { this.ruolo = ruolo; }
}
