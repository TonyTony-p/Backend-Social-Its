package it.permessi.rest.permessi.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "opzioni_sondaggio")
public class OpzioneSondaggio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOpzione;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sondaggio_id", nullable = false)
    private Sondaggio sondaggio;

    @Column(nullable = false, length = 200)
    private String testo;

    @OneToMany(mappedBy = "opzione", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VotoSondaggio> voti = new ArrayList<>();

    public Long getIdOpzione() { return idOpzione; }
    public void setIdOpzione(Long idOpzione) { this.idOpzione = idOpzione; }
    public Sondaggio getSondaggio() { return sondaggio; }
    public void setSondaggio(Sondaggio sondaggio) { this.sondaggio = sondaggio; }
    public String getTesto() { return testo; }
    public void setTesto(String testo) { this.testo = testo; }
    public List<VotoSondaggio> getVoti() { return voti; }
    public void setVoti(List<VotoSondaggio> voti) { this.voti = voti; }
}
