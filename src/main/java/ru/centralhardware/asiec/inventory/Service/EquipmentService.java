package ru.centralhardware.asiec.inventory.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.centralhardware.asiec.inventory.Dto.Create.ReceiveEquipmentDto;
import ru.centralhardware.asiec.inventory.Dto.EquipmentDto;
import ru.centralhardware.asiec.inventory.Entity.Characteristic;
import ru.centralhardware.asiec.inventory.Entity.Equipment;
import ru.centralhardware.asiec.inventory.Entity.InventoryUser;
import ru.centralhardware.asiec.inventory.Mapper.EquipmentMapper;
import ru.centralhardware.asiec.inventory.Repository.EquipmentRepository;
import ru.centralhardware.asiec.inventory.Web.Dto.FilterRequest;
import ru.centralhardware.asiec.inventory.Web.ValueType;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final UserService userService;

    public EquipmentService(EquipmentRepository equipmentRepository, UserService userService) {
        this.equipmentRepository = equipmentRepository;
        this.userService = userService;
    }

    public Optional<Equipment> findById(int id){
        return equipmentRepository.findById(id).filter(not(Equipment::isDeleted));
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

    public boolean existById(int id){
        var equipment = equipmentRepository.findById(id);
        return equipment.isPresent() && !equipment.get().isDeleted();
    }

    public boolean hasAccess(int id, InventoryUser user){
        var equipment = equipmentRepository.findById(id);
        return equipment.map(value -> value.getResponsible().getId().equals(id)).orElse(false);
    }

    public Equipment create(ReceiveEquipmentDto dto, InventoryUser createdBy){
        var equipment = EquipmentMapper.INSTANCE.dtoToEquipment(dto);
        return equipmentRepository.save(equipment);
    }

    public Equipment update(ReceiveEquipmentDto dto, InventoryUser updatedBy){
        var equipment = EquipmentMapper.INSTANCE.dtoToEquipment(dto);
        return equipmentRepository.save(equipment);
    }

    public void delete(int id) {
        equipmentRepository.deleteById(id);
    }

    public List<EquipmentDto> list(InventoryUser user, Pageable sort, List<FilterRequest> filterRequests){
        return equipmentRepository.
                findAllByResponsible(user, sort).
                stream().
                filter(not(Equipment::isDeleted)).
                filter(equipment -> isFiltered(filterRequests, equipment)).
                map(EquipmentMapper.INSTANCE::equipmentToDto).
                collect(Collectors.toList());
    }

    public List<EquipmentDto> list(Pageable sort, List<FilterRequest> filterRequests){
        return equipmentRepository.
                findAll(sort).
                get().
                filter(not(Equipment::isDeleted)).
                filter(equipment -> isFiltered(filterRequests, equipment)).
                map(EquipmentMapper.INSTANCE::equipmentToDto).
                collect(Collectors.toList());
    }

    public void save(Equipment equipment){
        equipmentRepository.save(equipment);
    }

    /**
     * @param filterRequests filter conditions
     * @param equipment list of equipment for filter
     * @return true if must filtered
     */
    private boolean isFiltered(List<FilterRequest> filterRequests, Equipment equipment){
        if (filterRequests == null) return true;
        if (filterRequests.isEmpty()) return true;

        Set<Characteristic> characteristics = equipment.getCharacteristics();
        if (characteristics.size() == 0) return false;

        for (FilterRequest request : filterRequests){
            if (!request.equipmentKey().equalsIgnoreCase(equipment.getEquipmentKey())) return false;

            switch (request.operation()){
                case "=" -> {
                    for (Characteristic characteristic : characteristics){
                        if (!characteristic.getAttribute().getAttribute().equals(request.attributeName())) continue;

                        if (characteristic.getValue().equals(request.value())) return true;
                    }
                }
                case "!=" -> {
                    for (Characteristic characteristic : characteristics){
                        if (!characteristic.getAttribute().getAttribute().equals(request.attributeName())) continue;

                        if (!characteristic.getValue().equals(request.value())) return true;
                    }
                }
                case ">" -> {
                    for (Characteristic characteristic : characteristics){
                        if (!characteristic.getAttribute().getAttribute().equals(request.attributeName())) continue;
                        if (request.type() != ValueType.NUMBER) continue;

                        if (graterThen(request.value(), characteristic.getValue())) return true;
                    }
                }
                case "<" -> {
                    for (Characteristic characteristic : characteristics){
                        if (!characteristic.getAttribute().getAttribute().equals(request.attributeName())) continue;
                        if (request.type() != ValueType.NUMBER) continue;

                        if (lowerThen(request.value(), characteristic.getValue())) return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @return true if first grater then second
     */
    private boolean graterThen(String first, String second){
        return Integer.parseInt(first) < Integer.parseInt(second);
    }

    /**
     * @return true if first lower then second
     */
    private boolean lowerThen(String first, String second){
        return Integer.parseInt(first) > Integer.parseInt(second);
    }

}
