package ru.centralhardware.asiec.inventory.GraaphQLResolver.Query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;
import ru.centralhardware.asiec.inventory.Entity.User;
import ru.centralhardware.asiec.inventory.Service.UserService;

import java.util.Optional;

@Component
public class UserQuery implements GraphQLQueryResolver {

    private final UserService userService;

    public UserQuery(UserService userService) {
        this.userService = userService;
    }

    public Optional<User> getUser(int id){
        return userService.findById(id);
    }
}
