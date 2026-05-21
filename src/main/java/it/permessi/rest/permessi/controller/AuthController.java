package it.permessi.rest.permessi.controller;

import it.permessi.rest.permessi.dto.LoginRequest;
import it.permessi.rest.permessi.dto.LoginResponse;
import it.permessi.rest.permessi.dto.NuovaPasswordRequest;
import it.permessi.rest.permessi.dto.PasswordResetRequest;
import it.permessi.rest.permessi.dto.RegistrazioneRequest;
import it.permessi.rest.permessi.dto.VerificaCodiceRequest;
import it.permessi.rest.permessi.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Controller REST per autenticazione: delega tutta la logica ad AuthService. */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest req) {
        return authService.login(req);
    }
    
    @PostMapping("/registrazione")
    public ResponseEntity<Void> registrazione(@RequestBody @Valid RegistrazioneRequest request) {
        authService.registrazione(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public void logout() {
        authService.logout();
    }
    
    //provaaa
    
    /** Step 1: Richiesta codice via email */
    @PostMapping("/password-dimenticata")
    public ResponseEntity<Void> passwordDimenticata(@Valid @RequestBody PasswordResetRequest req) {
        authService.richiestaResetPassword(req);
        return ResponseEntity.ok().build();
    }
    
    /** Step 2: Verifica codice a 4 cifre */
    @PostMapping("/verifica-codice")
    public ResponseEntity<Boolean> verificaCodice(@Valid @RequestBody VerificaCodiceRequest req) {
        boolean valido = authService.verificaCodice(req);
        return ResponseEntity.ok(valido);
    }
    
    /** Step 3: Imposta nuova password */
    @PostMapping("/nuova-password")
    public ResponseEntity<Void> nuovaPassword(@Valid @RequestBody NuovaPasswordRequest req) {
        authService.impostaNuovaPassword(req);
        return ResponseEntity.ok().build();
    }
}
