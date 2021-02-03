package ru.centralhardware.asiec.inventory.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.centralhardware.asiec.inventory.Entity.Equipment;

@Repository
public interface EquipmentRepository extends CrudRepository<Equipment, Integer> {
}
