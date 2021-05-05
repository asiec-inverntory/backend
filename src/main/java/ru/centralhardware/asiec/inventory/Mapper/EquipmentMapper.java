package ru.centralhardware.asiec.inventory.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.centralhardware.asiec.inventory.Dto.EquipmentDto;
import ru.centralhardware.asiec.inventory.Entity.Equipment;

@Mapper(uses = {UserConverter.class, CharacteristicConverter.class, RoomConverter.class, EquipmentConverter.class})
public interface EquipmentMapper {

    EquipmentMapper INSTANCE = Mappers.getMapper(EquipmentMapper.class);

    @Mappings({
            @Mapping(target = "name", source = "humanReadable")
    })
    EquipmentDto equipmentToDto(Equipment equipment);
//    Equipment dtoToEquipment(ReceiveEquipmentDto dto);


}
