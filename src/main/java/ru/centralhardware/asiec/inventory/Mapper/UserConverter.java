package ru.centralhardware.asiec.inventory.Mapper;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import ru.centralhardware.asiec.inventory.Entity.InventoryUser;
import ru.centralhardware.asiec.inventory.Service.UserService;
import ru.centralhardware.asiec.inventory.SpringContext;

@Service
public class UserConverter {

    public InventoryUser toUser(int id) throws ChangeSetPersister.NotFoundException {
        return SpringContext.getBean(UserService.class).findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public String toDto(InventoryUser user){
        if (user == null) return null;
        return String.format("%s %s %s",
                user.getSurname(),
                user.getName(),
                user.getLastName().isEmpty()? "" : user.getLastName());
    }

}
