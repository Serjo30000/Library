package ru.shop.unit;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import ru.shop.models.*;
import ru.shop.repositories.BookRepo;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookRepoUnitTest {
    @InjectMocks
    private BookRepo bookRepo = mock(BookRepo.class);

    @Test
    @Order(0)
    public void testFindById(){
        Optional<Book> book = Optional.of(new Book());
        book.get().setId(1);
        book.get().setNameBook("NameBook1");
        book.get().setDatePublication(new Date(24,0,31));
        book.get().setCountBook(5);
        book.get().setPrice(1000);
        book.get().setImageBook("default1.png");
        book.get().setAuthor(new Author());
        book.get().setGenre(new Genre());
        book.get().setPublisher(new Publisher());
        book.get().setGrades(new ArrayList<>());
        book.get().setRentals(new ArrayList<>());

        when(bookRepo.findById(1)).thenReturn(book);
        Optional<Book> resultBook = bookRepo.findById(1);

        assertEquals(book.get().getId(), resultBook.get().getId());
        verify(bookRepo, times(1)).findById(1);
    }

    @Test
    @Order(1)
    public void testFindAll(){
        Book book = new Book();
        book.setId(1);
        book.setNameBook("NameBook1");
        book.setDatePublication(new Date(24,0,31));
        book.setCountBook(5);
        book.setPrice(1000);
        book.setImageBook("default1.png");
        book.setAuthor(new Author());
        book.setGenre(new Genre());
        book.setPublisher(new Publisher());
        book.setGrades(new ArrayList<>());
        book.setRentals(new ArrayList<>());
        List<Book> bookList = Arrays.asList(book);

        when(bookRepo.findAll()).thenReturn(Arrays.asList(book));
        List<Book> resultBookList = bookRepo.findAll();

        assertEquals(bookList.size(), resultBookList.size());
        verify(bookRepo, times(1)).findAll();
    }

    @Test
    @Order(2)
    public void testDeleteById(){
        int id = 1;

        bookRepo.deleteById(1);

        verify(bookRepo, times(1)).deleteById(id);
    }

    @Test
    @Order(3)
    public void testSave(){
        Book book = new Book();
        book.setNameBook("NameBook1");
        book.setDatePublication(new Date(24,0,31));
        book.setCountBook(5);
        book.setPrice(1000);
        book.setImageBook("default1.png");
        book.setAuthor(new Author());
        book.setGenre(new Genre());
        book.setPublisher(new Publisher());
        book.setGrades(new ArrayList<>());
        book.setRentals(new ArrayList<>());

        when(bookRepo.save(book)).thenReturn(book);
        Book savedBook = bookRepo.save(book);

        assertEquals(book.getNameBook(), savedBook.getNameBook());
        assertEquals(book.getDatePublication(), savedBook.getDatePublication());
        assertEquals(book.getCountBook(), savedBook.getCountBook());
        assertEquals(book.getPrice(), savedBook.getPrice());
        assertEquals(book.getImageBook(), savedBook.getImageBook());
        verify(bookRepo, times(1)).save(book);
    }

    @Test
    @Order(4)
    public void testFindByGenreId(){
        Optional<Book> book = Optional.of(new Book());
        book.get().setId(1);
        book.get().setNameBook("NameBook1");
        book.get().setDatePublication(new Date(24,0,31));
        book.get().setCountBook(5);
        book.get().setPrice(1000);
        book.get().setImageBook("default1.png");
        int genreId = 1;
        Genre genre = new Genre();
        genre.setId(genreId);
        genre.setNameGenre("NameGenre1");
        genre.setBooks(Arrays.asList(book.get()));
        book.get().setAuthor(new Author());
        book.get().setGenre(genre);
        book.get().setPublisher(new Publisher());
        book.get().setGrades(new ArrayList<>());
        book.get().setRentals(new ArrayList<>());

        when(bookRepo.findByGenreId(genreId)).thenReturn(genre.getBooks());
        List<Book> resultGenreList = bookRepo.findByGenreId(genreId);

        assertEquals(genre.getBooks().size(), resultGenreList.size());
        verify(bookRepo, times(1)).findByGenreId(genreId);
    }

    @Test
    @Order(5)
    public void testFindByAuthorId(){
        Optional<Book> book = Optional.of(new Book());
        book.get().setId(1);
        book.get().setNameBook("NameBook1");
        book.get().setDatePublication(new Date(24,0,31));
        book.get().setCountBook(5);
        book.get().setPrice(1000);
        book.get().setImageBook("default1.png");
        int authorId = 1;
        Author author = new Author();
        author.setId(authorId);
        author.setName("Name1");
        author.setSurname("Surname1");
        author.setPatronymic("Patronymic1");
        author.setDateBirth(new Date(24,0,31));
        author.setBooks(Arrays.asList(book.get()));
        book.get().setAuthor(author);
        book.get().setGenre(new Genre());
        book.get().setPublisher(new Publisher());
        book.get().setGrades(new ArrayList<>());
        book.get().setRentals(new ArrayList<>());

        when(bookRepo.findByAuthorId(authorId)).thenReturn(author.getBooks());
        List<Book> resultAuthorList = bookRepo.findByAuthorId(authorId);

        assertEquals(author.getBooks().size(), resultAuthorList.size());
        verify(bookRepo, times(1)).findByAuthorId(authorId);
    }

    @Test
    @Order(6)
    public void testFindByPublisherId(){
        Optional<Book> book = Optional.of(new Book());
        book.get().setId(1);
        book.get().setNameBook("NameBook1");
        book.get().setDatePublication(new Date(24,0,31));
        book.get().setCountBook(5);
        book.get().setPrice(1000);
        book.get().setImageBook("default1.png");
        int publisherId = 1;
        Publisher publisher = new Publisher();
        publisher.setId(publisherId);
        publisher.setNamePublisher("NamePublisher1");
        publisher.setAddress("Address1");
        publisher.setBooks(Arrays.asList(book.get()));
        book.get().setAuthor(new Author());
        book.get().setGenre(new Genre());
        book.get().setPublisher(publisher);
        book.get().setGrades(new ArrayList<>());
        book.get().setRentals(new ArrayList<>());

        when(bookRepo.findByPublisherId(publisherId)).thenReturn(publisher.getBooks());
        List<Book> resultPublisherList = bookRepo.findByPublisherId(publisherId);

        assertEquals(publisher.getBooks().size(), resultPublisherList.size());
        verify(bookRepo, times(1)).findByPublisherId(publisherId);
    }

    @Test
    @Order(7)
    public void testUpdate(){
        Book book = new Book();
        int id = 1;
        book.setId(id);
        book.setNameBook("NameBook1");
        book.setDatePublication(new Date(24,0,31));
        book.setCountBook(5);
        book.setPrice(1000);
        book.setImageBook("default1.png");
        book.setAuthor(new Author());
        book.setGenre(new Genre());
        book.setPublisher(new Publisher());
        book.setGrades(new ArrayList<>());
        book.setRentals(new ArrayList<>());

        bookRepo.updateBook(book.getNameBook(), book.getDatePublication(), book.getCountBook(), book.getPrice(), book.getImageBook(), book.getGenre(), book.getAuthor(), book.getPublisher(), id);
        when(bookRepo.findById(1)).thenReturn(Optional.of(book));
        Book updatedBook = bookRepo.findById(1).get();

        assertEquals(book.getId(), updatedBook.getId());
        assertEquals(book.getNameBook(), updatedBook.getNameBook());
        assertEquals(book.getDatePublication(), updatedBook.getDatePublication());
        assertEquals(book.getCountBook(), updatedBook.getCountBook());
        assertEquals(book.getPrice(), updatedBook.getPrice());
        assertEquals(book.getImageBook(), updatedBook.getImageBook());
        verify(bookRepo, times(1)).updateBook(book.getNameBook(), book.getDatePublication(), book.getCountBook(), book.getPrice(), book.getImageBook(), book.getGenre(), book.getAuthor(), book.getPublisher(), id);
    }
}
