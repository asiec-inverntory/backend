package ru.centralhardware.asiec.inventory.Mapper;

import org.springframework.data.crossstore.ChangeSetPersister;
import ru.centralhardware.asiec.inventory.Dto.HumanReadableHolder;
import ru.centralhardware.asiec.inventory.Entity.InventoryUser;
import ru.centralhardware.asiec.inventory.Service.UserService;
import ru.centralhardware.asiec.inventory.SpringContext;

public class UserConverter {

    public InventoryUser toUser(int id) throws ChangeSetPersister.NotFoundException {
        return SpringContext.getBean(UserService.class).findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public int toDto(InventoryUser user){
        if (user == null) return -1;
        return user.getId();
    }

    public HumanReadableHolder toHumanReadable(InventoryUser user){
        if (user == null) return null;
        return new HumanReadableHolder(user.getId(), String.format("%s %s %s",
                user.getName(),
                user.getSurname(),
                user.getLastName().isEmpty()? "" : user.getLastName()));
    }

}
