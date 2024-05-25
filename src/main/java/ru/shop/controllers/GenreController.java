package ru.shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shop.models.Genre;
import ru.shop.services.GenreService;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*", methods = {RequestMethod.GET,  RequestMethod.PUT,  RequestMethod.DELETE})
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    public ResponseEntity<List<Genre>> getAllGenre(){
        return ResponseEntity.ok().body(genreService.getAllGenre());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getByIdGenre(@PathVariable("id") int id){
        return ResponseEntity.ok().body(genreService.getByIdGenre(id));
    }

    @PutMapping("/save")
    public ResponseEntity<Integer> saveGenre(@RequestBody Genre g){
        return ResponseEntity.ok().body(genreService.saveGenre(g));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteGenre(@PathVariable("id") int id){
        return ResponseEntity.ok().body(genreService.deleteGenre(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Integer> updateGenre(@PathVariable("id") int id, @RequestBody Genre g){
        return ResponseEntity.ok().body(genreService.updateGenre(id,g));
    }

}
