package br.com.basis.madre.web.rest.exception.handler;

import br.com.basis.madre.service.exceptions.PacienteNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(PacienteNotFoundException.class)
    public ResponseEntity<DefaultError> pageNotFound(PacienteNotFoundException e, HttpServletRequest request){
        DefaultError error = new DefaultError(HttpStatus.NOT_FOUND.value(), e.getMessage(), Instant.now(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
