package ru.centralhardware.asiec.inventory.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.w3c.dom.Attr;
import ru.centralhardware.asiec.inventory.Dto.AttributeDto;
import ru.centralhardware.asiec.inventory.Dto.EquipmentDto;
import ru.centralhardware.asiec.inventory.Entity.Attribute;
import ru.centralhardware.asiec.inventory.Entity.Characteristic;
import ru.centralhardware.asiec.inventory.Entity.Equipment;
import ru.centralhardware.asiec.inventory.Repository.AttributeRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public String getAttributesName(Pageable sort) throws JsonProcessingException {
        List<Equipment> equipment = equipmentService.findAll(sort);
        return formatAnswer(equipment);
    }

    public String getAttributesForEquipment(int equipmentId) throws JsonProcessingException {
        return formatAnswer(equipmentService.findById(equipmentId).stream().toList());
    }

    private String formatAnswer(List<Equipment> equipments) throws JsonProcessingException {
        Map<String, List<AttributeDto>> content = new HashMap<>();
        for (Equipment equipment : equipments){
            if (equipment.getCharacteristics().size() == 0) continue;

            content.put(equipment.getName(), getDto(equipment.getCharacteristics().
                    stream().
                    map(Characteristic::getAttribute).
                    collect(Collectors.toList())));
        }
        return new ObjectMapper().writeValueAsString(content);
    }

    private List<AttributeDto> getDto(List<Attribute> attributes){
        List<AttributeDto> res = new ArrayList<>();
        for (Attribute attribute : attributes){
            res.add(new AttributeDto(
                    attribute.getAttribute(),
                    attribute.getType(),
                    attribute.getMinimum(),
                    attribute.getMaximum(),
                    attribute.getCharacteristics().stream().map(Characteristic::getValue).collect(Collectors.toList())));
        }
        return res;
    }

}
