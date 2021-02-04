package ru.centralhardware.asiec.inventory.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.centralhardware.asiec.inventory.Dto.CreateUserDto;
import ru.centralhardware.asiec.inventory.Entity.InventoryUser;

@Mapper
public interface CreateUserMapper {

    CreateUserMapper INSTANCE = Mappers.getMapper(CreateUserMapper.class);

    InventoryUser dtoToUser(CreateUserDto dto);

}
