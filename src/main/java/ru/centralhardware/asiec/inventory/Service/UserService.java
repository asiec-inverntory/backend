package ru.centralhardware.asiec.inventory.Service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.centralhardware.asiec.inventory.Repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userOptional = userRepository.findByUsername(username);
        User.UserBuilder builder;
        if (userOptional.isPresent()){
            var user = userOptional.get();
            builder = User.withUsername(username);
            builder.password(user.getPassword());
            builder.roles(user.getRole().name());
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
        return builder.build();
    }
}
