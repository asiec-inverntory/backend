package ru.centralhardware.asiec.inventory.Repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.centralhardware.asiec.inventory.Entity.Attribute;

@Repository
public interface AttributeRepository extends PagingAndSortingRepository<Attribute, Integer> {
}
