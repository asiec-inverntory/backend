package ru.centralhardware.asiec.inventory.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.centralhardware.asiec.inventory.Entity.Repair;

@Repository
public interface RepairRepository extends CrudRepository<Repair, Integer> {
}
