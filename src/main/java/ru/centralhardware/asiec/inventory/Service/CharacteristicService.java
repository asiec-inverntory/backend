package ru.centralhardware.asiec.inventory.Service;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
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

    public void save(@NotNull Characteristic characteristic){
        characteristicRepository.save(characteristic);
    }
}
