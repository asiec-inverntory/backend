package ru.centralhardware.asiec.inventory.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.centralhardware.asiec.inventory.Entity.Building;

@Repository
public interface BuildingRepository extends CrudRepository<Building, Integer> {
}
