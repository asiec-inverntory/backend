package ru.centralhardware.asiec.inventory.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.centralhardware.asiec.inventory.Entity.InventoryOrder;

@Repository
@Transactional(rollbackFor=Throwable.class)
public interface InventoryOrderRepository extends CrudRepository<InventoryOrder, Integer> {
}
