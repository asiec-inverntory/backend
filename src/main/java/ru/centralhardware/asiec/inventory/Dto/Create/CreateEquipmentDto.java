package ru.centralhardware.asiec.inventory.Dto.Create;

public record CreateEquipmentDto (

    int id,
    String name,
    String inventory_code,
    int room,
    int parentEquipment,
    boolean isAtomic,
    int responsible

) {}
