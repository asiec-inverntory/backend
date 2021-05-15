package ru.centralhardware.asiec.inventory.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.centralhardware.asiec.inventory.Dto.EquipmentDto;
import ru.centralhardware.asiec.inventory.Entity.Equipment;

@Mapper(uses =
        {
                UserConverter.class,
                CharacteristicConverter.class,
                RoomConverter.class,
                EquipmentConverter.class
        },
        componentModel = "spring"
        )
public interface EquipmentMapper {

    @Mappings({
            @Mapping(target = "name", source = "equipmentType.humanReadable"),
            @Mapping(target = "equipmentKey", source = "equipmentType.typeName"),
            @Mapping(target = "equipmentType", source = "equipmentVariant")
    })
    EquipmentDto equipmentToDto(Equipment equipment);
//    Equipment dtoToEquipment(ReceiveEquipmentDto dto);


}
