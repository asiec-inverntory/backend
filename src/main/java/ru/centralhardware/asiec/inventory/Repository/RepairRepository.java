package ru.centralhardware.asiec.inventory.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.centralhardware.asiec.inventory.Entity.Repair;

@Repository
@Transactional(rollbackFor=Throwable.class)
public interface RepairRepository extends CrudRepository<Repair, Integer> {
}
