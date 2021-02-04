package ru.centralhardware.asiec.inventory.Web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import ru.centralhardware.asiec.inventory.Dto.CreateEquipmentDto;
import ru.centralhardware.asiec.inventory.Entity.Enum.Role;
import ru.centralhardware.asiec.inventory.Mapper.EquipmentMapper;
import ru.centralhardware.asiec.inventory.Security.JwtTokenUtil;
import ru.centralhardware.asiec.inventory.Service.EquipmentService;
import ru.centralhardware.asiec.inventory.Service.UserService;

public class EquipmentController {

    private final EquipmentService equipmentService;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    public EquipmentController(EquipmentService equipmentService, UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.equipmentService = equipmentService;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public ResponseEntity<?> createEquipment(@RequestBody CreateEquipmentDto equipmentDto,
                                             @RequestHeader(name = "Authorisation") String authorisation){
        var userOptional = userService.findByUsername(jwtTokenUtil.getUsernameFromToken(authorisation));
        if (userOptional.isPresent()){
            return ResponseEntity.ok(EquipmentMapper.INSTANCE.equipmentToDto(equipmentService.create(equipmentDto,userOptional.get())));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    public ResponseEntity<?> updateEquipment(@RequestBody CreateEquipmentDto equipmentDto,
                                             @RequestHeader(name = "Authorisation") String authorisation){
        var userOptional = userService.findByUsername(jwtTokenUtil.getUsernameFromToken(authorisation));
        if (!userOptional.isPresent()) {
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

    public ResponseEntity<?> deleteEquipment(@RequestParam int id,
                                             @RequestHeader(name = "Authorisation") String authorisation){
        var userOptional = userService.findByUsername(jwtTokenUtil.getUsernameFromToken(authorisation));
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

//    public ResponseEntity<?> getEquipment(){
//
//    }
}
