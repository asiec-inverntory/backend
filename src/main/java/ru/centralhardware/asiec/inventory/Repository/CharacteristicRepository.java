package ru.centralhardware.asiec.inventory.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.centralhardware.asiec.inventory.Entity.Characteristic;

@Repository
public interface CharacteristicRepository extends CrudRepository<Characteristic, Integer> {
}
