package it.permessi.rest.permessi.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.permessi.rest.permessi.entity.Utente;
import it.permessi.rest.permessi.repository.UtenteRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired private UtenteRepository utenteRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utente u = utenteRepository.findByUsernameWithPermissions(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato: " + username));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        if (u.getRuolo() != null) {
            authorities.add(new SimpleGrantedAuthority(u.getRuolo().getNome()));
            // Carica tutti i permessi del ruolo come authorities individuali
            u.getRuolo().getRuoloPermessi().forEach(rp ->
                authorities.add(new SimpleGrantedAuthority(rp.getPermesso().getAlias()))
            );
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), u.getPassword(), authorities
        );
    }
}