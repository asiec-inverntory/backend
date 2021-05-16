package ru.centralhardware.asiec.inventory.Web;

import javassist.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.centralhardware.asiec.inventory.Web.Dto.Create.CreateUserDto;
import ru.centralhardware.asiec.inventory.Entity.Enum.Role;
import ru.centralhardware.asiec.inventory.Main;
import ru.centralhardware.asiec.inventory.Service.UserService;
import ru.centralhardware.asiec.inventory.support.EmbeddedPostgreSQLBootstrapConfiguration;
import ru.centralhardware.asiec.inventory.support.EmbeddedPostgreSQLDependenciesAutoConfiguration;

@SpringBootTest(classes = {
        Main.class,
        EmbeddedPostgreSQLBootstrapConfiguration.class,
        EmbeddedPostgreSQLDependenciesAutoConfiguration.class,
})
class AuthenticationControllerTest {

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService.create(new CreateUserDto(12,
                "test_user",
                "password",
                "alice",
                "foo",
                "bar",
                Role.OWNER), null);
    }

    @AfterEach
    void tearDown() throws NotFoundException {
        userService.delete(12);
    }

    @Test
    void authenticate() {
    }

    @Test
    void logout() {
    }
}