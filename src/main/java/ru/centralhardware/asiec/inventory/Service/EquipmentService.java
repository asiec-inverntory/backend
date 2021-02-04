package ru.centralhardware.asiec.inventory.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.centralhardware.asiec.inventory.Dto.CreateEquipmentDto;
import ru.centralhardware.asiec.inventory.Entity.Equipment;
import ru.centralhardware.asiec.inventory.Entity.InventoryUser;
import ru.centralhardware.asiec.inventory.Mapper.CreateEquipmentMapper;
import ru.centralhardware.asiec.inventory.Repository.EquipmentRepository;

import java.util.List;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final UserService userService;

    public EquipmentService(EquipmentRepository equipmentRepository, UserService userService) {
        this.equipmentRepository = equipmentRepository;
        this.userService = userService;
    }

    public boolean existById(int id){
        return equipmentRepository.existsById(id);
    }

    public boolean hasAccess(int id, InventoryUser user){
        var equipment = equipmentRepository.findById(id);
        return equipment.map(value -> value.getResponsible().getId().equals(id)).orElse(false);
    }

    public Equipment create(CreateEquipmentDto dto, InventoryUser createdBy){
        var equipment = CreateEquipmentMapper.INSTANCE.dtoToEquipment(dto);
        equipment.setCreatedBy(createdBy);
        return equipmentRepository.save(equipment);
    }

    public Equipment update(CreateEquipmentDto dto, InventoryUser updatedBy){
        var equipment = CreateEquipmentMapper.INSTANCE.dtoToEquipment(dto);
        equipment.setUpdatedBy(updatedBy);
        return equipmentRepository.save(equipment);
    }

    public void delete(int id) {
        equipmentRepository.deleteById(id);
    }

    public List<Equipment> list(String username, Pageable sort){
        return equipmentRepository.findAllByUsername(username, sort);
    }

    public Page<Equipment> list(Pageable sort){
        return equipmentRepository.findAll(sort);
    }
}
