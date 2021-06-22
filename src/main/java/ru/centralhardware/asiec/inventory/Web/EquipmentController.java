package ru.centralhardware.asiec.inventory.Web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.centralhardware.asiec.inventory.Web.Dto.EquipmentDto;
import ru.centralhardware.asiec.inventory.Entity.Characteristic;
import ru.centralhardware.asiec.inventory.Entity.Enum.EquipmentVariant;
import ru.centralhardware.asiec.inventory.Entity.Enum.Role;
import ru.centralhardware.asiec.inventory.Entity.Equipment;
import ru.centralhardware.asiec.inventory.Filter.EquipmentFilterBuilder;
import ru.centralhardware.asiec.inventory.Filter.EquipmentFilter;
import ru.centralhardware.asiec.inventory.Mapper.EquipmentMapper;
import ru.centralhardware.asiec.inventory.Security.JwtTokenUtil;
import ru.centralhardware.asiec.inventory.Service.*;
import ru.centralhardware.asiec.inventory.Web.Dto.ReceiveEquipment;
import ru.centralhardware.asiec.inventory.Web.ExceptionHander.Exception.OutOfRangeException;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
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
    private final EquipmentTypeService equipmentTypeService;
    private final UserService userService;
    private final AttributeService attributeService;
    private final EquipmentMapper equipmentMapper;

    public EquipmentController(EquipmentService equipmentService,
                               CharacteristicService characteristicService,
                               EquipmentTypeService equipmentTypeService,
                               UserService userService,
                               JwtTokenUtil jwtTokenUtil,
                               AttributeService attributeService,
                               EquipmentMapper equipmentMapper) {
        this.equipmentService = equipmentService;
        this.characteristicService = characteristicService;
        this.equipmentTypeService = equipmentTypeService;
        this.userService = userService;
        this.attributeService = attributeService;
        this.equipmentMapper = equipmentMapper;
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
    public ResponseEntity<?> receivingEquipment(@RequestBody List<ReceiveEquipment> receiveEquipments){
        List<EquipmentDto> res = new ArrayList<>();
        for (ReceiveEquipment receiveEquipment : receiveEquipments){
            Equipment equipment = new Equipment();

            var type = equipmentTypeService.findByName(receiveEquipment.type());
            if (type.isEmpty()) return ResponseEntity.status(410).build();

            equipment.setEquipmentType(type.get());
            equipment.setSerialCode(receiveEquipment.serial_code());
            equipment.setEquipmentVariant(EquipmentVariant.COMPONENT);
            equipmentService.save(equipment);
            receiveEquipment.properties().forEach(it -> {
                var attributeOptional = attributeService.findByName(it.key());
                var characteristic = new Characteristic();
                characteristic.getEquipments().add(equipment);
                characteristic.setValue(it.value());
                if (attributeOptional.isPresent()){
                    var attribute = attributeOptional.get();
                    switch (attribute.getType()){
                        case NUMBER -> //noinspection ResultOfMethodCallIgnored
                                Integer.parseInt(it.value());
                        case RANGE -> {
                            var value = Integer.parseInt(it.value());
                            if (!(value >= attribute.getMinimum() && value <= attribute.getMaximum())) throw new OutOfRangeException();
                        }
                    }
                    characteristic.setAttribute(attributeOptional.get());
                }
                characteristicService.save(characteristic);
                equipment.getCharacteristics().add(characteristic);
            });
            equipmentService.save(equipment);
            res.add(equipmentMapper.equipmentToDto(equipment));
        }
        return ResponseEntity.ok(res);
    }

    @ApiOperation(
            value = "search equipment",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "successfully get searched equipment", response = EquipmentDto.class)
    }
    )
    @GetMapping(path = "search")
    public ResponseEntity<?> search(@RequestParam(required = false) String serialCode,
                                    @RequestParam(required = false) String inventoryCode){
        return ResponseEntity.ok(equipmentService.findBySerialAndInventoryCode(serialCode, inventoryCode));
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
    @DeleteMapping(path = "delete/{id}")
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


    @SuppressWarnings({"OptionalUsedAsFieldOrParameterType"})
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
                                          @RequestParam(required = false) String search,
                                          @ApiIgnore Principal principal,
                                          HttpServletResponse response) throws ParseException {
        EquipmentFilter equipmentFilter = EquipmentFilterBuilder.of(filter);


        response.addHeader("X-Page-Count", String.valueOf(equipmentService.getPageCount(pageSize)));
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(sortBy.orElse("equipmentVariant")));
        var userOptional = userService.findByUsername(principal.getName());
        if (userOptional.isEmpty()) return ResponseEntity.notFound().build();

        List<EquipmentDto> res = new ArrayList<>();

        List<EquipmentDto> fullList ;
        if (userOptional.get().getRole() == Role.ADMIN){
            fullList = equipmentFilter.filter(equipmentService.list(pageable));
        } else {
            fullList = equipmentFilter.filter(equipmentService.list(userOptional.get(), pageable), userOptional.get());
        }

        if (search != null){
            fullList.forEach(it -> {
                if (it.serialCode() != null && it.serialCode().toLowerCase().contains(search)){
                    res.add(it);
                }
                if (it.inventoryCode() != null && it.inventoryCode().toLowerCase().contains(search)){
                    res.add(it);
                }
            });
        } else {
            res.addAll(fullList);
        }
        return ResponseEntity.ok(res.stream().distinct().toList());
    }
}