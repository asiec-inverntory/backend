package ru.centralhardware.asiec.inventory.Mapper;

import ru.centralhardware.asiec.inventory.Entity.Position;
import ru.centralhardware.asiec.inventory.Service.PositionService;
import ru.centralhardware.asiec.inventory.SpringContext;

public class PositionConverter {

    int toDto(Position position){
        return position.getId();
    }

    Position fromDto(int id){
        return SpringContext.getBean(PositionService.class).findById(id).orElse(null);
    }

}
