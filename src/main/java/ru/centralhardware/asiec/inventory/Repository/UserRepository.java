package ru.centralhardware.asiec.inventory.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.centralhardware.asiec.inventory.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
