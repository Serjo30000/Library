package ru.shop.integration;

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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RentalIntegrationTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserLibraryService userLibraryService;
    @Autowired
    private AuthController authController;
    @Autowired
    private RentalRepo rentalRepo;
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private UserLibraryRepo userLibraryRepo;
    @Autowired
    private AuthorRepo authorRepo;
    @Autowired
    private PublisherRepo publisherRepo;
    @Autowired
    private GenreRepo genreRepo;
    @Autowired
    private RoleRepo pRepository;

    @BeforeEach
    @Rollback(value = false)
    public void testGeneratedRental(){
        userLibraryService.createDefaultAdmin();
        UserLibrary userLibrary = userLibraryRepo.findByLogin("DefaultAdmin").get();

        Author author = new Author();
        author.setName("Name1");
        author.setSurname("Surname1");
        author.setPatronymic("Patronymic1");
        author.setDateBirth(new Date(24,1,29));
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

        Book book = new Book();
        book.setNameBook("Book1");
        book.setDatePublication(new Date(24,1,29));
        book.setCountBook(5);
        book.setPrice(1000);
        book.setImageBook("default.png");
        book.setAuthor(authorRepo.findAll().get(0));
        book.setPublisher(publisherRepo.findAll().get(0));
        book.setGenre(genreRepo.findAll().get(0));
        bookRepo.save(book);

        Rental rental1 = new Rental();
        rental1.setCountBook(1);
        rental1.setDateStart(new Date(24,1,29));
        rental1.setDateEnd(new Date(24,2,29));
        rental1.setUserLibrary(userLibrary);
        rental1.setBook(bookRepo.findAll().get(0));
        rentalRepo.save(rental1);
        Rental rental2 = new Rental();
        rental2.setCountBook(3);
        rental2.setDateStart(new Date(24,1,29));
        rental2.setDateEnd(new Date(24,2,29));
        rental2.setUserLibrary(userLibrary);
        rental2.setBook(bookRepo.findAll().get(0));
        rentalRepo.save(rental2);
    }

    @AfterEach
    @Rollback(value = false)
    public void testAllDeletedRental(){
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
    public void testRentalById() {
        int rentalId = rentalRepo.findAll().get(0).getId();

        ResponseEntity<Rental> response = restTemplate.exchange("http://localhost:" + port + "/api/rentals/" + rentalId, HttpMethod.GET, HttpEntity.EMPTY, Rental.class);

        Rental rental = response.getBody();
        assertNotNull(rental);
    }

    @Test
    @Order(1)
    public void testRentalByUserListLibraryId() {
        UserLibrary userLibrary = rentalRepo.findAll().get(0).getUserLibrary();

        ResponseEntity<List<Rental>> response = restTemplate.exchange("http://localhost:" + port + "/api/rentals/userLibrary/" + userLibrary.getId(), HttpMethod.GET,HttpEntity.EMPTY,  new ParameterizedTypeReference<List<Rental>>() {});

        assertThat(response.getBody()).size().isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void testRentalByListBookId() {
        Book book = rentalRepo.findAll().get(0).getBook();

        ResponseEntity<List<Rental>> response = restTemplate.exchange("http://localhost:" + port + "/api/rentals/book/" + book.getId(), HttpMethod.GET,HttpEntity.EMPTY,  new ParameterizedTypeReference<List<Rental>>() {});

        assertThat(response.getBody()).size().isGreaterThan(0);
    }

    @Test
    @Order(3)
    public void testRentalByList() {
        ResponseEntity<List<Rental>> response = restTemplate.exchange("http://localhost:" + port + "/api/rentals", HttpMethod.GET,HttpEntity.EMPTY,  new ParameterizedTypeReference<List<Rental>>() {});

        assertThat(rentalRepo.findAll()).size().isGreaterThan(0);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void testRentalDeleteById(){
        int rentalId = rentalRepo.findAll().get(0).getId();
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

        restTemplate.exchange("http://localhost:" + port + "/api/rentals/" + rentalId, HttpMethod.DELETE, entity, Integer.class);

        assertFalse(rentalRepo.findById(rentalId).isPresent());
    }
}
