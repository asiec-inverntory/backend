package ru.centralhardware.asiec.inventory.Repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.centralhardware.asiec.inventory.Entity.Equipment;
import ru.centralhardware.asiec.inventory.Entity.InventoryUser;

import java.util.List;

@Repository
public interface EquipmentRepository extends PagingAndSortingRepository<Equipment, Integer> {

    List<Equipment> findAllByResponsible(InventoryUser responsible, Pageable sort);

}
