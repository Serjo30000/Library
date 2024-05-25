package ru.shop.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.shop.models.Book;
import ru.shop.models.Grade;
import ru.shop.models.Rental;
import ru.shop.repositories.*;
import ru.shop.exceptions.BookNotFoundException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepo bookRepo;
    private final RentalRepo rentalRepo;
    private final UserLibraryRepo userLibraryRepo;
    private final GradeRepo gradeRepo;
    private final GenreRepo genreRepo;
    private final AuthorRepo authorRepo;
    private final PublisherRepo publisherRepo;

    public List<Book> getAllBook(){
        return bookRepo.findAll();
    }
    public List<Book> getAllBookByGenreId(int genre){
        return bookRepo.findByGenreId(genre);
    }
    public List<Book> getAllBookByAuthorId(int author){
        return bookRepo.findByAuthorId(author);
    }
    public List<Book> getAllBookByPublisherId(int publisher){
        return bookRepo.findByPublisherId(publisher);
    }

    public Book getByIdBook(int id){
        return bookRepo.findById(id).orElseThrow(BookNotFoundException::new);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public int saveBook(Book b, int genreId, int authorId, int publisherId){
        if (b.getCountBook()<0 || b.getPrice()<0 || b.getNameBook().equals("default")){
            return 0;
        }
        for (Book book: bookRepo.findAll()){
            if (book.getNameBook().equals(b.getNameBook()) || book.getImageBook().equals(b.getImageBook())){
                return 0;
            }
        }
        b.setGenre(genreRepo.findById(genreId).get());
        b.setAuthor(authorRepo.findById(authorId).get());
        b.setPublisher(publisherRepo.findById(publisherId).get());
        return bookRepo.save(b).getId();
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public int deleteBook(int id){
        if (bookRepo.findById(id).isPresent()) {
            for (Rental rent: bookRepo.findById(id).get().getRentals()){
                rentalRepo.deleteById(rent.getId());
            }
            for (Grade gr: bookRepo.findById(id).get().getGrades()){
                gradeRepo.deleteById(gr.getId());
            }
            bookRepo.deleteById(id);
            return id;
        }
        return 0;
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public int updateBook(int id, Book book){
        if (bookRepo.findById(id).isPresent()) {
            if (book.getPrice()<0 || book.getCountBook()<0 || book.getNameBook().equals("default")){
                return 0;
            }
            for (Book b: bookRepo.findAll()){
                if ((b.getNameBook().equals(book.getNameBook()) || b.getImageBook().equals(book.getImageBook())) && b.getId()!=id){
                    return 0;
                }
            }
            bookRepo.updateBook(book.getNameBook(),book.getDatePublication(),book.getCountBook(),book.getPrice(),book.getImageBook(),bookRepo.findById(id).get().getGenre(),bookRepo.findById(id).get().getAuthor(),bookRepo.findById(id).get().getPublisher(), id);
            return id;
        }
        return 0;
    }

    @Transactional
    @PreAuthorize("hasRole('USER')")
    public int updateBookCountBook(int id, int countBook,String login){
        if (bookRepo.findById(id).isPresent()) {
            var book = bookRepo.findById(id).get();
            if (countBook<1 || countBook>book.getCountBook()){
                return 0;
            }
            bookRepo.updateBook(book.getNameBook(),book.getDatePublication(),(book.getCountBook()-countBook),book.getPrice(),book.getImageBook(),bookRepo.findById(id).get().getGenre(),bookRepo.findById(id).get().getAuthor(),bookRepo.findById(id).get().getPublisher(), id);
            Rental rental = new Rental();
            rental.setBook(bookRepo.findById(id).get());
            rental.setCountBook(countBook);
            rental.setDateStart(Date.valueOf(LocalDate.now()));
            rental.setDateEnd(Date.valueOf(LocalDate.now().plusMonths(1)));
            rental.setUserLibrary(userLibraryRepo.findByLogin(login).get());
            for (Rental rent: book.getRentals()){
                if (rent.getUserLibrary().equals(login)){
                    rentalRepo.updateRental((rent.getCountBook()+rental.getCountBook()),rent.getDateStart(),rental.getDateEnd(),rent.getBook(),rent.getUserLibrary(),rent.getId());
                    return id;
                }
            }
            rentalRepo.save(rental);
            return id;
        }
        return 0;
    }
}
