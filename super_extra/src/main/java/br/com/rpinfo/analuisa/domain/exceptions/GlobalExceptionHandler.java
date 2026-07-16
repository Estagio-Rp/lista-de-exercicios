package br.com.rpinfo.analuisa.adapter.rest.exception;

import br.com.rpinfo.analuisa.domain.exceptions.CampoInvalidoException;
import br.com.rpinfo.analuisa.domain.exceptions.CredenciaisInvalidasException;
import br.com.rpinfo.analuisa.domain.exceptions.EmailJaCadastradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CampoInvalidoException.class)
    public ResponseEntity<?> tratarCampoInvalido(
            CampoInvalidoException ex
    ) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", 400,
                        "erro", "Bad Request",
                        "mensagem", ex.getMessage()
                ));
    }

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<?> tratarEmailDuplicado(
            EmailJaCadastradoException ex
    ) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", 409,
                        "erro", "Conflict",
                        "mensagem", ex.getMessage()
                ));
    }

    @ExceptionHandler(CredenciaisInvalidasException.class)
    public ResponseEntity<?> tratarCredenciais(
            CredenciaisInvalidasException ex
    ) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", 401,
                        "erro", "Unauthorized",
                        "mensagem", ex.getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> tratarGenerico(
            Exception ex
    ) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", 500,
                        "erro", "Internal Server Error",
                        "mensagem", ex.getMessage()
                ));
    }
}