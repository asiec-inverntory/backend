package ru.centralhardware.asiec.inventory.Mapper;

import ru.centralhardware.asiec.inventory.Dto.HumanReadableHolder;
import ru.centralhardware.asiec.inventory.Entity.Position;
import ru.centralhardware.asiec.inventory.Service.PositionService;
import ru.centralhardware.asiec.inventory.SpringContext;

public class PositionConverter {

    public int toDto(Position position){
        return position.getId();
    }

    public Position fromDto(int id){
        return SpringContext.getBean(PositionService.class).findById(id).orElse(null);
    }

    public HumanReadableHolder toHumanReadable(Position position){
        if (position == null) return null;
        return new HumanReadableHolder(position.getId(),
                String.format("каб. %s место %s",
                        position.getRoom().getNumber(),
                        position.getNumber()));
    }

}
