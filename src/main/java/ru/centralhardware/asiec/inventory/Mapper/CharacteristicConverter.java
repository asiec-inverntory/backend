package ru.centralhardware.asiec.inventory.Mapper;

import ru.centralhardware.asiec.inventory.Entity.Characteristic;
import ru.centralhardware.asiec.inventory.Service.CharacteristicService;
import ru.centralhardware.asiec.inventory.SpringContext;

import java.util.Set;
import java.util.stream.Collectors;

public class CharacteristicConverter {

    Set<Characteristic> fromDto (Set<Integer> characteristic){
        return characteristic.
                stream().
                map(it -> SpringContext.getBean(CharacteristicService.class).findById(it).orElse(null)).
                collect(Collectors.toSet());
    }

    Set<Integer> toDto(Set<Characteristic> characteristics) {
        return characteristics.
                stream().
                map(Characteristic::getId).
                collect(Collectors.toSet());
    }

}
