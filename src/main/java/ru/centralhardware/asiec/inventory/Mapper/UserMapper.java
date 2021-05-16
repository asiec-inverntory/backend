package ru.centralhardware.asiec.inventory.Mapper;

import org.mapstruct.Mapper;
import ru.centralhardware.asiec.inventory.Web.Dto.Create.CreateUserDto;
import ru.centralhardware.asiec.inventory.Web.Dto.UserDto;
import ru.centralhardware.asiec.inventory.Entity.InventoryUser;

@Mapper(uses = UserConverter.class,
        componentModel = "spring")
public interface UserMapper {

    UserDto userToDto(InventoryUser user);
    InventoryUser dtoToUser(CreateUserDto dto);

}
