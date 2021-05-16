package ru.centralhardware.asiec.inventory.Web.Dto;

import ru.centralhardware.asiec.inventory.Entity.Enum.EquipmentVariant;

import java.util.Set;

public record EquipmentDto (

    int id,
    String equipmentKey,
    String name,
    String inventoryCode,
    String serialCode,
    RoomDto room,
    EquipmentVariant equipmentType,
    String responsible,
    Set<PropertyDto> characteristics

) { }