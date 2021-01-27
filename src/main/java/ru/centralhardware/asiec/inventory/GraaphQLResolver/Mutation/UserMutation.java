package ru.centralhardware.asiec.inventory.GraaphQLResolver.Mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;
import ru.centralhardware.asiec.inventory.Entity.User;
import ru.centralhardware.asiec.inventory.Service.UserService;

@Component
public class UserMutation implements GraphQLMutationResolver {

    private final UserService userService;

    public UserMutation(UserService userService) {
        this.userService = userService;
    }

    public User createUser(String name){
        return userService.createUser(name);
    }

}
