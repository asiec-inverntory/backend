package ru.centralhardware.asiec.inventory.Mapper;

import ru.centralhardware.asiec.inventory.Entity.Room;
import ru.centralhardware.asiec.inventory.Service.RoomService;
import ru.centralhardware.asiec.inventory.SpringContext;

public class RoomConverter {


    int toDto(Room room){
        return room.getId();
    }

    Room fromDto(int id){
        return SpringContext.getBean(RoomService.class).findById(id).orElse(null);
    }
}
