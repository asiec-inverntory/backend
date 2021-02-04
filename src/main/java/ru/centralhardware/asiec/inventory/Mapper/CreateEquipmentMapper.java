package ru.centralhardware.asiec.inventory.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.centralhardware.asiec.inventory.Dto.CreateEquipmentDto;
import ru.centralhardware.asiec.inventory.Entity.Equipment;

@Mapper(uses = {UserConverter.class, CharacteristicConverter.class, RoomConverter.class, EquipmentConverter.class, PositionConverter.class})
public interface CreateEquipmentMapper {

    CreateEquipmentMapper INSTANCE = Mappers.getMapper(CreateEquipmentMapper.class);

    Equipment dtoToEquipment(CreateEquipmentDto dto);

}
