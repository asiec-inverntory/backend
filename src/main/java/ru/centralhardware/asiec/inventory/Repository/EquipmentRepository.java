package ru.centralhardware.asiec.inventory.Repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.centralhardware.asiec.inventory.Entity.Equipment;
import ru.centralhardware.asiec.inventory.Entity.InventoryUser;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(rollbackFor=Throwable.class)
public interface EquipmentRepository extends PagingAndSortingRepository<Equipment, Integer> {

    List<Equipment> findAllByResponsible(InventoryUser responsible, Pageable sort);

    Optional<Equipment> findBySerialCodeAndInventoryCode(String serialCode, String inventoryCode);

    List<Equipment> findAll();

}
