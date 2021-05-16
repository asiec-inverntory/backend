package ru.centralhardware.asiec.inventory.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.centralhardware.asiec.inventory.Web.Dto.EquipmentDto;
import ru.centralhardware.asiec.inventory.Entity.Equipment;
import ru.centralhardware.asiec.inventory.Entity.InventoryUser;
import ru.centralhardware.asiec.inventory.Mapper.EquipmentMapper;
import ru.centralhardware.asiec.inventory.Repository.EquipmentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

@Service
public class EquipmentService extends Equipment {

    private final EquipmentRepository equipmentRepository;
    private final EquipmentMapper equipmentMapper;

    public EquipmentService(EquipmentRepository equipmentRepository,
                            UserService userService,
                            EquipmentMapper equipmentMapper) {
        this.equipmentRepository = equipmentRepository;
        this.equipmentMapper = equipmentMapper;
    }

    public Optional<Equipment> findById(int id){
        return equipmentRepository.findById(id).filter(not(Equipment::isDeleted));
    }

    public int getPageCount(int pageSize){
        return (findAll().size() % pageSize > 0) ? ((findAll().size() / pageSize) + 1) : (findAll().size() / pageSize);
    }

    public List<Equipment> findAll(){
        return equipmentRepository.
                findAll().
                stream().
                filter(not(Equipment::isDeleted)).
                collect(Collectors.toList());
    }

    public List<Equipment> findAll(Pageable pageable){
        return equipmentRepository.
                findAll(pageable).
                stream().
                filter(not(Equipment::isDeleted)).
                collect(Collectors.toList());
    }

    public Optional<EquipmentDto> findBySerialAndInventoryCode(String serialCode, String inventoryCode){
        var res = equipmentRepository.findBySerialCodeAndInventoryCode(serialCode, inventoryCode);
        return res.map(equipmentMapper::equipmentToDto);
    }

    public boolean existById(int id){
        var equipment = equipmentRepository.findById(id);
        return equipment.isPresent() && !equipment.get().isDeleted();
    }

    public boolean hasAccess(int id, InventoryUser user){
        var equipment = equipmentRepository.findById(id);
        return equipment.map(value -> value.getResponsible().getId().equals(id)).orElse(false);
    }

//    public Equipment create(ReceiveEquipmentDto dto, InventoryUser createdBy){
//        var equipment = EquipmentMapper.INSTANCE.dtoToEquipment(dto);
//        return equipmentRepository.save(equipment);
//    }
//
//    public Equipment update(ReceiveEquipmentDto dto, InventoryUser updatedBy){
//        var equipment = EquipmentMapper.INSTANCE.dtoToEquipment(dto);
//        return equipmentRepository.save(equipment);
//    }

    public void delete(int id) {
        equipmentRepository.deleteById(id);
    }

    public List<Equipment> list(InventoryUser user, Pageable sort){
        return equipmentRepository.findAllByResponsible(user, sort);
    }

    public List<Equipment> list(Pageable sort){
        return equipmentRepository.
                findAll(sort).
                get().
                collect(Collectors.toList());
    }

    public void save(Equipment equipment){
        equipmentRepository.save(equipment);
    }



}
