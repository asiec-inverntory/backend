package ru.centralhardware.asiec.inventory.Web.Dto;

import ru.centralhardware.asiec.inventory.Entity.Enum.Role;

public record UserDto (
        int id,
        String username,
        String name,
        String surname,
        String lastName,
        Role role
) { }
