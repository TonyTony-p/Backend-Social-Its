package it.permessi.rest.permessi.service;

import it.permessi.rest.permessi.dto.MessaggioDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ChatSseService {

    private final ConcurrentHashMap<String, CopyOnWriteArrayList<SseEmitter>> emitters =
            new ConcurrentHashMap<>();

    /** Registra un nuovo emitter per l'utente e restituisce la connessione SSE. */
    public SseEmitter subscribe(String username) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        emitters.computeIfAbsent(username, k -> new CopyOnWriteArrayList<>()).add(emitter);

        Runnable remove = () -> {
            CopyOnWriteArrayList<SseEmitter> list = emitters.get(username);
            if (list != null) list.remove(emitter);
        };
        emitter.onCompletion(remove);
        emitter.onTimeout(remove);
        emitter.onError(e -> remove.run());

        try {
            emitter.send(SseEmitter.event().name("connected").data("ok"));
        } catch (IOException e) {
            emitter.complete();
        }

        return emitter;
    }

    /** Invia un evento "messaggio" a tutti gli emitter attivi del destinatario. */
    public void notifica(String recipientUsername, MessaggioDto msg) {
        CopyOnWriteArrayList<SseEmitter> list = emitters.get(recipientUsername);
        if (list == null || list.isEmpty()) return;

        List<SseEmitter> dead = new ArrayList<>();
        for (SseEmitter emitter : list) {
            try {
                emitter.send(SseEmitter.event()
                        .name("messaggio")
                        .data(msg, MediaType.APPLICATION_JSON));
            } catch (IOException e) {
                dead.add(emitter);
            }
        }
        list.removeAll(dead);
    }
}
