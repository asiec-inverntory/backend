package ru.centralhardware.asiec.inventory.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.centralhardware.asiec.inventory.Dto.Create.CreateEquipmentDto;
import ru.centralhardware.asiec.inventory.Dto.EquipmentDto;
import ru.centralhardware.asiec.inventory.Entity.Equipment;
import ru.centralhardware.asiec.inventory.Entity.InventoryUser;
import ru.centralhardware.asiec.inventory.Mapper.EquipmentMapper;
import ru.centralhardware.asiec.inventory.Repository.EquipmentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final UserService userService;

    public EquipmentService(EquipmentRepository equipmentRepository, UserService userService) {
        this.equipmentRepository = equipmentRepository;
        this.userService = userService;
    }

    public Optional<Equipment> findById(int id){
        return equipmentRepository.findById(id);
    }

    public boolean existById(int id){
        return equipmentRepository.existsById(id);
    }

    public boolean hasAccess(int id, InventoryUser user){
        var equipment = equipmentRepository.findById(id);
        return equipment.map(value -> value.getResponsible().getId().equals(id)).orElse(false);
    }

    public Equipment create(CreateEquipmentDto dto, InventoryUser createdBy){
        var equipment = EquipmentMapper.INSTANCE.dtoToEquipment(dto);
        equipment.setCreatedBy(createdBy);
        return equipmentRepository.save(equipment);
    }

    public Equipment update(CreateEquipmentDto dto, InventoryUser updatedBy){
        var equipment = EquipmentMapper.INSTANCE.dtoToEquipment(dto);
        equipment.setUpdatedBy(updatedBy);
        return equipmentRepository.save(equipment);
    }

    public void delete(int id) {
        equipmentRepository.deleteById(id);
    }

    public List<EquipmentDto> list(InventoryUser user, Pageable sort){
        return equipmentRepository.findAllByResponsible(user, sort).stream().map(EquipmentMapper.INSTANCE::equipmentToDto).collect(Collectors.toList());
    }

    public List<EquipmentDto> list(Pageable sort){
        return equipmentRepository.findAll(sort).map(EquipmentMapper.INSTANCE::equipmentToDto).stream().collect(Collectors.toList());
    }
}
