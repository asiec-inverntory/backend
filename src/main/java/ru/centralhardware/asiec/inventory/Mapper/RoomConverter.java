package ru.centralhardware.asiec.inventory.Mapper;

import org.springframework.stereotype.Service;
import ru.centralhardware.asiec.inventory.Web.Dto.RoomDto;
import ru.centralhardware.asiec.inventory.Entity.Room;
import ru.centralhardware.asiec.inventory.Service.RoomService;
import ru.centralhardware.asiec.inventory.SpringContext;

@Service
public class RoomConverter {


    public int toDto(Room room){
        return room.getId();
    }

    public Room fromDto(int id){
        return SpringContext.getBean(RoomService.class).findById(id).orElse(null);
    }

    public RoomDto toRoomDto(Room room){
        if (room == null) return null;
        return new RoomDto(room.getBuilding().getId(), room.getNumber());
    }
}
