package ru.centralhardware.asiec.inventory.Web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
public class HealthCheck {

    @GetMapping(path = "healthcheck")
    public ResponseEntity<?> healthCheck(){
        return ResponseEntity.ok().build();
    }

}
