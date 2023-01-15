package main.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import main.dto.UserDto;
import main.entity.RoleType;
import main.entity.StatusType;
import main.entity.User;
import main.exception.UserAlreadyOnDbException;
import main.mapper.UserMapper;
import main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping
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
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username){
        return ResponseEntity.ok(userService.deleteUser(username));
    }

    @PutMapping("/{id}/{username}")
    public ResponseEntity<UserDto> updateUsername(@PathVariable Long id, @PathVariable String username){
        return ResponseEntity.ok(userService.updateUsername(id, username));
    }


}
