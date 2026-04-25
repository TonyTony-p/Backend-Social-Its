package it.permessi.rest.permessi.repository;

import it.permessi.rest.permessi.entity.Utente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/** Repository CRUD per Utente + finder per email (login). */
public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Optional<Utente> findByUsername(String username);

    Optional<Utente> findWithPostsById(Long id);
    Optional<Utente> findByEmail(String email);

    @Query("SELECT u FROM Utente u LEFT JOIN FETCH u.posts WHERE u.username = :username")
    Optional<Utente> findWithPostsByUsername(@Param("username") String username);

    /** Carica utente con ruolo e permessi in un'unica query (usato da UserDetailsServiceImpl). */
    @Query("SELECT u FROM Utente u LEFT JOIN FETCH u.ruolo r LEFT JOIN FETCH r.ruoloPermessi rp LEFT JOIN FETCH rp.permesso WHERE u.username = :username")
    Optional<Utente> findByUsernameWithPermissions(@Param("username") String username);
}
