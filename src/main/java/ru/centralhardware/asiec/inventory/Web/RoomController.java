package ru.centralhardware.asiec.inventory.Web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.centralhardware.asiec.inventory.Dto.EquipmentDto;
import ru.centralhardware.asiec.inventory.Service.BuildingService;

@RestController
@Api(value = "room")
@RequestMapping("/room")
public class RoomController {

    private final BuildingService buildingService;

    public RoomController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @SuppressWarnings("unchecked")
    @ApiOperation(
            value = "get all room",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "successfully get room", response = EquipmentDto.class, responseContainer = "List")
    })
    @GetMapping(path = "/all")
    public ResponseEntity<?> all(){
        JSONObject building1 = new JSONObject();
        building1.put("number", 1);
        var building1Room = new JSONArray();
        building1Room.addAll(buildingService.getRoomList(1));
        building1.put( "rooms", building1Room);

        JSONObject building2 = new JSONObject();
        building2.put("number", 2);
        var building2Room = new JSONArray();
        building2Room.addAll(buildingService.getRoomList(1));
        building2.put( "rooms", building2Room);

        JSONArray buildings = new JSONArray();
        buildings.add(building1);
        buildings.add(building2);

        JSONObject res = new JSONObject();
        res.put("buildings", buildings);

        return ResponseEntity.ok(res.toJSONString());
    }

}
