package ru.centralhardware.asiec.inventory.Service;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.centralhardware.asiec.inventory.Repository.UserRepository;

@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(@NotNull String username) throws UsernameNotFoundException {
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
