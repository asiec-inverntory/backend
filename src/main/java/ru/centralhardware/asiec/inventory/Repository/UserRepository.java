package ru.centralhardware.asiec.inventory.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.centralhardware.asiec.inventory.Entity.InventoryUser;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<InventoryUser, Integer> {

    Optional<InventoryUser> findByUsername(String username);

}
