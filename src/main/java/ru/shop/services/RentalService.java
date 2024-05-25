package ru.shop.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.shop.exceptions.RentalNotFoundException;
import ru.shop.models.Book;
import ru.shop.models.Grade;
import ru.shop.models.Rental;
import ru.shop.repositories.BookRepo;
import ru.shop.repositories.RentalRepo;

import java.util.List;

@Service
@AllArgsConstructor
public class RentalService {
    private final RentalRepo rentalRepo;
    private final BookRepo bookRepo;

    public List<Rental> getAllRental(){
        return rentalRepo.findAll();
    }

    public List<Rental> getAllRentalByBookId(int book){
        return rentalRepo.findByBookId(book);
    }

    public List<Rental> getAllRentalByUserLibraryId(int userLibrary){
        return rentalRepo.findByUserLibraryId(userLibrary);
    }

    public Rental getByIdRental(int id){
        return rentalRepo.findById(id).orElseThrow(RentalNotFoundException::new);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public int deleteRental(int id){
        if (rentalRepo.findById(id).isPresent()) {
            Book book = rentalRepo.findById(id).get().getBook();
            book.setCountBook(book.getCountBook()+rentalRepo.findById(id).get().getCountBook());
            bookRepo.updateBook(book.getNameBook(),book.getDatePublication(),book.getCountBook(),book.getPrice(),book.getImageBook(),book.getGenre(),book.getAuthor(),book.getPublisher(), book.getId());
            rentalRepo.deleteById(id);
            return id;
        }
        return 0;
    }
}
