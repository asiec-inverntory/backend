package ru.centralhardware.asiec.inventory.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.centralhardware.asiec.inventory.Entity.InventoryUser;

import java.util.Optional;

@Repository
@Transactional(rollbackFor=Throwable.class)
public interface UserRepository extends JpaRepository<InventoryUser, Integer> {

    Optional<InventoryUser> findByUsername(String username);

}
