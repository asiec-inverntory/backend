package ru.centralhardware.asiec.inventory.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.centralhardware.asiec.inventory.Entity.RepairService;

@Repository
public interface RepairServiceRepository  extends CrudRepository<RepairService, Integer> {
}
