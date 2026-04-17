package it.permessi.rest.permessi.repository;
import it.permessi.rest.permessi.entity.TokenResetPassword;
import it.permessi.rest.permessi.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TokenResetPasswordRepository extends JpaRepository<TokenResetPassword, Long> {
    Optional<TokenResetPassword> findByUtente(Utente utente);
    Optional<TokenResetPassword> findByCodice(String codice);
}
