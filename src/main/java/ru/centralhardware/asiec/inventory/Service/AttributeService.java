package ru.centralhardware.asiec.inventory.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.centralhardware.asiec.inventory.Entity.Attribute;
import ru.centralhardware.asiec.inventory.Repository.AttributeRepository;

import java.util.List;
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

    public List<String> getAttributesName(Pageable sort){
        return repository.
                findAll(sort).
                get().
                filter(not(Attribute::isDeleted)).
                map(Attribute::getAttribute).
                collect(Collectors.toList());
    }

    public List<String> getAttributesForEquipment(int equipmentId){
        var equipment = equipmentService.findById(equipmentId);
        return equipment.map(value -> value.
                getCharacteristics().
                stream().
                filter(it -> !it.getAttribute().isDeleted()).
                map(it -> it.getAttribute().getAttribute()).
                collect(Collectors.toList())).orElseGet(List::of);
    }
}
