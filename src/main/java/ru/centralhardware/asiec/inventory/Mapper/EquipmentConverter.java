package ru.centralhardware.asiec.inventory.Mapper;

import ru.centralhardware.asiec.inventory.Entity.Equipment;
import ru.centralhardware.asiec.inventory.Service.EquipmentService;
import ru.centralhardware.asiec.inventory.SpringContext;

public class EquipmentConverter {

    int toDto(Equipment equipment){
        return equipment.getId();
    }

    Equipment fromDto(int id){
        return SpringContext.getBean(EquipmentService.class).findById(id).orElse(null);
    }

}
