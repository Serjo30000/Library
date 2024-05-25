package ru.shop.integration;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import ru.shop.controllers.AuthController;
import ru.shop.dto.AuthenticationDto;
import ru.shop.models.*;
import ru.shop.repositories.*;
import ru.shop.services.UserLibraryService;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookIntegrationTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserLibraryService userLibraryService;
    @Autowired
    private AuthController authController;
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private RentalRepo rentalRepo;
    @Autowired
    private UserLibraryRepo userLibraryRepo;
    @Autowired
    private GradeRepo gradeRepo;
    @Autowired
    private GenreRepo genreRepo;
    @Autowired
    private AuthorRepo authorRepo;
    @Autowired
    private PublisherRepo publisherRepo;
    @Autowired
    private RoleRepo pRepository;

    @BeforeEach
    @Rollback(value = false)
    public void testGeneratedBook(){
        userLibraryService.createDefaultAdmin();
        UserLibrary userLibrary = userLibraryRepo.findByLogin("DefaultAdmin").get();

        Author author = new Author();
        author.setName("Name1");
        author.setSurname("Surname1");
        author.setPatronymic("Patronymic1");
        author.setDateBirth(new Date(2024,1,29));
        author.setBooks(new ArrayList<>());
        authorRepo.save(author);

        Publisher publisher = new Publisher();
        publisher.setNamePublisher("Publisher1");
        publisher.setAddress("Address1");
        publisher.setBooks(new ArrayList<>());
        publisherRepo.save(publisher);

        Genre genre = new Genre();
        genre.setNameGenre("Genre1");
        genre.setBooks(new ArrayList<>());
        genreRepo.save(genre);

        Book book1 = new Book();
        book1.setNameBook("Book1");
        book1.setDatePublication(new Date(24,0,29));
        book1.setCountBook(5);
        book1.setPrice(1000);
        book1.setImageBook("default1.png");
        book1.setAuthor(authorRepo.findAll().get(0));
        book1.setPublisher(publisherRepo.findAll().get(0));
        book1.setGenre(genreRepo.findAll().get(0));
        book1.setRentals(new ArrayList<>());
        book1.setGrades(new ArrayList<>());
        bookRepo.save(book1);
        Book book2 = new Book();
        book2.setNameBook("Book2");
        book2.setDatePublication(new Date(24,0,29));
        book2.setCountBook(4);
        book2.setPrice(800);
        book2.setImageBook("default2.png");
        book2.setAuthor(authorRepo.findAll().get(0));
        book2.setPublisher(publisherRepo.findAll().get(0));
        book2.setGenre(genreRepo.findAll().get(0));
        book2.setRentals(new ArrayList<>());
        book2.setGrades(new ArrayList<>());
        bookRepo.save(book2);
    }

    @AfterEach
    @Rollback(value = false)
    public void testAllDeletedBook(){
        for (Rental rnt: rentalRepo.findAll()){
            rentalRepo.deleteById(rnt.getId());
        }
        for (Book bk: bookRepo.findAll()){
            bookRepo.deleteById(bk.getId());
        }
        for (UserLibrary ur: userLibraryRepo.findAll()){
            userLibraryRepo.deleteById(ur.getId());
        }
        for (Genre gn: genreRepo.findAll()){
            genreRepo.deleteById(gn.getId());
        }
        for (Publisher pb: publisherRepo.findAll()){
            publisherRepo.deleteById(pb.getId());
        }
        for (Author au: authorRepo.findAll()){
            authorRepo.deleteById(au.getId());
        }
        for (Role rl: pRepository.findAll()){
            pRepository.deleteById(rl.getId());
        }
    }

    @Test
    @Order(0)
    public void testBookById() {
        int bookId = bookRepo.findAll().get(0).getId();

        ResponseEntity<Book> response = restTemplate.exchange("http://localhost:" + port + "/api/books/" + bookId, HttpMethod.GET, HttpEntity.EMPTY, Book.class);

        assertNotNull(response.getBody());
    }

    @Test
    @Order(1)
    public void testBookByListGenreId() {
        Genre genre = bookRepo.findAll().get(0).getGenre();

        ResponseEntity<List<Book>> response = restTemplate.exchange("http://localhost:" + port + "/api/books/genre/" + genre.getId(), HttpMethod.GET,HttpEntity.EMPTY,  new ParameterizedTypeReference<List<Book>>() {});

        assertThat(response.getBody()).size().isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void testBookByListAuthorId() {
        Author author = bookRepo.findAll().get(0).getAuthor();

        ResponseEntity<List<Book>> response = restTemplate.exchange("http://localhost:" + port + "/api/books/author/" + author.getId(), HttpMethod.GET,HttpEntity.EMPTY,  new ParameterizedTypeReference<List<Book>>() {});

        assertThat(response.getBody()).size().isGreaterThan(0);
    }

    @Test
    @Order(3)
    public void testBookByListPublisherId() {
        Publisher publisher = bookRepo.findAll().get(0).getPublisher();

        ResponseEntity<List<Book>> response = restTemplate.exchange("http://localhost:" + port + "/api/books/publisher/" + publisher.getId(), HttpMethod.GET,HttpEntity.EMPTY,  new ParameterizedTypeReference<List<Book>>() {});

        assertThat(response.getBody()).size().isGreaterThan(0);
    }

    @Test
    @Order(4)
    public void testBookByList() {
        ResponseEntity<List<Book>> response = restTemplate.exchange("http://localhost:" + port + "/api/books", HttpMethod.GET,HttpEntity.EMPTY,  new ParameterizedTypeReference<List<Book>>() {});

        assertThat(response.getBody()).size().isGreaterThan(0);
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void testBookCreate() {
        Book savedBook = new Book();
        savedBook.setNameBook("NameBook3");
        savedBook.setDatePublication(new Date(24,0,31));
        savedBook.setCountBook(5);
        savedBook.setPrice(1000);
        savedBook.setImageBook("default3.png");
        int genreId = genreRepo.findAll().get(0).getId();
        if (genreRepo.findById(genreId).isPresent()) {
            savedBook.setGenre(genreRepo.findById(genreId).get());
        }
        int authorId = authorRepo.findAll().get(0).getId();
        if (authorRepo.findById(authorId).isPresent()) {
            savedBook.setAuthor(authorRepo.findById(authorId).get());
        }
        int publisherId = publisherRepo.findAll().get(0).getId();
        if (publisherRepo.findById(publisherId).isPresent()) {
            savedBook.setPublisher(publisherRepo.findById(publisherId).get());
        }
        UserLibrary userLibrary = userLibraryRepo.findByLogin("DefaultAdmin").get();
        HttpHeaders headers = new HttpHeaders();
        AuthenticationDto authenticationDto = new AuthenticationDto(userLibrary.getLogin(),"1");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nameBook",savedBook.getNameBook());
        jsonObject.put("datePublication",savedBook.getDatePublication());
        jsonObject.put("countBook",savedBook.getCountBook());
        jsonObject.put("price",savedBook.getPrice());
        jsonObject.put("imageBook",savedBook.getImageBook());
        headers.set("Authorization", "Bearer " + authController.logIn(authenticationDto).getBody().getToken());
        headers.set("Content-type", "application/json; charset=UTF-8");
        HttpEntity<Object> entity = new HttpEntity<>(jsonObject,headers);

        ResponseEntity<Integer> response = restTemplate.exchange("http://localhost:" + port + "/api/books/save?genreId=" + savedBook.getGenre().getId() + "&authorId=" + savedBook.getAuthor().getId() + "&publisherId=" + savedBook.getPublisher().getId(), HttpMethod.PUT, entity, Integer.class);

        List <Book> bookList = bookRepo.findAll();
        assertNotNull(bookRepo.findById(bookList.get(bookList.size() - 1).getId()).get());
        assertThat(bookList.get(bookList.size()-1).getNameBook()).isEqualTo("NameBook3");
        assertThat(bookList.get(bookList.size()-1).getDatePublication()).isEqualTo(new Date(24,0,31));
        assertThat(bookList.get(bookList.size()-1).getCountBook()).isEqualTo(5);
        assertThat(bookList.get(bookList.size()-1).getPrice()).isEqualTo(1000);
        assertThat(bookList.get(bookList.size()-1).getImageBook()).isEqualTo("default3.png");
    }

    @Test
    @Order(6)
    @Rollback(value = false)
    public void testBookDeleteById(){
        int bookId = bookRepo.findAll().get(0).getId();
        UserLibrary userLibrary = userLibraryRepo.findByLogin("DefaultAdmin").get();
        HttpHeaders headers = new HttpHeaders();
        AuthenticationDto authenticationDto = new AuthenticationDto(userLibrary.getLogin(),"1");
        headers.set("Authorization", "Bearer " + authController.logIn(authenticationDto).getBody().getToken());
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        restTemplate.exchange("http://localhost:" + port + "/api/books/" + bookId, HttpMethod.DELETE, entity, Integer.class);

        assertFalse(bookRepo.findById(bookId).isPresent());
    }

    @Test
    @Order(7)
    @Rollback(value = false)
    public void testBookUpdateById() {
        Book book = bookRepo.findAll().get(0);
        if (bookRepo.findById(book.getId()).isPresent()) {
            book.setNameBook("NameBook3");
            book.setDatePublication(new Date(24,0,31));
            book.setCountBook(7);
            book.setPrice(2000);
            book.setImageBook("default3.png");
        }
        UserLibrary userLibrary = userLibraryRepo.findByLogin("DefaultAdmin").get();
        HttpHeaders headers = new HttpHeaders();
        AuthenticationDto authenticationDto = new AuthenticationDto(userLibrary.getLogin(),"1");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nameBook",book.getNameBook());
        jsonObject.put("datePublication",book.getDatePublication());
        jsonObject.put("countBook",book.getCountBook());
        jsonObject.put("price",book.getPrice());
        jsonObject.put("imageBook",book.getImageBook());
        headers.set("Authorization", "Bearer " + authController.logIn(authenticationDto).getBody().getToken());
        headers.set("Content-type", "application/json; charset=UTF-8");
        HttpEntity<Object> entity = new HttpEntity<>(jsonObject,headers);

        ResponseEntity<Integer> response = restTemplate.exchange("http://localhost:" + port + "/api/books/update/" + book.getId(), HttpMethod.PUT, entity, Integer.class);

        Book updatedBook = bookRepo.findById(book.getId()).get();
        assertThat(updatedBook.getNameBook()).isEqualTo("NameBook3");
        assertThat(updatedBook.getDatePublication()).isEqualTo(new Date(24,0,31));
        assertThat(updatedBook.getCountBook()).isEqualTo(7);
        assertThat(updatedBook.getPrice()).isEqualTo(2000);
        assertThat(updatedBook.getImageBook()).isEqualTo("default3.png");
    }

    @Test
    @Order(8)
    @Rollback(value = false)
    public void testBookUpdateCountBookById() {
        Book book = bookRepo.findAll().get(0);
        int countBook = 3;
        String login = "DefaultAdmin";
        Role roleUser = new Role("ROLE_USER");
        pRepository.save(roleUser);
        UserLibrary userLibrary = userLibraryRepo.findByLogin("DefaultAdmin").get();
        userLibrary.setRole(pRepository.findByNameRole("ROLE_USER").get());
        userLibraryRepo.save(userLibrary);
        userLibrary = userLibraryRepo.findByLogin("DefaultAdmin").get();
        HttpHeaders headers = new HttpHeaders();
        AuthenticationDto authenticationDto = new AuthenticationDto(userLibrary.getLogin(),"1");
        headers.set("Authorization", "Bearer " + authController.logIn(authenticationDto).getBody().getToken());
        headers.set("Content-type", "application/json; charset=UTF-8");
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        ResponseEntity<Integer> response = restTemplate.exchange("http://localhost:" + port + "/api/books/updateCount/" + book.getId() + "?countBook=" + countBook + "&login=" + login, HttpMethod.PUT, entity, Integer.class);

        assertTrue(true);
    }
}
