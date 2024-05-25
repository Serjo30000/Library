package ru.shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shop.models.Grade;
import ru.shop.models.Rental;
import ru.shop.services.RentalService;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*", methods = {RequestMethod.GET, RequestMethod.DELETE})
public class RentalController {
    private final RentalService rentalService;

    @GetMapping
    public ResponseEntity<List<Rental>> getAllRental(){
        return ResponseEntity.ok().body(rentalService.getAllRental());
    }

    @GetMapping("/book/{book}")
    public ResponseEntity<List<Rental>> getAllRentalByBookId(@PathVariable("book") int book){
        return ResponseEntity.ok().body(rentalService.getAllRentalByBookId(book));
    }

    @GetMapping("/userLibrary/{userLibrary}")
    public ResponseEntity<List<Rental>> getAllRentalByUserLibraryId(@PathVariable("userLibrary") int userLibrary){
        return ResponseEntity.ok().body(rentalService.getAllRentalByUserLibraryId(userLibrary));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rental> getByIdRental(@PathVariable("id") int id){
        return ResponseEntity.ok().body(rentalService.getByIdRental(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteRental(@PathVariable("id") int id){
        return ResponseEntity.ok().body(rentalService.deleteRental(id));
    }

}
