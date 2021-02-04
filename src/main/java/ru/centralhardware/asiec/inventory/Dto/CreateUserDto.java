package ru.centralhardware.asiec.inventory.Dto;

import ru.centralhardware.asiec.inventory.Entity.Enum.Role;

import java.util.Date;

public class CreateUserDto {

    public int id;
    public String username;
    public String password;
    public String name;
    public String surname;
    public String lastName;
    public Role role;
}
