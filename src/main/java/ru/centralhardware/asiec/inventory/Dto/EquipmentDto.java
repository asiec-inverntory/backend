package ru.centralhardware.asiec.inventory.Dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class EquipmentDto {

    public int id;
    public String name;
    public String inventory_code;
    public HumanReadableHolder room;
    public HumanReadableHolder childEquipment;
    public boolean isAtomic;
    public String appointment;
    public HumanReadableHolder position;
    public Set<HumanReadableHolder> characteristics = new HashSet<>();

    public Date createdAt;
    public Date updatedAt;
    public HumanReadableHolder createdBy;
    public HumanReadableHolder updatedBy;

}