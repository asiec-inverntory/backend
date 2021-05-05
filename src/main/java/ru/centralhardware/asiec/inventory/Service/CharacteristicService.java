package ru.centralhardware.asiec.inventory.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.centralhardware.asiec.inventory.Entity.Characteristic;
import ru.centralhardware.asiec.inventory.Repository.CharacteristicRepository;

import java.util.Optional;

@Service
public class CharacteristicService {

    private final CharacteristicRepository characteristicRepository;

    public CharacteristicService(CharacteristicRepository characteristicRepository) {
        this.characteristicRepository = characteristicRepository;
    }

    public Optional<Characteristic> findById(int id){
        return characteristicRepository.findById(id);
    }

    public void save(Characteristic characteristic){
        characteristicRepository.save(characteristic);
    }
}
