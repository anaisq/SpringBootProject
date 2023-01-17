package main.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import main.dto.UserDto;
import main.entity.RoleType;
import main.entity.StatusType;
import main.entity.User;
import main.mapper.UserMapper;
import main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Api(value = "/users",
    tags = "Users")
public class UserController {

    @Autowired
    private UserService userService;

//    @Autowired
//    private UserMapper userrService;

    @GetMapping
    @ApiOperation(value = "See all users")
    public ResponseEntity<List<UserDto>> getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{id}")
    public Optional<UserDto> getById(@PathVariable Long id){
        Optional<UserDto> user = userService.getById(id);
        return user;
    }

    @GetMapping("/filter")
    public List<UserDto> getByRoleAndStatus(@RequestParam RoleType role, @RequestParam(required = false)StatusType status){
        return userService.getUserByRoleAndStatus(role, status);
    }

    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.addUser(userDto));
        //return ResponseEntity.ok(userrService.mapToUserDto(user));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username){
        return ResponseEntity.ok(userService.deleteUser(username));
    }

    @PutMapping("/{id}/{status}")
    public ResponseEntity<UserDto> updateUsername(@PathVariable Long id, @PathVariable String status){
        return ResponseEntity.ok(userService.updateStatus(id, status));
    }


}
