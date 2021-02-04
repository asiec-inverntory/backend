package ru.centralhardware.asiec.inventory.Service;

import org.springframework.stereotype.Service;
import ru.centralhardware.asiec.inventory.Entity.Position;
import ru.centralhardware.asiec.inventory.Repository.PositionRepository;

import java.util.Optional;

@Service
public class PositionService {

    private final PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public Optional<Position> findById(int id){
        return positionRepository.findById(id);
    }
}
