package ru.centralhardware.asiec.inventory.Service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.centralhardware.asiec.inventory.Dto.UserDto;
import ru.centralhardware.asiec.inventory.Entity.InventoryUser;
import ru.centralhardware.asiec.inventory.Mapper.UserMapper;
import ru.centralhardware.asiec.inventory.Repository.UserRepository;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<InventoryUser> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public Optional<InventoryUser> findById(int id){
        return userRepository.findById(id);
    }

    public void delete(int id){
        var userOptional = userRepository.findById(id);
        userRepository.save(userOptional.get().setDeleted(true));
    }

    public InventoryUser create(UserDto userDto, InventoryUser createdBy){
        var user = UserMapper.INSTANCE.dtoToUser(userDto);
        user.setCreatedBy(createdBy);
        return userRepository.save(user);
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
