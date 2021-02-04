package ru.centralhardware.asiec.inventory.Web;

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
import ru.centralhardware.asiec.inventory.Dto.CreateEquipmentDto;
import ru.centralhardware.asiec.inventory.Dto.EquipmentDto;
import ru.centralhardware.asiec.inventory.Entity.Enum.Role;
import ru.centralhardware.asiec.inventory.Mapper.EquipmentMapper;
import ru.centralhardware.asiec.inventory.Security.JwtTokenUtil;
import ru.centralhardware.asiec.inventory.Service.EquipmentService;
import ru.centralhardware.asiec.inventory.Service.UserService;

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
            response = EquipmentDto.class,
            httpMethod = "POST",
            produces = MediaType.APPLICATION_NDJSON_VALUE
    )
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "successfully get user"),
            @ApiResponse(code = 401 , message = "unauthorized" ),
            @ApiResponse(code = 404 , message = "not found" )}
    )
    @PostMapping(path = "create")
    public ResponseEntity<?> createEquipment(@RequestBody CreateEquipmentDto equipmentDto,
                                             @CookieValue(name = "authorisation") String token){
        var userOptional = userService.findByUsername(jwtTokenUtil.getUsernameFromToken(token));
        if (userOptional.isPresent()){
            return ResponseEntity.ok(EquipmentMapper.INSTANCE.equipmentToDto(equipmentService.create(equipmentDto,userOptional.get())));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @ApiOperation(
            value = "update equipment",
            response = EquipmentDto.class,
            httpMethod = "POST",
            produces = MediaType.APPLICATION_NDJSON_VALUE
    )
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "successfully get user"),
            @ApiResponse(code = 401 , message = "unauthorized" ),
            @ApiResponse(code = 404 , message = "unauthorized" )}
    )
    @PostMapping(path = "update")
    public ResponseEntity<?> updateEquipment(@RequestBody CreateEquipmentDto equipmentDto,
                                             @CookieValue(name = "authorisation") String token){
        var userOptional = userService.findByUsername(jwtTokenUtil.getUsernameFromToken(token));
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
            produces = MediaType.APPLICATION_NDJSON_VALUE
    )
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "successfully get user"),
            @ApiResponse(code = 401 , message = "unauthorized" ),
            @ApiResponse(code = 404 , message = "unauthorized" )}
    )
    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> deleteEquipment(@RequestParam int id,
                                             @CookieValue(name = "authorisation") String token){
        var userOptional = userService.findByUsername(jwtTokenUtil.getUsernameFromToken(token));
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

    @ApiOperation(
            value = "get pageable list of equipment",
            response = EquipmentDto.class,
            responseContainer = "List",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_NDJSON_VALUE
    )
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "successfully get user")
    })
    @GetMapping(path = "list")
    public ResponseEntity<?> getEquipment(@RequestParam int page,
                                          @RequestParam int size,
                                          @RequestParam String sortBy,
                                          @CookieValue(name = "authorisation") String token){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        var userOptional = userService.findByUsername(jwtTokenUtil.getUsernameFromToken(token));
        if (userOptional.isEmpty()) return ResponseEntity.notFound().build();
        if (userOptional.get().getRole() == Role.ADMIN){
            return ResponseEntity.ok(equipmentService.list(pageable));
        } else {
            return ResponseEntity.ok(equipmentService.list(userOptional.get(), pageable));
        }
    }
}