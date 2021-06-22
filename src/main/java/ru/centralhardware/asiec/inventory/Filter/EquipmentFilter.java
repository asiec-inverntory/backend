package ru.centralhardware.asiec.inventory.Filter;

import org.jetbrains.annotations.NotNull;
import ru.centralhardware.asiec.inventory.Web.Dto.EquipmentDto;
import ru.centralhardware.asiec.inventory.Entity.Equipment;
import ru.centralhardware.asiec.inventory.Entity.InventoryUser;
import ru.centralhardware.asiec.inventory.Mapper.EquipmentMapper;
import ru.centralhardware.asiec.inventory.SpringContext;
import ru.centralhardware.asiec.inventory.Web.Dto.FilterRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
    protected EquipmentFilter(@NotNull List<FilterRequest> filterRequests){
        this.filterRequests = filterRequests;
        this.equipmentMapper = SpringContext.getBean(EquipmentMapper.class);
    }

    public List<FilterRequest> getFilterRequests() {
        return List.of(filterRequests.toArray(new FilterRequest[]{}));
    }

    public List<EquipmentDto> filter(@NotNull List<Equipment> equipments, @NotNull InventoryUser user){
        return null;
    }

    public List<EquipmentDto> filter(@NotNull List<Equipment> equipments){
        List<EquipmentDto> res = new ArrayList<>();

        equipments.stream().forEach(equipment -> {
            equipment.getCharacteristics().stream().forEach(characteristic -> {
                AtomicInteger matchCount = new AtomicInteger();
                filterRequests.stream().forEach(request -> {
                    if (request.equipmentKey().equalsIgnoreCase(equipment.getEquipmentType().getTypeName())){
                       if (request.attributeName().equalsIgnoreCase("responsible")){
                           String[] word = equipment.getResponsible().getFio().split(" ");
                           String fio = String.format("%s %s. %s", word[0], word[1].charAt(0), word[2].charAt(0));
                            if (fio.equalsIgnoreCase(request.value())){
                                matchCount.getAndIncrement();
                            }
                       } else if (request.attributeName().equalsIgnoreCase("type")){
                            if (equipment.getEquipmentType().getTypeName().equalsIgnoreCase(request.value())){
                                matchCount.getAndIncrement();
                            }
                       } else {
                           if (request.attributeName().equalsIgnoreCase(characteristic.getAttribute().getAttribute())){
                               switch (request.operation()){
                                   case "!=" -> {
                                       if (!request.value().equalsIgnoreCase(characteristic.getValue())){
                                           matchCount.getAndIncrement();
                                       }
                                   }
                                   case "=" -> {
                                       if (request.value().equalsIgnoreCase(characteristic.getValue())){
                                           matchCount.getAndIncrement();
                                       }
                                   }
                                   case ">" -> {
                                       if (request.type() != ValueType.NUMBER) {
                                           if (graterThen(request.value(), characteristic.getValue())){
                                               matchCount.getAndIncrement();
                                           }
                                       }
                                   }
                                   case "<" -> {
                                       if (request.type() != ValueType.NUMBER){
                                           if (lowerThen(request.value(), characteristic.getValue())) {
                                               matchCount.getAndIncrement();
                                           }
                                       };
                                   }
                               }
                           }
                       }
                    }
                });
                if (matchCount.get() == filterRequests.size()) {
                    res.add(equipmentMapper.equipmentToDto(equipment));
                }
            });
        });
        return res;
    }

    /**
     * @return true if first grater then second
     */
    private boolean graterThen(@NotNull String first, @NotNull String second){
        return Integer.parseInt(first) >= Integer.parseInt(second);
    }

    /**
     * @return true if first lower then second
     */
    private boolean lowerThen(@NotNull String first, @NotNull String second){
        return Integer.parseInt(first) <= Integer.parseInt(second);
    }

}
