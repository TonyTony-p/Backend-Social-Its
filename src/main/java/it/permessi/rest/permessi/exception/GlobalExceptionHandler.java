package it.permessi.rest.permessi.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

/**
 * Gestione centralizzata delle eccezioni comuni.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntime(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errori = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(err ->
            errori.put(err.getField(), err.getDefaultMessage())
        );

        
        return ResponseEntity
                .badRequest()
                .body(Map.of("errori", errori));
    }

}
