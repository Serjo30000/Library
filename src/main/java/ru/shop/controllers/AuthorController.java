package ru.shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shop.models.Author;
import ru.shop.models.Book;
import ru.shop.services.AuthorService;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*", methods = {RequestMethod.GET,  RequestMethod.PUT,  RequestMethod.DELETE})
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthor(){
        return ResponseEntity.ok().body(authorService.getAllAuthor());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getByIdAuthor(@PathVariable("id") int id){
        return ResponseEntity.ok().body(authorService.getByIdAuthor(id));
    }

    @PutMapping("/save")
    public ResponseEntity<Integer> saveAuthor(@RequestBody Author a){
        return ResponseEntity.ok().body(authorService.saveAuthor(a));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteAuthor(@PathVariable("id") int id){
        return ResponseEntity.ok().body(authorService.deleteAuthor(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Integer> updateAuthor(@PathVariable("id") int id, @RequestBody Author a){
        return ResponseEntity.ok().body(authorService.updateAuthor(id,a));
    }

}
