package ru.centralhardware.asiec.inventory.Service;

import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import ru.centralhardware.asiec.inventory.Web.Dto.Create.CreateUserDto;
import ru.centralhardware.asiec.inventory.Entity.InventoryUser;
import ru.centralhardware.asiec.inventory.Mapper.UserMapper;
import ru.centralhardware.asiec.inventory.Repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.function.Predicate.not;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<InventoryUser> findAll(){
        return userRepository.
                findAll().
                stream().
                filter(not(InventoryUser::isDeleted)).
                toList();
    }

    public List<String> responsible(){
        return findAll().
                stream().
                filter(not(it -> it.getEquipment().isEmpty())).
                map(InventoryUser::getFio).toList();
    }

    public Optional<InventoryUser> findByUsername(String username){
        return userRepository.
                findByUsername(username).
                filter(not(InventoryUser::isDeleted));
    }

    public Optional<InventoryUser> findById(int id){
        return userRepository.
                findById(id).
                filter(not(InventoryUser::isDeleted));
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
        var user = userMapper.dtoToUser(userDto);
        user.setId(null);
        return userRepository.save(user);
    }

    public InventoryUser update(CreateUserDto userDto){
        var user = userMapper.dtoToUser(userDto);
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
