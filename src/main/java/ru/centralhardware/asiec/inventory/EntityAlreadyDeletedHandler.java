package ru.centralhardware.asiec.inventory;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@Controller
public class EntityAlreadyDeletedHandler {

    @ExceptionHandler(EntityAlreadyDeleted.class)
    public ResponseEntity<?> handle(){
        return ResponseEntity.status(404).body(Optional.of("try to fetch entity that already deleted"));
    }

}
