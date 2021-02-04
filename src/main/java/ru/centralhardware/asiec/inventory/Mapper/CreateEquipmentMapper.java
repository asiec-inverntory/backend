package ru.centralhardware.asiec.inventory.Mapper;

import org.mapstruct.factory.Mappers;
import ru.centralhardware.asiec.inventory.Dto.CreateEquipmentDto;
import ru.centralhardware.asiec.inventory.Entity.Equipment;

public interface CreateEquipmentMapper {

    CreateEquipmentMapper INSTANCE = Mappers.getMapper(CreateEquipmentMapper.class);

    Equipment dtoToEquipment(CreateEquipmentDto dto);

}
