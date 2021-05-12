package ru.centralhardware.asiec.inventory.Service;

import org.springframework.stereotype.Service;
import ru.centralhardware.asiec.inventory.Entity.EquipmentType;
import ru.centralhardware.asiec.inventory.Repository.EquipmentTypeRepository;

import java.util.Optional;

@Service
public class EquipmentTypeService {

    private final EquipmentTypeRepository equipmentTypeRepository;

    public EquipmentTypeService(EquipmentTypeRepository equipmentTypeRepository) {
        this.equipmentTypeRepository = equipmentTypeRepository;
    }

    public Optional<EquipmentType> findByName(String typeName){
        return equipmentTypeRepository.findByTypeName(typeName);
    }
}
