package it.permessi.rest.permessi.config;

import it.permessi.rest.permessi.repository.UtenteRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LastSeenInterceptor implements HandlerInterceptor {

    @Autowired
    private UtenteRepository utenteRepo;

    // Tiene traccia dell'ultimo aggiornamento in memoria per evitare scritture continue
    private final ConcurrentHashMap<String, LocalDateTime> cache = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return true;
        }

        String username = auth.getName();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastUpdate = cache.get(username);

        // Aggiorna solo se l'ultima scrittura è avvenuta più di 60s fa
        if (lastUpdate == null || lastUpdate.plusSeconds(60).isBefore(now)) {
            cache.put(username, now);
            final LocalDateTime lastSeen = now;
            // Aggiornamento asincrono per non rallentare la request
            utenteRepo.findByUsername(username).ifPresent(u -> {
                u.setLastSeen(lastSeen);
                utenteRepo.save(u);
            });
        }
        return true;
    }
}
