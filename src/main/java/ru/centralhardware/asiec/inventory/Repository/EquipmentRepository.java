package ru.centralhardware.asiec.inventory.Repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.centralhardware.asiec.inventory.Entity.Equipment;

import java.util.List;

@Repository
public interface EquipmentRepository extends PagingAndSortingRepository<Equipment, Integer> {

    List<Equipment> findAllByUsername(String username, Pageable sort);

}
