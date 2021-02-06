package ru.centralhardware.asiec.inventory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
public class EntityAlreadyDeletedHandler {

    @ExceptionHandler(EntityAlreadyDeleted.class)
    public ResponseEntity<?> handle(){
        return ResponseEntity.status(404).body(Optional.of("try to fetch entity that already deleted"));
    }

}
