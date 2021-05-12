package ru.centralhardware.asiec.inventory.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.centralhardware.asiec.inventory.Entity.EquipmentType;

import java.util.Optional;

@Repository
public interface EquipmentTypeRepository extends CrudRepository<EquipmentType, Integer> {

    Optional<EquipmentType> findByTypeName(String typeName);
}
