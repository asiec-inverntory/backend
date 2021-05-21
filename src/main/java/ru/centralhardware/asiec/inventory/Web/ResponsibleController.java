package ru.centralhardware.asiec.inventory.Web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.centralhardware.asiec.inventory.Service.UserService;
import ru.centralhardware.asiec.inventory.Web.Dto.EquipmentDto;

@RestController
@Api(value = "responsible")
@RequestMapping("/responsible")
public class ResponsibleController {

    public final UserService userService;

    public ResponsibleController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(
            value = "get  list of responsible",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "successfully get responsible", response = EquipmentDto.class, responseContainer = "List")
    })
    @GetMapping(path = "/list")
    public ResponseEntity<?> list(){
        return ResponseEntity.ok(userService.responsible());
    }

}
