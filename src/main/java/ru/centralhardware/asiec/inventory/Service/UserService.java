package ru.centralhardware.asiec.inventory.Service;

import javassist.NotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.centralhardware.asiec.inventory.Dto.CreateUserDto;
import ru.centralhardware.asiec.inventory.Dto.UserDto;
import ru.centralhardware.asiec.inventory.Entity.InventoryUser;
import ru.centralhardware.asiec.inventory.Mapper.CreateUserMapper;
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

    public void delete(int id) throws NotFoundException {
        var userOptional = userRepository.findById(id);
        if (userOptional.isPresent()){
            userRepository.save(userOptional.get().setDeleted(true));
        } else {
            throw new NotFoundException(String.format("user with id = %s not found", id));
        }
    }

    public InventoryUser create(CreateUserDto userDto, InventoryUser createdBy){
        var user = CreateUserMapper.INSTANCE.dtoToUser(userDto);
        user.setCreatedBy(createdBy);
        return userRepository.save(user);
    }

    public InventoryUser update(UserDto userDto){
        var user = UserMapper.INSTANCE.dtoToUser(userDto);
        return userRepository.save(user);
    }

    public boolean existById(int id){
        return userRepository.existsById(id);
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
