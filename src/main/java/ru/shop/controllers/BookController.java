package ru.shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shop.models.Book;
import ru.shop.services.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*", methods = {RequestMethod.GET,  RequestMethod.PUT,  RequestMethod.DELETE})
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBook(){
        return ResponseEntity.ok().body(bookService.getAllBook());
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<Book>> getAllBookByGenreId(@PathVariable("genre") int genre){
        return ResponseEntity.ok().body(bookService.getAllBookByGenreId(genre));
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<Book>> getAllBookByAuthorId(@PathVariable("author") int author){
        return ResponseEntity.ok().body(bookService.getAllBookByAuthorId(author));
    }

    @GetMapping("/publisher/{publisher}")
    public ResponseEntity<List<Book>> getAllBookByPublisherId(@PathVariable("publisher") int publisher){
        return ResponseEntity.ok().body(bookService.getAllBookByPublisherId(publisher));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getByIdBook(@PathVariable("id") int id){
        return ResponseEntity.ok().body(bookService.getByIdBook(id));
    }

    @PutMapping("/save")
    public ResponseEntity<Integer> saveBook(@RequestBody Book b, @RequestParam("genreId") int genreId, @RequestParam("authorId") int authorId, @RequestParam("publisherId") int publisherId){
        return ResponseEntity.ok().body(bookService.saveBook(b,genreId,authorId,publisherId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteBook(@PathVariable("id") int id){
        return ResponseEntity.ok().body(bookService.deleteBook(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Integer> updateBook(@PathVariable("id") int id, @RequestBody Book b){
        return ResponseEntity.ok().body(bookService.updateBook(id,b));
    }

    @PutMapping("/updateCount/{id}")
    public ResponseEntity<Integer> updateBookCountBook(@PathVariable("id") int id, @RequestParam("countBook") int countBook, @RequestParam("login") String login){
        return ResponseEntity.ok().body(bookService.updateBookCountBook(id,countBook,login));
    }
}
