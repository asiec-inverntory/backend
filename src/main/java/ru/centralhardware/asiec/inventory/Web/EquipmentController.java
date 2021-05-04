package ru.centralhardware.asiec.inventory.Web;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.centralhardware.asiec.inventory.Dto.EquipmentDto;
import ru.centralhardware.asiec.inventory.Entity.Attribute;
import ru.centralhardware.asiec.inventory.Entity.Characteristic;
import ru.centralhardware.asiec.inventory.Entity.Enum.AttributeType;
import ru.centralhardware.asiec.inventory.Entity.Enum.EquipmentType;
import ru.centralhardware.asiec.inventory.Entity.Enum.Role;
import ru.centralhardware.asiec.inventory.Entity.Equipment;
import ru.centralhardware.asiec.inventory.Mapper.EquipmentMapper;
import ru.centralhardware.asiec.inventory.Security.JwtTokenUtil;
import ru.centralhardware.asiec.inventory.Service.AttributeService;
import ru.centralhardware.asiec.inventory.Service.CharacteristicService;
import ru.centralhardware.asiec.inventory.Service.EquipmentService;
import ru.centralhardware.asiec.inventory.Service.UserService;
import ru.centralhardware.asiec.inventory.Web.Dto.FilterRequest;
import ru.centralhardware.asiec.inventory.Web.Dto.ReceiveEquipment;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Api(value = "equipment")
@RequestMapping("/equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;
    private final CharacteristicService characteristicService;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AttributeService attributeService;

    public EquipmentController(EquipmentService equipmentService, CharacteristicService characteristicService, UserService userService, JwtTokenUtil jwtTokenUtil, AttributeService attributeService) {
        this.equipmentService = equipmentService;
        this.characteristicService = characteristicService;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.attributeService = attributeService;
    }

    @ApiOperation(
            value = "create equipment",
            httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "successfully receive equipment", response = EquipmentDto.class)
        }
    )
    @PostMapping(path = "receiving")
    public ResponseEntity<?> receivingEquipment(@RequestBody ReceiveEquipment receiveEquipment){
        Equipment equipment = new Equipment();
        equipment.setEquipmentKey(receiveEquipment.type());
        equipment.setSerialCode(receiveEquipment.serialCode());
        equipment.setEquipmentType(EquipmentType.COMPONENT);
        equipmentService.save(equipment);
        receiveEquipment.properties().forEach(it -> {
            var attributeOptional = attributeService.findByName(it.key());
            var characteristic = new Characteristic();
            characteristic.getEquipments().add(equipment);
            characteristic.setValue(it.value());
            if (attributeOptional.isPresent()){
                var attribute = attributeOptional.get();
                switch (attribute.getType()){
                    case NUMBER -> {
                        Integer.parseInt(it.value());
                    }
                    case RANGE -> {
                        var value = Integer.parseInt(it.value());
                        if (!(value >= attribute.getMinimum() && value <= attribute.getMaximum())) throw new OutOfRangeException();
                    }
                }
                characteristic.setAttribute(attributeOptional.get());
            } else {
                var attribute = new Attribute();
                attribute.setAttribute(it.key());
                attribute.setType(AttributeType.STRING);
                characteristic.setAttribute(attribute);
            }
            characteristicService.save(characteristic);
            equipment.getCharacteristics().add(characteristic);
        });
        equipmentService.save(equipment);
        return ResponseEntity.ok(EquipmentMapper.INSTANCE.equipmentToDto(equipment));
    }

//    @ApiOperation(
//            value = "update equipment",
//            httpMethod = "POST",
//            produces = MediaType.APPLICATION_JSON_VALUE
//    )
//    @ApiResponses( value = {
//            @ApiResponse(code = 200, message = "successfully get user", response = EquipmentDto.class),
//            @ApiResponse(code = 401 , message = "unauthorized" ),
//            @ApiResponse(code = 404 , message = "equipment not found" )}
//    )
//    @PostMapping(path = "update")
//    public ResponseEntity<?> updateEquipment(@RequestBody CreateEquipmentDto equipmentDto,@ApiIgnore Principal principal){
//        var userOptional = userService.findByUsername(principal.getName());
//        if (userOptional.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        if (userOptional.get().getRole() != Role.ADMIN || !equipmentService.hasAccess(equipmentDto.id(), userOptional.get())){
//            return ResponseEntity.status(401).build();
//        }
//        if (!equipmentService.existById(equipmentDto.id())){
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(EquipmentMapper.INSTANCE.equipmentToDto(equipmentService.update(equipmentDto, userOptional.get())));
//    }

    @ApiOperation(
            value = "delete equipment",
            httpMethod = "DELETE",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "successfully get user"),
            @ApiResponse(code = 401 , message = "unauthorized" ),
            @ApiResponse(code = 404 , message = "equipment not found" )}
    )
    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> deleteEquipment(@RequestParam int id,@ApiIgnore Principal principal){
        var userOptional = userService.findByUsername(principal.getName());
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (userOptional.get().getRole() != Role.ADMIN || !equipmentService.hasAccess(id, userOptional.get())){
            return ResponseEntity.status(401).build();
        }
        if (!equipmentService.existById(id)){
            equipmentService.delete(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * example filter json:
     * [
     *      {
     *          "attributeName": "",
     *          "operation": "=",
     *          "value": ""
     *      }
     * ]
     * list of support operation:
     *      - != : filter request value and characteristic value are NOT equals
     *      - =  : filter request value and characteristic value are equals
     *      - >  : filter request value grater then characteristic value
     *      - <  : filter request value lower then characteristic value
     * WARNING: for < and > operation, if string can not be parsed its length is taken
     */
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @ApiOperation(
            value = "get pageable list of equipment",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "successfully get user", response = EquipmentDto.class, responseContainer = "List")
    })
    @GetMapping(path = "/list")
    public ResponseEntity<?> getEquipment(@RequestParam int page,
                                          @RequestParam int pageSize,
                                          @RequestParam(required = false) Optional<String> sortBy,
                                          @RequestParam(required = false) String filter,
                                          @ApiIgnore Principal principal) throws JsonProcessingException, ParseException {
        List<FilterRequest> filterRequest = new ArrayList<>();
        if (filter != null){
            JSONObject object = (JSONObject) new JSONParser().parse(filter);
            object.forEach((k,v) -> {
                if (!(v instanceof JSONObject)) return;

                ((JSONObject) v).forEach((key,value) -> {
                    AttributeType type = attributeService.getAttributeType((String) key);
                    if (type == null) return;

                    switch (type){
                        case STRING -> {
                            if (value instanceof JSONArray){
                                ((JSONArray) value).forEach(it -> {
                                    filterRequest.add(new FilterRequest(
                                            ValueType.STRING,
                                            (String) k,
                                            (String) key,
                                            "=",
                                            (String) it
                                    ));
                                });
                            } else {
                                filterRequest.add(new FilterRequest(
                                        ValueType.STRING,
                                        (String) k,
                                        (String) key,
                                        "=",
                                        (String) value
                                ));
                            }
                        }
                        case NUMBER -> {
                            if (value instanceof JSONArray){
                                ((JSONArray) value).forEach(it -> {
                                    filterRequest.add(new FilterRequest(
                                            ValueType.NUMBER,
                                            (String) k,
                                            (String) key,
                                            "=",
                                            (String) it
                                    ));
                                });
                            } else {
                                filterRequest.add(new FilterRequest(
                                        ValueType.NUMBER,
                                        (String) k,
                                        (String) key,
                                        "=",
                                        (String) value
                                ));
                            }
                        }
                        case RANGE -> {
                            filterRequest.add(new FilterRequest(
                                    ValueType.NUMBER,
                                    (String) k,
                                    (String) key,
                                    ">",
                                    ((org.json.simple.JSONArray)value).get(0).toString()
                            ));
                            filterRequest.add(new FilterRequest(
                                    ValueType.NUMBER,
                                    (String) k,
                                    (String) key,
                                    "<",
                                    ((org.json.simple.JSONArray)value).get(1).toString()
                            ));
                        }
                    }
                });
            });
        }

        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(sortBy.orElse("equipmentKey")));
        var userOptional = userService.findByUsername(principal.getName());
        if (userOptional.isEmpty()) return ResponseEntity.notFound().build();
        if (userOptional.get().getRole() == Role.ADMIN){
            return ResponseEntity.ok(equipmentService.list(pageable, filterRequest));
        } else {
            return ResponseEntity.ok(equipmentService.list(userOptional.get(), pageable, filterRequest));
        }
    }
}