package ru.centralhardware.asiec.inventory.Repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.centralhardware.asiec.inventory.Entity.Attribute;

import java.util.Optional;

@Repository
@Transactional(rollbackFor=Throwable.class)
public interface AttributeRepository extends PagingAndSortingRepository<Attribute, Integer> {

    Optional<Attribute> findByAttribute(String name);

}
