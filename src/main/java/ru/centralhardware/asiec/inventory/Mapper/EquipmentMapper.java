package ru.centralhardware.asiec.inventory.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.centralhardware.asiec.inventory.Dto.EquipmentDto;
import ru.centralhardware.asiec.inventory.Entity.Equipment;

@Mapper(uses = {UserConverter.class, CharacteristicConverter.class, RoomConverter.class, EquipmentConverter.class, PositionConverter.class})
public interface EquipmentMapper {

    EquipmentMapper INSTANCE = Mappers.getMapper(EquipmentMapper.class);

    Equipment dtoToEquipment(EquipmentDto dto);
    EquipmentDto equipmentToDto(Equipment equipment);

}
