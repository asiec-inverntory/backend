package ru.centralhardware.asiec.inventory.Web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.centralhardware.asiec.inventory.Dto.Create.CreateEquipmentDto;
import ru.centralhardware.asiec.inventory.Dto.EquipmentDto;
import ru.centralhardware.asiec.inventory.Entity.Enum.Role;
import ru.centralhardware.asiec.inventory.Mapper.EquipmentMapper;
import ru.centralhardware.asiec.inventory.Security.JwtTokenUtil;
import ru.centralhardware.asiec.inventory.Service.EquipmentService;
import ru.centralhardware.asiec.inventory.Service.UserService;
import ru.centralhardware.asiec.inventory.Web.Dto.FilterRequest;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@Api(value = "equipment")
@RequestMapping("/equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    public EquipmentController(EquipmentService equipmentService, UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.equipmentService = equipmentService;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @ApiOperation(
            value = "create equipment",
            httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "successfully get equipment", response = EquipmentDto.class),
            @ApiResponse(code = 404 , message = "not found" )}
    )
    @PostMapping(path = "create")
    public ResponseEntity<?> createEquipment(@RequestBody CreateEquipmentDto equipmentDto,@ApiIgnore Principal principal){
        var userOptional = userService.findByUsername(principal.getName());
        if (userOptional.isPresent()){
            return ResponseEntity.ok(EquipmentMapper.INSTANCE.equipmentToDto(equipmentService.create(equipmentDto,userOptional.get())));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @ApiOperation(
            value = "update equipment",
            httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "successfully get user", response = EquipmentDto.class),
            @ApiResponse(code = 401 , message = "unauthorized" ),
            @ApiResponse(code = 404 , message = "equipment not found" )}
    )
    @PostMapping(path = "update")
    public ResponseEntity<?> updateEquipment(@RequestBody CreateEquipmentDto equipmentDto,@ApiIgnore Principal principal){
        var userOptional = userService.findByUsername(principal.getName());
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (userOptional.get().getRole() != Role.ADMIN || !equipmentService.hasAccess(equipmentDto.id, userOptional.get())){
            return ResponseEntity.status(401).build();
        }
        if (!equipmentService.existById(equipmentDto.id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(EquipmentMapper.INSTANCE.equipmentToDto(equipmentService.update(equipmentDto, userOptional.get())));
    }

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
    @GetMapping(path = "list")
    public ResponseEntity<?> getEquipment(@RequestParam int page,
                                          @RequestParam int pageSIze,
                                          @RequestParam(required = false) Optional<String> sortBy,
                                          @RequestParam(required = false) String filter,
                                          @ApiIgnore Principal principal) throws JsonProcessingException {
        List<FilterRequest> filterRequest = new ObjectMapper().readValue(filter, new TypeReference<List<FilterRequest>>() {});

        Pageable pageable = PageRequest.of(page - 1, pageSIze, Sort.by(sortBy.orElse("name")));
        var userOptional = userService.findByUsername(principal.getName());
        if (userOptional.isEmpty()) return ResponseEntity.notFound().build();
        if (userOptional.get().getRole() == Role.ADMIN){
            return ResponseEntity.ok(equipmentService.list(pageable, filterRequest));
        } else {
            return ResponseEntity.ok(equipmentService.list(userOptional.get(), pageable, filterRequest));
        }
    }
}