package ru.centralhardware.asiec.inventory.Dto;

import ru.centralhardware.asiec.inventory.Entity.Enum.EquipmentType;

import java.util.Set;

public record EquipmentDto (

    int id,
    String equipmentKey,
    String name,
    String inventoryCode,
    String serialCode,
    RoomDto room,
    EquipmentType equipmentType,
    String responsible,
    Set<HumanReadableHolder> characteristics

) { }