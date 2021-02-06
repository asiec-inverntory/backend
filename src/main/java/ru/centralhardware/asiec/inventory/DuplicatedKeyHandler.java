package ru.centralhardware.asiec.inventory;

import org.postgresql.util.PSQLException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice

public class DuplicatedKeyHandler {

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<?> handle(PSQLException exception){
        return ResponseEntity.badRequest().body(exception.getLocalizedMessage());
    }


}
