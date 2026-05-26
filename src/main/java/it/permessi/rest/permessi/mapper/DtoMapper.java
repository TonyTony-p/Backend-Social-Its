package it.permessi.rest.permessi.mapper;

import it.permessi.rest.permessi.dto.AllegatoDto;
import it.permessi.rest.permessi.dto.CommentoDto;
import it.permessi.rest.permessi.dto.GruppoDto;
import it.permessi.rest.permessi.dto.LikeDto;
import it.permessi.rest.permessi.dto.OpzioneDto;
import it.permessi.rest.permessi.dto.PermessoDto;
import it.permessi.rest.permessi.dto.PermessoRuoloDto;
import it.permessi.rest.permessi.dto.PostDto;
import it.permessi.rest.permessi.dto.ProfiloDto;
import it.permessi.rest.permessi.dto.RuoloDto;
import it.permessi.rest.permessi.dto.SondaggioDto;
import it.permessi.rest.permessi.dto.UtenteDto;
import it.permessi.rest.permessi.entity.Allegato;
import it.permessi.rest.permessi.entity.Commento;
import it.permessi.rest.permessi.entity.Gruppo;
import it.permessi.rest.permessi.entity.Like;
import it.permessi.rest.permessi.entity.OpzioneSondaggio;
import it.permessi.rest.permessi.entity.Permesso;
import it.permessi.rest.permessi.entity.Post;
import it.permessi.rest.permessi.entity.Ruolo;
import it.permessi.rest.permessi.entity.RuoloPermesso;
import it.permessi.rest.permessi.entity.Sondaggio;
import it.permessi.rest.permessi.entity.Utente;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper Entity -> DTO con metodi ESPLICITI (niente overload con booleani),
 * per evitare confusione ed eliminare ricorsioni.
 */
public class DtoMapper {

   
    /** Gruppo "light": solo campi del gruppo, senza lista dei permessi. */
    public static GruppoDto toGruppoDtoLight(Gruppo g) {
        if (g == null) return null;
        GruppoDto dto = new GruppoDto();
        dto.setId(g.getId());
        dto.setNome(g.getNome());
        dto.setAlias(g.getAlias());
        dto.setCreatedAt(g.getCreatedAt());
        dto.setUpdatedAt(g.getUpdatedAt());
        return dto;
    }

    /** Gruppo "withPermessi": include anche la lista dei PermessoDto (light). */
    public static GruppoDto toGruppoDtoWithPermessi(Gruppo g) {
        if (g == null) return null;
        GruppoDto dto = toGruppoDtoLight(g);
        if (g.getPermessi() != null) {
            dto.setPermessi(
                g.getPermessi().stream()
                  .map(DtoMapper::toPermessoDtoLight)  // permesso light
                  .collect(Collectors.toList())
            );
        }
        return dto;
    }

    
    /** Permesso "light": solo campi + gruppo (light). */
    public static PermessoDto toPermessoDtoLight(Permesso p) {
        if (p == null) return null;
        PermessoDto dto = new PermessoDto();
        dto.setId(p.getId());
        dto.setNome(p.getNome());
        dto.setAlias(p.getAlias());
        dto.setGruppo(toGruppoDtoLight(p.getGruppo())); // gruppo light
        dto.setCreatedAt(p.getCreatedAt());
        dto.setUpdatedAt(p.getUpdatedAt());
        return dto;
    }

    /**
     * Permesso "withAssoc": include anche la lista di associazioni ruolo-permesso.
     * Ogni associazione è mappata in modo "safe" (ruolo/permesso light) per evitare ricorsioni.
     */
    public static PermessoDto toPermessoDtoWithAssoc(Permesso p) {
        if (p == null) return null;
        PermessoDto dto = toPermessoDtoLight(p);
        if (p.getRuoloPermessi() != null) {
            dto.setRuoloPermessi(
                p.getRuoloPermessi().stream()
                  .map(DtoMapper::toRuoloPermessoDtoSafe) // safe: nested light
                  .collect(Collectors.toList())
            );
        }
        return dto;
    }

    
    /** Ruolo "light": solo campi base, senza lista associazioni. */
    public static RuoloDto toRuoloDtoLight(Ruolo r) {
        if (r == null) return null;
        RuoloDto dto = new RuoloDto();
        dto.setId(r.getId());
        dto.setNome(r.getNome());
        dto.setAlias(r.getAlias());
        dto.setCreatedAt(r.getCreatedAt());
        dto.setUpdatedAt(r.getUpdatedAt());
        return dto;
    }

    /**
     * Ruolo "withAssoc": include la lista di associazioni ruolo-permesso.
     * Ogni associazione è mappata in modo "safe" (ruolo/permesso light) per evitare ricorsioni.
     */
    public static RuoloDto toRuoloDtoWithAssoc(Ruolo r) {
        if (r == null) return null;
        RuoloDto dto = toRuoloDtoLight(r);
        if (r.getRuoloPermessi() != null) {
            dto.setRuoloPermessi(
                r.getRuoloPermessi().stream()
                  .map(DtoMapper::toRuoloPermessoDtoSafe) // safe: nested light
                  .collect(Collectors.toList())
            );
        }
        return dto;
    }

    
    /**
     * Associazione "safe": mappa RuoloPermesso con:
     * - Ruolo LIGHT (senza lista ruoloPermessi)
     * - Permesso LIGHT (senza lista ruoloPermessi)
     * per evitare strutture annidate infinite.
     */
    public static PermessoRuoloDto toRuoloPermessoDtoSafe(RuoloPermesso rp) {
        if (rp == null) return null;

        PermessoRuoloDto dto = new PermessoRuoloDto();
        dto.setId(rp.getId());

        // Ruolo light
        Ruolo ruolo = rp.getRuolo();
        if (ruolo != null) {
            dto.setRuolo(toRuoloDtoLight(ruolo));
        }

        // Permesso light
        Permesso perm = rp.getPermesso();
        if (perm != null) {
            dto.setPermesso(toPermessoDtoLight(perm));
        }

        return dto;
    }

    
    /**
     * UtenteDto con Ruolo LIGHT (niente lista di associazioni),
     * sufficiente per la maggior parte degli scenari e senza ricorsioni.
     */
    public static UtenteDto toUtenteDto(Utente u) {
        if (u == null) return null;
        UtenteDto dto = new UtenteDto();
        dto.setId(u.getId());
        dto.setNome(u.getNome());
        dto.setCognome(u.getCognome());
        dto.setEmail(u.getEmail());
        dto.setUsername(u.getUsername());
        dto.setDataNascita(u.getDataNascita());
        dto.setTelefono(u.getTelefono());
        dto.setIndirizzo(u.getIndirizzo());
        dto.setRuolo(toRuoloDtoLight(u.getRuolo())); // ruolo light
        dto.setCreatedAt(u.getCreatedAt());
        dto.setUpdatedAt(u.getUpdatedAt());
        return dto;
    }
    
    public static UtenteDto toUtenteDtoLight(Utente u) {
    	if (u == null) return null;
    	UtenteDto dto = new UtenteDto();
    	dto.setNome(u.getNome());
    	dto.setCognome(u.getCognome());
    	dto.setUsername(u.getUsername());
    	return dto;
    }
    
    public static UtenteDto toUtenteDtoWithPosts(Utente utente) {
        if (utente == null) return null;
        
        UtenteDto dto = toUtenteDto(utente); 
        
        // Aggiungi i post se presenti
        if (utente.getPosts() != null) {
            dto.setPost(utente.getPosts().stream()  
                    .map(DtoMapper::toPostDtoLight)
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }
    
    public static LikeDto toLikeDtoLight(Like l) {
        if (l == null) return null;
        
        LikeDto dto = new LikeDto();
        dto.setIdLike(l.getIdLike());
        dto.setDataOra(l.getDataOra());
        
        
        if (l.getUtente() != null) {
            dto.setIdUtente(l.getUtente().getId());
            dto.setNomeUtente(l.getUtente().getNome()); 
        }
        
        // Solo ID post
        if (l.getPost() != null) {
            dto.setIdPost(l.getPost().getIdPost());
        }
        
        return dto;
    }
    
    
    public static LikeDto toLikeDtoMinimal(Like l) {
        if (l == null) return null;

        LikeDto dto = new LikeDto();
        dto.setIdLike(l.getIdLike());
        dto.setDataOra(l.getDataOra());

        if (l.getUtente() != null) {
            dto.setNomeUtente(l.getUtente().getNome());
        }

        return dto;
    }
    
    
    
    public static PostDto toPostDtoComplete(Post p) {
        if (p == null) return null;

        PostDto dto = new PostDto();
        
        dto.setId(p.getIdPost());
        dto.setContenuto(p.getContenuto());
        dto.setDataOra(p.getDataOra());


        if (p.getUtente() != null) {
            dto.setIdUtente(p.getUtente().getId());
            dto.setNomeUtente(p.getUtente().getNome());
            dto.setUsernameUtente(p.getUtente().getUsername());
            if (p.getUtente().getRuolo() != null) dto.setRuoloUtente(p.getUtente().getRuolo().getNome());
        }

        // Mappa i like
        if (p.getLikes() != null && !p.getLikes().isEmpty()) {
            List<LikeDto> likeDtos = p.getLikes().stream()
                .map(DtoMapper::toLikeDtoMinimal)
                .collect(Collectors.toList());
            dto.setLike(likeDtos);
            dto.setNumeroLike(p.getLikes().size());
        } else {
            dto.setNumeroLike(0);
        }

        if (p.getCommenti() != null && !p.getCommenti().isEmpty()) {
            List<CommentoDto> commentiDtos = p.getCommenti().stream()
                .map(DtoMapper::toCommentoDtoLight)
                .collect(Collectors.toList());
            dto.setCommenti(commentiDtos);
        }

        if (p.getAllegati() != null && !p.getAllegati().isEmpty()) {
            List<AllegatoDto> allegatiDtos = p.getAllegati().stream()
                .map(DtoMapper::toAllegatoDto)
                .collect(Collectors.toList());
            dto.setAllegati(allegatiDtos);
        }

        return dto;
    }

    public static SondaggioDto toSondaggioDto(Sondaggio s, Long idOpzioneVotata) {
        if (s == null) return null;
        SondaggioDto dto = new SondaggioDto();
        dto.setIdSondaggio(s.getIdSondaggio());
        dto.setDomanda(s.getDomanda());
        if (s.getScadenza() != null) dto.setScadenza(s.getScadenza().toString());
        dto.setScaduto(s.isScaduto());
        dto.setIdOpzioneVotata(idOpzioneVotata);
        if (s.getOpzioni() != null) {
            int totale = s.getOpzioni().stream()
                .mapToInt(o -> o.getVoti() != null ? o.getVoti().size() : 0).sum();
            dto.setTotaleVoti(totale);
            dto.setOpzioni(s.getOpzioni().stream()
                .map(o -> toOpzioneDto(o, totale))
                .collect(Collectors.toList()));
        }
        return dto;
    }

    public static OpzioneDto toOpzioneDto(OpzioneSondaggio o, int totale) {
        if (o == null) return null;
        OpzioneDto dto = new OpzioneDto();
        dto.setIdOpzione(o.getIdOpzione());
        dto.setTesto(o.getTesto());
        int numVoti = o.getVoti() != null ? o.getVoti().size() : 0;
        dto.setNumVoti(numVoti);
        dto.setPercentuale(totale > 0 ? (int) Math.round((double) numVoti / totale * 100) : 0);
        return dto;
    }
    
    
 
    
    public static PostDto toPostDtoLight(Post p) {
        if (p == null) return null;

        PostDto dto = new PostDto();

        dto.setId(p.getIdPost());
        dto.setNomeUtente(p.getUtente().getNome());
        dto.setUsernameUtente(p.getUtente().getUsername());
        dto.setContenuto(p.getContenuto());
        dto.setDataOra(p.getDataOra());
        if (p.getUtente().getRuolo() != null) dto.setRuoloUtente(p.getUtente().getRuolo().getNome());

        return dto;
    }

    /** Per la pagina profilo: include conteggi like/commenti e allegati (senza lista completa like). */
    public static PostDto toPostDtoForProfile(Post p) {
        if (p == null) return null;

        PostDto dto = new PostDto();
        dto.setId(p.getIdPost());
        dto.setIdUtente(p.getUtente().getId());
        dto.setNomeUtente(p.getUtente().getNome() + " " + p.getUtente().getCognome());
        dto.setUsernameUtente(p.getUtente().getUsername());
        dto.setContenuto(p.getContenuto());
        dto.setDataOra(p.getDataOra());
        if (p.getUtente().getRuolo() != null) dto.setRuoloUtente(p.getUtente().getRuolo().getNome());

        dto.setNumeroLike(p.getLikes() != null ? p.getLikes().size() : 0);
        dto.setNumeroCommenti(p.getCommenti() != null ? p.getCommenti().size() : 0);

        if (p.getCommenti() != null && !p.getCommenti().isEmpty()) {
            dto.setCommenti(p.getCommenti().stream()
                .map(DtoMapper::toCommentoDtoLight)
                .collect(Collectors.toList()));
        }

        if (p.getAllegati() != null && !p.getAllegati().isEmpty()) {
            dto.setAllegati(p.getAllegati().stream()
                .map(DtoMapper::toAllegatoDto)
                .collect(Collectors.toList()));
        }

        return dto;
    }


   public static PostDto toPostDtoForTendenze(Post p) {
       if (p == null) return null;

       PostDto dto = new PostDto();
       
       dto.setId(p.getIdPost());
       dto.setContenuto(p.getContenuto());
       dto.setDataOra(p.getDataOra());
       
       if (p.getUtente() != null) {
           dto.setIdUtente(p.getUtente().getId());
           dto.setNomeUtente(p.getUtente().getNome());
           dto.setUsernameUtente(p.getUtente().getUsername());
           if (p.getUtente().getRuolo() != null) dto.setRuoloUtente(p.getUtente().getRuolo().getNome());
       }

       if (p.getLikes() != null) {
           dto.setNumeroLike(p.getLikes().size());
       } else {
           dto.setNumeroLike(0);
       }

       if (p.getCommenti() != null) {
           dto.setNumeroCommenti(p.getCommenti().size());
       } else {
           dto.setNumeroCommenti(0);
       }

       return dto;
   }
    
    
    
    public static ProfiloDto toProfiloDtoLight(Utente u) {
        if (u == null) return null;
        ProfiloDto dto = new ProfiloDto();
        dto.setId(u.getId());
        dto.setNome(u.getNome());
        dto.setCognome(u.getCognome());
        dto.setUsername(u.getUsername());
        dto.setBio(u.getBio());
        dto.setFotoProfilo(u.getFotoProfilo());
        dto.setMemberDal(u.getCreatedAt());
        if (u.getRuolo() != null) dto.setRuolo(u.getRuolo().getNome());
        return dto;
    }

    public static ProfiloDto toProfiloDto(Utente u) {
        if (u == null) return null;
        ProfiloDto dto = new ProfiloDto();
        dto.setId(u.getId());
        dto.setNome(u.getNome());
        dto.setCognome(u.getCognome());
        dto.setUsername(u.getUsername());
        dto.setBio(u.getBio());
        dto.setFotoProfilo(u.getFotoProfilo());
        dto.setMemberDal(u.getCreatedAt());
        if (u.getRuolo() != null) dto.setRuolo(u.getRuolo().getNome());

        if (u.getPosts() != null) {
            dto.setNumPost(u.getPosts().size());
            int totaleLike = u.getPosts().stream()
                .mapToInt(p -> p.getLikes() != null ? p.getLikes().size() : 0)
                .sum();
            dto.setNumLike(totaleLike);
            dto.setPosts(u.getPosts().stream()
                .map(DtoMapper::toPostDtoForProfile)
                .collect(Collectors.toList()));
        }
        return dto;
    }

    public static CommentoDto toCommentoDtoLight(Commento c) {
        CommentoDto dto = new CommentoDto();
        dto.setIdCommento(c.getIdCommento());
        dto.setTesto(c.getTesto());
        dto.setDataOra(c.getDataOra());
        dto.setUtente(toUtenteDtoLight(c.getUtente()));
        return dto;
    }

    public static AllegatoDto toAllegatoDto(Allegato a) {
        if (a == null) return null;
        AllegatoDto dto = new AllegatoDto();
        dto.setId(a.getIdAllegato());
        dto.setNomeOriginale(a.getNomeOriginale());
        dto.setUrl(a.getUrl());
        dto.setMimeType(a.getMimeType());
        dto.setTipo(a.getTipo());
        return dto;
    }



}
