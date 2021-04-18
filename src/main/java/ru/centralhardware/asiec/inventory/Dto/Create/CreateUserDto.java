package ru.centralhardware.asiec.inventory.Dto.Create;

import ru.centralhardware.asiec.inventory.Entity.Enum.Role;

public record CreateUserDto (

    int id,
    String username,
    String password,
    String name,
    String surname,
    String lastName,
    Role role

) { }
