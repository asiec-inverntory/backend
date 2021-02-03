package ru.centralhardware.asiec.inventory.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.centralhardware.asiec.inventory.Entity.Unit;

@Repository
public interface UnitRepository extends CrudRepository<Unit, Integer> {
}
