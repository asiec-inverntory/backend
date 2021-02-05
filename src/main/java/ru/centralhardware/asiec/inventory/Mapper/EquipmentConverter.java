package ru.centralhardware.asiec.inventory.Mapper;

import ru.centralhardware.asiec.inventory.Dto.HumanReadableHolder;
import ru.centralhardware.asiec.inventory.Entity.Equipment;
import ru.centralhardware.asiec.inventory.Service.EquipmentService;
import ru.centralhardware.asiec.inventory.SpringContext;

public class EquipmentConverter {

    public int toDto(Equipment equipment){
        return equipment.getId();
    }

    public Equipment fromDto(int id){
        return SpringContext.getBean(EquipmentService.class).findById(id).orElse(null);
    }

    public HumanReadableHolder toHumanReadable(Equipment equipment){
        if (equipment == null) return null;
        return new HumanReadableHolder(equipment.getId(),
                String.format("%s %s",
                        equipment.getName(),
                        equipment.getInventory_code()));
    }

}
