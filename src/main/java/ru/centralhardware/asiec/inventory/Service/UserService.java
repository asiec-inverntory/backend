package ru.centralhardware.asiec.inventory.Service;

import org.springframework.stereotype.Service;
import ru.centralhardware.asiec.inventory.Entity.User;
import ru.centralhardware.asiec.inventory.Repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(int id){
        return userRepository.findById(id);
    }

    public User createUser(String name){
        return userRepository.save(new User());
    }
}
