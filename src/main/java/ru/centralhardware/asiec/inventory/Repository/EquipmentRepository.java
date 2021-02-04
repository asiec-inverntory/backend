package ru.centralhardware.asiec.inventory.Repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.centralhardware.asiec.inventory.Entity.Equipment;

@Repository
public interface EquipmentRepository extends PagingAndSortingRepository<Equipment, Integer> {



}
