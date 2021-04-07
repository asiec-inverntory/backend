package ru.centralhardware.asiec.inventory.Dto;

import ru.centralhardware.asiec.inventory.Entity.Enum.EquipmentType;

import java.util.HashSet;
import java.util.Set;

public class EquipmentDto {

    public int id;
    public String name;
    public String inventory_code;
    public HumanReadableHolder room;
    public EquipmentType equipmentType;
    public HumanReadableHolder responsible;
    public Set<HumanReadableHolder> characteristics = new HashSet<>();

}