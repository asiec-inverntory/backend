package ru.centralhardware.asiec.inventory.Web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.centralhardware.asiec.inventory.Dto.UserDto;
import ru.centralhardware.asiec.inventory.Mapper.UserMapper;
import ru.centralhardware.asiec.inventory.Security.JwtTokenUtil;
import ru.centralhardware.asiec.inventory.Service.UserService;

@RestController
@Api(value = "user")
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    public UserController(UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
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
    public ResponseEntity<?> me(@RequestHeader(name = "Authorization") String authorization){
        var userOptional = userService.findByUsername(jwtTokenUtil.getUsernameFromToken(authorization));
        if (userOptional.isPresent()){
            return ResponseEntity.ok(UserMapper.INSTANCE.userToDto(userOptional.get()));
        } else {
            return ResponseEntity.noContent().build();
        }
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getUser(@RequestParam int id){
        var userOptional = userService.findById(id);
        if (userOptional.isPresent()){
            return ResponseEntity.ok(UserMapper.INSTANCE.userToDto(userOptional.get()));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping(path = "/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateUser(UserDto userDto, @RequestHeader(name = "Authorization") String authorization){
        var createdBy = userService.findByUsername(jwtTokenUtil.getUsernameFromToken(authorization));
        if (createdBy.isPresent()){
            return ResponseEntity.ok(userService.create(userDto, createdBy.get()));
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteUser(@RequestParam int id){
        var userOptional = userService.findById(id);
        if (userOptional.isPresent()){
            userService.delete(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}