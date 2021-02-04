package ru.centralhardware.asiec.inventory.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.centralhardware.asiec.inventory.Dto.UserDto;
import ru.centralhardware.asiec.inventory.Entity.InventoryUser;

@Mapper(uses = { UserConverter.class })
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToDto(InventoryUser user);
    InventoryUser dtoToUser(UserDto dto);

}
