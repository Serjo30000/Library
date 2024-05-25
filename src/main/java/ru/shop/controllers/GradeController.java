package ru.shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shop.models.Book;
import ru.shop.models.Grade;
import ru.shop.services.GradeService;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*", methods = {RequestMethod.GET,  RequestMethod.PUT,  RequestMethod.DELETE})
public class GradeController {
    private final GradeService gradeService;

    @GetMapping
    public ResponseEntity<List<Grade>> getAllGrade(){
        return ResponseEntity.ok().body(gradeService.getAllGrade());
    }

    @GetMapping("/book/{book}")
    public ResponseEntity<List<Grade>> getAllGradeByBookId(@PathVariable("book") int book){
        return ResponseEntity.ok().body(gradeService.getAllGradeByBookId(book));
    }

    @GetMapping("/userLibrary/{userLibrary}")
    public ResponseEntity<List<Grade>> getAllGradeByUserLibraryId(@PathVariable("userLibrary") int userLibrary){
        return ResponseEntity.ok().body(gradeService.getAllGradeByUserLibraryId(userLibrary));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Grade> getByIdGrade(@PathVariable("id") int id){
        return ResponseEntity.ok().body(gradeService.getByIdGrade(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteGrade(@PathVariable("id") int id){
        return ResponseEntity.ok().body(gradeService.deleteGrade(id));
    }

    @PutMapping("/save")
    public ResponseEntity<Integer> saveGrade(@RequestBody Grade g,@RequestParam("bookId") int bookId, @RequestParam("login") String login){
        return ResponseEntity.ok().body(gradeService.saveGrade(g, bookId, login));
    }
}
