package ru.centralhardware.asiec.inventory.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.centralhardware.asiec.inventory.Dto.Create.CreateEquipmentDto;
import ru.centralhardware.asiec.inventory.Dto.EquipmentDto;
import ru.centralhardware.asiec.inventory.Entity.Characteristic;
import ru.centralhardware.asiec.inventory.Entity.Equipment;
import ru.centralhardware.asiec.inventory.Entity.InventoryUser;
import ru.centralhardware.asiec.inventory.Mapper.EquipmentMapper;
import ru.centralhardware.asiec.inventory.Repository.EquipmentRepository;
import ru.centralhardware.asiec.inventory.Web.Dto.FilterRequest;

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

    public boolean existById(int id){
        var equipment = equipmentRepository.findById(id);
        return equipment.isPresent() && !equipment.get().isDeleted();
    }

    public boolean hasAccess(int id, InventoryUser user){
        var equipment = equipmentRepository.findById(id);
        return equipment.map(value -> value.getResponsible().getId().equals(id)).orElse(false);
    }

    public Equipment create(CreateEquipmentDto dto, InventoryUser createdBy){
        var equipment = EquipmentMapper.INSTANCE.dtoToEquipment(dto);
        return equipmentRepository.save(equipment);
    }

    public Equipment update(CreateEquipmentDto dto, InventoryUser updatedBy){
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

    /**
     * @param filterRequests filter conditions
     * @param equipment list of equipment for filter
     * @return true if must filtered
     */
    private boolean isFiltered(List<FilterRequest> filterRequests, Equipment equipment){
        if (filterRequests == null) return true;

        Set<Characteristic> characteristics = equipment.getCharacteristics();
        if (characteristics.size() == 0) return false;

        for (FilterRequest request : filterRequests){
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

                        if (graterThen(request.value(), characteristic.getValue())) return true;
                    }
                }
                case "<" -> {
                    for (Characteristic characteristic : characteristics){
                        if (!characteristic.getAttribute().getAttribute().equals(request.attributeName())) continue;

                        if (lowerThen(request.value(), characteristic.getValue())) return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param f
     * @param s
     * @return true if first grater then second
     */
    private boolean graterThen(String f, String s){
        int first = str2int(f);
        int second = str2int(s);
        return first < second;
    }

    /**
     * @param f
     * @param s
     * @return true if first lower then second
     */
    private boolean lowerThen(String f, String s){
        int first = str2int(f);
        int second = str2int(s);
        return first > second;
    }

    private int str2int(String str){
        if (tryParse(str)){
            return Integer.parseInt(str);
        } else {
            return str.length();
        }
    }

    private boolean tryParse(String str){
        try {
            int i = Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
}
