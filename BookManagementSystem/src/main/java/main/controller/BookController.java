package main.controller;

import main.dto.BookDto;
import main.entity.RoleType;
import main.entity.StatusType;
import main.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;


    @GetMapping
    public ResponseEntity<List<BookDto>> getBooks(){
        return ResponseEntity.ok(bookService.getBooks());
    }

    @GetMapping("/{title}")
    public Optional<BookDto> getById(@PathVariable String title){
        Optional<BookDto> book = bookService.getBookByTitle(title);
        return book;
    }

//    @GetMapping("/filter")
//    public List<BookDto> getByRoleAndStatus(@RequestParam RoleType role, @RequestParam(required = false) StatusType status){
//        return bookService.getBookByRoleAndStatus(role, status);
//    }

    @PostMapping
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto){
        return ResponseEntity.ok(bookService.addBook(bookDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id){
        return ResponseEntity.ok(bookService.deleteBook(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBookname(@PathVariable Long id, @RequestBody String incrementDecrement){
        if(incrementDecrement.equals("+1"))
            return ResponseEntity.ok(bookService.incrementNumberOfAvailableCopiesByOne(id));
        if(incrementDecrement.equals("-1"))
            return ResponseEntity.ok(bookService.decrementNumberOfAvailableCopiesByOne(id));
        else return ResponseEntity.ok(bookService.getById(id).get());
    }
}
