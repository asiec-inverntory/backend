package ru.centralhardware.asiec.inventory.Dto;

import ru.centralhardware.asiec.inventory.Entity.Enum.EquipmentType;

import java.util.Set;

public record EquipmentDto (

    int id,
    String name,
    String inventory_code,
    HumanReadableHolder room,
    EquipmentType equipmentType,
    HumanReadableHolder responsible,
    Set<HumanReadableHolder> characteristics

) { }