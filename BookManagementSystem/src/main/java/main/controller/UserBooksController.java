package main.controller;

import main.entity.UserBooks;
import main.service.UserBooksServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/books")
public class UserBooksController {

    @Autowired
    private UserBooksServiceImpl userBooksServiceImpl;

    @PostMapping
    public ResponseEntity<UserBooks> add(@RequestBody UserBooks userBooks){
        return ResponseEntity.ok(userBooksServiceImpl.add(userBooks));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<UserBooks>> getById(@PathVariable Long id){
        return ResponseEntity.ok(userBooksServiceImpl.getById(id));
    }

    @PutMapping
    public ResponseEntity<UserBooks> updateUsername(@PathVariable Long idS, @PathVariable Long idB){
        return ResponseEntity.ok(userBooksServiceImpl.updateDelay(idS, idB));
    }


}
