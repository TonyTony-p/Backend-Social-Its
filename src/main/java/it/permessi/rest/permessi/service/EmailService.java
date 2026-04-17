package it.permessi.rest.permessi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {
    
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Value("${spring.mail.from:noreply@itsocial.com}")
    private String mittente;
    
    public void inviaCodiceDiReset(String emailDestinatario, String codice) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mittente);
            message.setTo(emailDestinatario);
            message.setSubject("Codice di Ripristino Password - ITSocial");
            message.setText(
                "Ciao,\n\n" +
                "Hai richiesto il ripristino della password.\n\n" +
                "Il tuo codice di verifica è: " + codice + "\n\n" +
                "⏱️ Il codice è valido per 15 minuti.\n" +
                "🔢 Hai a disposizione 3 tentativi.\n\n" +
                "Se non hai richiesto il ripristino della password, ignora questa email.\n\n" +
                "Cordiali saluti,\n" +
                "Il Team ITSocial"
            );
            
            mailSender.send(message);
            logger.info("Email inviata con successo a: {}", emailDestinatario);
            
        } catch (Exception e) {
            logger.error("Errore durante l'invio dell'email a: {}", emailDestinatario, e);
            throw new RuntimeException("Impossibile inviare l'email. Riprova più tardi.", e);
        }
    }
}