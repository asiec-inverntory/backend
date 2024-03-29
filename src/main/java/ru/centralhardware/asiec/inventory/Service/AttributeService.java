package ru.centralhardware.asiec.inventory.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.centralhardware.asiec.inventory.Web.Dto.AttributeDto;
import ru.centralhardware.asiec.inventory.Entity.Attribute;
import ru.centralhardware.asiec.inventory.Entity.Characteristic;
import ru.centralhardware.asiec.inventory.Entity.Enum.AttributeType;
import ru.centralhardware.asiec.inventory.Entity.Equipment;
import ru.centralhardware.asiec.inventory.Repository.AttributeRepository;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

@Service
public class AttributeService {

    private final AttributeRepository repository;
    private final EquipmentService equipmentService;

    public AttributeService(AttributeRepository repository, EquipmentService equipmentService) {
        this.repository = repository;
        this.equipmentService = equipmentService;
    }

    public void save(@NotNull Attribute attribute){
        repository.save(attribute);
    }

    public Optional<Attribute> findByName(@NotNull String name){
        return repository.findByAttribute(name);
    }

    public String getAttributesList() throws JsonProcessingException {
        List<Equipment> equipment = equipmentService.findAll();
        return formatAnswer(equipment);
    }

    public AttributeType getAttributeType(@NotNull String name){
        var attribute =  repository.findByAttribute(name);

        return attribute.map(Attribute::getType).orElse(null);
    }

    public String getAttributesForEquipment(int equipmentId) throws JsonProcessingException {
        return formatAnswer(equipmentService.findById(equipmentId).stream().toList());
    }

    private String formatAnswer(@NotNull List<Equipment> equipments) throws JsonProcessingException {
        Map<String, List<AttributeDto>> content = new HashMap<>();
        Map<String, String> humanReadable = new HashMap<>();
        for (Equipment equipment : equipments){
            if (equipment.getCharacteristics().size() == 0) continue;

            if (content.containsKey(equipment.getEquipmentType().getTypeName())){
                var toAppend = getDto(equipment.getCharacteristics().
                        stream().
                        map(Characteristic::getAttribute).
                        filter(not(it -> content.get(equipment.getEquipmentType().getTypeName()).contains(it))).
                        toList());
                content.put(equipment.getEquipmentType().getTypeName(), toAppend);
            } else {
                content.put(equipment.getEquipmentType().getTypeName(), getDto(equipment.getCharacteristics().
                        stream().
                        map(Characteristic::getAttribute).
                        toList()));
            }
            humanReadable.put(equipment.getEquipmentType().getTypeName(), equipment.getEquipmentType().getHumanReadable());
        }
        return new ObjectMapper().writeValueAsString(List.of(content, humanReadable));
    }

    private List<AttributeDto> getDto(@NotNull List<Attribute> attributes){
        List<AttributeDto> res = new ArrayList<>();
        for (Attribute attribute : attributes){
            res.add(new AttributeDto(
                    attribute.getAttribute(),
                    attribute.getType(),
                    attribute.getMinimum(),
                    attribute.getMaximum(),
                    attribute.getHumanReadable(),
                    attribute.getCharacteristics().
                            stream().
                            map(Characteristic::getValue).
                            distinct().
                            sorted().
                            collect(Collectors.toList()),
                    attribute.getCharacteristics().
                            stream().
                            map(it -> it.getEquipments()
                                    .stream().
                                            filter(equipment  -> equipment.getResponsible() != null).
                                            map(equipment -> equipment.getResponsible().getFio()).
                                            toList()).
                            flatMap(List::stream).
                            distinct().
                            sorted().
                            toList()));
        }
        return res;
    }

}
