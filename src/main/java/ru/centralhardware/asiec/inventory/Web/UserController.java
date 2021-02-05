package ru.centralhardware.asiec.inventory.Web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.centralhardware.asiec.inventory.Dto.Create.CreateUserDto;
import ru.centralhardware.asiec.inventory.Dto.UserDto;
import ru.centralhardware.asiec.inventory.Entity.Enum.Role;
import ru.centralhardware.asiec.inventory.Mapper.UserMapper;
import ru.centralhardware.asiec.inventory.Security.JwtTokenUtil;
import ru.centralhardware.asiec.inventory.Service.UserService;
import springfox.documentation.annotations.ApiIgnore;

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
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "successfully get user"),
            @ApiResponse(code = 204, message = "no content" )}
    )
    @GetMapping(path = "/me")
    public ResponseEntity<?> me(@ApiIgnore @CookieValue(name = "authorisation") String token){
        var userOptional = userService.findByUsername(jwtTokenUtil.getUsernameFromToken(token));
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
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successfully get user"),
            @ApiResponse(code = 404, message = "user not found")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getUser(@PathVariable Integer id){
        var userOptional = userService.findById(id);
        if (userOptional.isPresent()){
            return ResponseEntity.ok(UserMapper.INSTANCE.userToDto(userOptional.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(
            value = "create user",
            httpMethod = "PUT",
            response = UserDto.class,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successfully delete user"),
            @ApiResponse(code = 404, message = "user not found")
    })
    @PutMapping(path = "/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody  CreateUserDto userDto,
                                        @ApiIgnore @CookieValue(name = "authorisation") String token){
        var createdBy = userService.findByUsername(jwtTokenUtil.getUsernameFromToken(token));
        if (createdBy.isPresent()){
            return ResponseEntity.ok(UserMapper.INSTANCE.userToDto(userService.create(userDto, createdBy.get())));
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @ApiOperation(
            value = "update user",
            response = UserDto.class,
            httpMethod = "PUT",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successfully delete user"),
            @ApiResponse(code = 404, message = "user not found")
    })
    @PutMapping(path = "/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateUser(@RequestBody CreateUserDto userDto){
        if (!userService.existById(userDto.id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.update(userDto));
    }

    @ApiOperation(
            value = "delete user by id",
            httpMethod = "DELETE"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successfully delete user"),
            @ApiResponse(code = 401, message = "no permission"),
            @ApiResponse(code = 404, message = "user not found")
    })
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id,
                                        @ApiIgnore @CookieValue(name = "authorisation") String token) throws NotFoundException {
        var userOptional = userService.findByUsername(jwtTokenUtil.getUsernameFromToken(token));
        if (userOptional.isEmpty()) return ResponseEntity.notFound().build();
        if (!userOptional.get().getId().equals(id) || userOptional.get().getRole() != Role.ADMIN) return ResponseEntity.status(401).build();
        var userToDeleteOptional = userService.findById(id);
        if (userToDeleteOptional.isPresent()){
            userService.delete(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}