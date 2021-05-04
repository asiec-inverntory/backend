package ru.centralhardware.asiec.inventory.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.centralhardware.asiec.inventory.Entity.Room;
import ru.centralhardware.asiec.inventory.Repository.RoomRepository;

import java.util.Optional;

import static java.util.function.Predicate.not;

@Service
@Transactional(rollbackFor=Exception.class)
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Optional<Room> findById(int id){
        return roomRepository.findById(id).filter(not(Room::isDeleted));
    }
}
