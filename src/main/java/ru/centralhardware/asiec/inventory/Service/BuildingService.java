package ru.centralhardware.asiec.inventory.Service;

import org.springframework.stereotype.Service;
import ru.centralhardware.asiec.inventory.Entity.Room;
import ru.centralhardware.asiec.inventory.Repository.BuildingRepository;

import java.util.List;

@Service
public class BuildingService {

    private final BuildingRepository buildingRepository;

    public BuildingService(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    public List<Integer> getRoomList(int buildingId){
        var building =  buildingRepository.findById(buildingId);

        if (building.isPresent()){
            return building.get().getRooms().
                    stream().
                    map(Room::getNumber).
                    sorted().
                    toList();
        } else {
            return List.of();
        }
    }

}
