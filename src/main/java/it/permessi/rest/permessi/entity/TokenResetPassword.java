package it.permessi.rest.permessi.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "token_reset_pass")
public class TokenResetPassword {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String codice; 
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "utente_id")
    private Utente utente;
    
    private LocalDateTime dataScadenza; // Valido 15 minuti
    
    private int tentativiRimasti = 3; 	// Max 3 tentativi
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getCodice() { return codice; }
    public void setCodice(String codice) { this.codice = codice; }
    
    public Utente getUtente() { return utente; }
    public void setUtente(Utente utente) { this.utente = utente; }
    
    public LocalDateTime getDataScadenza() { return dataScadenza; }
    public void setDataScadenza(LocalDateTime dataScadenza) { this.dataScadenza = dataScadenza; }
    
    public int getTentativiRimasti() { return tentativiRimasti; }
    public void setTentativiRimasti(int tentativiRimasti) { this.tentativiRimasti = tentativiRimasti; }
}