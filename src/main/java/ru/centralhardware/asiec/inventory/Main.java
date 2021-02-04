package ru.centralhardware.asiec.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger.web.SecurityConfiguration;

@ComponentScan("ru.centralhardware.asiec.inventory")
@EnableJpaRepositories("ru.centralhardware.asiec.inventory.Repository")
@EnableAutoConfiguration
@Configuration
@SpringBootApplication()
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
