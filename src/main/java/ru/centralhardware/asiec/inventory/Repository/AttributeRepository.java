package ru.centralhardware.asiec.inventory.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.centralhardware.asiec.inventory.Entity.Attribute;

@Repository
public interface AttributeRepository extends CrudRepository<Attribute, Integer> {
}
