package ru.centralhardware.asiec.inventory.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.centralhardware.asiec.inventory.Entity.Characteristic;

@Repository
@Transactional(rollbackFor=Throwable.class)
public interface CharacteristicRepository extends CrudRepository<Characteristic, Integer> {
}
