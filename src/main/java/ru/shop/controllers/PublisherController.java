package ru.shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shop.models.Publisher;
import ru.shop.services.PublisherService;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*", methods = {RequestMethod.GET,  RequestMethod.PUT,  RequestMethod.DELETE})
public class PublisherController {
    private final PublisherService publisherService;

    @GetMapping
    public ResponseEntity<List<Publisher>> getAllPublisher(){
        return ResponseEntity.ok().body(publisherService.getAllPublisher());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getByIdPublisher(@PathVariable("id") int id){
        return ResponseEntity.ok().body(publisherService.getByIdPublisher(id));
    }

    @PutMapping("/save")
    public ResponseEntity<Integer> savePublisher(@RequestBody Publisher p){
        return ResponseEntity.ok().body(publisherService.savePublisher(p));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deletePublisher(@PathVariable("id") int id){
        return ResponseEntity.ok().body(publisherService.deletePublisher(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Integer> updatePublisher(@PathVariable("id") int id, @RequestBody Publisher p){
        return ResponseEntity.ok().body(publisherService.updatePublisher(id,p));
    }

}
