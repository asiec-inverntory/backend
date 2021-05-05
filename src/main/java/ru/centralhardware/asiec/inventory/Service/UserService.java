package ru.centralhardware.asiec.inventory.Service;

import javassist.NotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.centralhardware.asiec.inventory.Dto.Create.CreateUserDto;
import ru.centralhardware.asiec.inventory.Entity.InventoryUser;
import ru.centralhardware.asiec.inventory.Mapper.UserMapper;
import ru.centralhardware.asiec.inventory.Repository.UserRepository;

import java.util.Date;
import java.util.Optional;

import static java.util.function.Predicate.not;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<InventoryUser> findByUsername(String username){
        return userRepository.findByUsername(username).filter(not(InventoryUser::isDeleted));
    }

    public Optional<InventoryUser> findById(int id){
        return userRepository.findById(id).filter(not(InventoryUser::isDeleted));
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
        var user = UserMapper.INSTANCE.dtoToUser(userDto);
        user.setId(null);
        return userRepository.save(user);
    }

    public InventoryUser update(CreateUserDto userDto){
        var user = UserMapper.INSTANCE.dtoToUser(userDto);
        return userRepository.save(user);
    }

    public void setLastLogin(String username){
        userRepository.findByUsername(username).ifPresent(user -> user.setLastLogin(new Date()));
    }

    public boolean existById(int id){
        var user = findById(id);
        return user.isPresent() && !user.get().isDeleted();
    }

}
