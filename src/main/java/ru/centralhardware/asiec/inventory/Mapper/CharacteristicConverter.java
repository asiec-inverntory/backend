package ru.centralhardware.asiec.inventory.Mapper;

import ru.centralhardware.asiec.inventory.Dto.HumanReadableHolder;
import ru.centralhardware.asiec.inventory.Dto.PropertyDto;
import ru.centralhardware.asiec.inventory.Entity.Characteristic;
import ru.centralhardware.asiec.inventory.Service.CharacteristicService;
import ru.centralhardware.asiec.inventory.SpringContext;

import java.util.Set;
import java.util.stream.Collectors;

public class CharacteristicConverter {

    public Set<Characteristic> fromDto (Set<Integer> characteristic){
        return characteristic.
                stream().
                map(it -> SpringContext.getBean(CharacteristicService.class).findById(it).orElse(null)).
                collect(Collectors.toSet());
    }

    public Set<Integer> toDto(Set<Characteristic> characteristics) {
        return characteristics.
                stream().
                map(Characteristic::getId).
                collect(Collectors.toSet());
    }

    public PropertyDto toHumanReadable(Characteristic characteristic){
        if (characteristic == null) return null;
        return new PropertyDto(characteristic.getId(),
                characteristic.getAttribute().getHumanReadable(),
                characteristic.getValue());
    }

    public Set<PropertyDto> toHumanReadableSet(Set<Characteristic> characteristics){
        return characteristics.
                stream().
                map(this::toHumanReadable).
                collect(Collectors.toSet());
    }

}