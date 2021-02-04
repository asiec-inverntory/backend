package ru.centralhardware.asiec.inventory.Dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class EquipmentDto {

    public int id;
    public String inventory_code;
    public int room;
    public int childEquipment;
    public boolean isAtomic;
    public String appointment;
    public int position;
    public Set<Integer> characteristics = new HashSet<>();

    public Date createdAt;
    public Date updatedAt;
    public int createdBy;
    public int updatedBy;

}