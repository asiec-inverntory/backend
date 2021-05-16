package ru.centralhardware.asiec.inventory.Filter;

import ru.centralhardware.asiec.inventory.Web.Dto.EquipmentDto;
import ru.centralhardware.asiec.inventory.Entity.Characteristic;
import ru.centralhardware.asiec.inventory.Entity.Equipment;
import ru.centralhardware.asiec.inventory.Entity.InventoryUser;
import ru.centralhardware.asiec.inventory.Mapper.EquipmentMapper;
import ru.centralhardware.asiec.inventory.SpringContext;
import ru.centralhardware.asiec.inventory.Web.Dto.FilterRequest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EquipmentFilter {

    private final List<FilterRequest> filterRequests;
    private final EquipmentMapper equipmentMapper;

    /**
     * example filter json:
     * [
     *      {
     *          "attributeName": "",
     *          "operation": "=",
     *          "value": ""
     *      }
     * ]
     * list of support operation:
     *      - != : filter request value and characteristic value are NOT equals
     *      - =  : filter request value and characteristic value are equals
     *      - >  : filter request value grater then characteristic value
     *      - <  : filter request value lower then characteristic value
     */
    protected EquipmentFilter(List<FilterRequest> filterRequests){
        this.filterRequests = filterRequests;
        this.equipmentMapper = SpringContext.getBean(EquipmentMapper.class);
    }


    public List<EquipmentDto> filter(List<Equipment> equipments, InventoryUser user){
        return equipments.
                stream().
                filter(it -> it.getResponsible().equals(user)).
                filter(equipment -> isFiltered(filterRequests, equipment)).
                map(equipmentMapper::equipmentToDto).
                collect(Collectors.toList());
    }

    public List<EquipmentDto> filter(List<Equipment> equipments){
        return equipments.
                stream().
                filter(equipment -> isFiltered(filterRequests, equipment)).
                map(equipmentMapper::equipmentToDto).
                collect(Collectors.toList());
    }

    /**
     * @param filterRequests filter conditions
     * @param equipment list of equipment for filter
     * @return true if must filtered
     */
    private boolean isFiltered(List<FilterRequest> filterRequests, Equipment equipment){
        if (filterRequests == null) return true;
        if (filterRequests.isEmpty()) return true;

        for (FilterRequest request : filterRequests){
            if (request.attributeName().equalsIgnoreCase("responsible")){
                if (equipment.getResponsible().getFio().equalsIgnoreCase(request.value())) return true;
            }
        }

        Set<Characteristic> characteristics = equipment.getCharacteristics();
        if (characteristics.size() == 0) return false;

        int matchCount = 0;
        for (FilterRequest request : filterRequests){
            if (!request.equipmentKey().equalsIgnoreCase(equipment.getEquipmentType().getTypeName())) return false;

            switch (request.operation()){
                case "=" -> {
                    for (Characteristic characteristic : characteristics){
                        if (!characteristic.getAttribute().getAttribute().equals(request.attributeName())) continue;

                        if (characteristic.getValue().equalsIgnoreCase(request.value())) matchCount++;
                    }
                }
                case "!=" -> {
                    for (Characteristic characteristic : characteristics){
                        if (!characteristic.getAttribute().getAttribute().equals(request.attributeName())) continue;

                        if (!characteristic.getValue().equalsIgnoreCase(request.value())) matchCount++;
                    }
                }
                case ">" -> {
                    for (Characteristic characteristic : characteristics){
                        if (!characteristic.getAttribute().getAttribute().equals(request.attributeName())) continue;
                        if (request.type() != ValueType.NUMBER) continue;

                        if (graterThen(request.value(), characteristic.getValue())) matchCount++;
                    }
                }
                case "<" -> {
                    for (Characteristic characteristic : characteristics){
                        if (!characteristic.getAttribute().getAttribute().equals(request.attributeName())) continue;
                        if (request.type() != ValueType.NUMBER) continue;

                        if (lowerThen(request.value(), characteristic.getValue())) matchCount++;
                    }
                }
            }
        }
        return matchCount == filterRequests.size();
    }

    /**
     * @return true if first grater then second
     */
    private boolean graterThen(String first, String second){
        return Integer.parseInt(first) >= Integer.parseInt(second);
    }

    /**
     * @return true if first lower then second
     */
    private boolean lowerThen(String first, String second){
        return Integer.parseInt(first) <= Integer.parseInt(second);
    }

}
