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
import ru.centralhardware.asiec.inventory.Dto.UserDto;
import ru.centralhardware.asiec.inventory.Service.UserService;

@RestController
@Api(value = "user")
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(
            value = "get user of current session",
            response = UserDto.class,
            httpMethod = "GET",
            produces = MediaType.APPLICATION_NDJSON_VALUE
    )
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "successfully get user"),
            @ApiResponse(code = 401, message = "unauthorized" )}
    )
    @GetMapping(path = "/me")
    public ResponseEntity<?> me(){
        return ResponseEntity.ok().build();
    }

    @ApiOperation(
            value = "get user by id",
            response = UserDto.class,
            httpMethod = "GET",
            produces = MediaType.APPLICATION_NDJSON_VALUE
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successfully get user"),
            @ApiResponse(code = 401, message = "no permission")
    })
    @GetMapping(path = "/user")
    public ResponseEntity<?> user(){
        return ResponseEntity.ok().build();
    }

}