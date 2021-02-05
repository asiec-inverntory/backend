package ru.centralhardware.asiec.inventory.Mapper;

import ru.centralhardware.asiec.inventory.Dto.HumanReadableHolder;
import ru.centralhardware.asiec.inventory.Entity.Room;
import ru.centralhardware.asiec.inventory.Service.RoomService;
import ru.centralhardware.asiec.inventory.SpringContext;

public class RoomConverter {


    public int toDto(Room room){
        return room.getId();
    }

    public Room fromDto(int id){
        return SpringContext.getBean(RoomService.class).findById(id).orElse(null);
    }

    public HumanReadableHolder toHumanReadable(Room room){
        if (room == null) return null;
        return new HumanReadableHolder(room.getId(), String.format("каб. № %s", room.getNumber()));
    }
}
