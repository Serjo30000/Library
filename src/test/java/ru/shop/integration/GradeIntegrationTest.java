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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GradeIntegrationTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserLibraryRepo userLibraryRepo;
    @Autowired
    private UserLibraryService userLibraryService;
    @Autowired
    private AuthController authController;
    @Autowired
    private GradeRepo gradeRepo;
    @Autowired
    private BookRepo bookRepo;
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
    public void testGeneratedGrade(){
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

        Book book = new Book();
        book.setNameBook("Book1");
        book.setDatePublication(new Date(2024,1,29));
        book.setCountBook(5);
        book.setPrice(1000);
        book.setImageBook("default.png");
        book.setAuthor(authorRepo.findAll().get(0));
        book.setPublisher(publisherRepo.findAll().get(0));
        book.setGenre(genreRepo.findAll().get(0));
        bookRepo.save(book);

        Grade grade1 = new Grade();
        grade1.setRating(5);
        grade1.setComment("Comment1");
        grade1.setUserLibrary(userLibrary);
        grade1.setBook(bookRepo.findAll().get(0));
        gradeRepo.save(grade1);
        Grade grade2 = new Grade();
        grade2.setRating(4);
        grade2.setComment("Comment2");
        grade2.setUserLibrary(userLibrary);
        grade2.setBook(bookRepo.findAll().get(0));
        gradeRepo.save(grade2);
    }

    @AfterEach
    @Rollback(value = false)
    public void testAllDeletedGrade(){
        for (Grade grd: gradeRepo.findAll()){
            gradeRepo.deleteById(grd.getId());
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
    public void testGradeById() {
        int gradeId = gradeRepo.findAll().get(0).getId();

        ResponseEntity<Grade> response = restTemplate.exchange("http://localhost:" + port + "/api/grades/" + gradeId, HttpMethod.GET, HttpEntity.EMPTY, Grade.class);

        Grade grade = response.getBody();
        assertNotNull(grade);
    }

    @Test
    @Order(1)
    public void testGradeByList() {
        ResponseEntity<List<Grade>> response = restTemplate.exchange("http://localhost:" + port + "/api/grades", HttpMethod.GET,HttpEntity.EMPTY,  new ParameterizedTypeReference<List<Grade>>() {});

        assertThat(gradeRepo.findAll()).size().isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void testGradeByListUserLibraryId() {
        UserLibrary userLibrary = gradeRepo.findAll().get(0).getUserLibrary();

        ResponseEntity<List<Grade>> response = restTemplate.exchange("http://localhost:" + port + "/api/grades/userLibrary/" + userLibrary.getId(), HttpMethod.GET,HttpEntity.EMPTY,  new ParameterizedTypeReference<List<Grade>>() {});

        assertNotNull(userLibrary);
    }

    @Test
    @Order(3)
    public void testGradeByListBookId() {
        Book book = gradeRepo.findAll().get(0).getBook();

        ResponseEntity<List<Grade>> response = restTemplate.exchange("http://localhost:" + port + "/api/grades/book/" + book.getId(), HttpMethod.GET,HttpEntity.EMPTY,  new ParameterizedTypeReference<List<Grade>>() {});

        assertNotNull(book);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void testGradeDeleteById(){
        int gradeId = gradeRepo.findAll().get(0).getId();
        UserLibrary userLibrary = userLibraryRepo.findByLogin("DefaultAdmin").get();
        HttpHeaders headers = new HttpHeaders();
        AuthenticationDto authenticationDto = new AuthenticationDto(userLibrary.getLogin(),"1");
        headers.set("Authorization", "Bearer " + authController.logIn(authenticationDto).getBody().getToken());
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        restTemplate.exchange("http://localhost:" + port + "/api/grades/" + gradeId, HttpMethod.DELETE, entity, Integer.class);

        assertFalse(gradeRepo.findById(gradeId).isPresent());
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void testGradeCreate() {
        Grade savedGrade = new Grade();
        savedGrade.setRating(5);
        savedGrade.setComment("Comment3");
        int bookId = bookRepo.findAll().get(0).getId();
        if (bookRepo.findById(bookId).isPresent()) {
            savedGrade.setBook(bookRepo.findById(bookId).get());
        }
        int userLibraryId = userLibraryRepo.findAll().get(0).getId();
        if (userLibraryRepo.findById(userLibraryId).isPresent()) {
            savedGrade.setUserLibrary(userLibraryRepo.findById(userLibraryId).get());
        }
        Role roleUser = new Role("ROLE_USER");
        pRepository.save(roleUser);
        UserLibrary userLibrary = userLibraryRepo.findByLogin("DefaultAdmin").get();
        userLibrary.setRole(pRepository.findByNameRole("ROLE_USER").get());
        userLibraryRepo.save(userLibrary);
        userLibrary = userLibraryRepo.findByLogin("DefaultAdmin").get();
        HttpHeaders headers = new HttpHeaders();
        AuthenticationDto authenticationDto = new AuthenticationDto(userLibrary.getLogin(),"1");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rating",savedGrade.getRating());
        jsonObject.put("comment",savedGrade.getComment());
        headers.set("Authorization", "Bearer " + authController.logIn(authenticationDto).getBody().getToken());
        headers.set("Content-type", "application/json; charset=UTF-8");
        HttpEntity<Object> entity = new HttpEntity<>(jsonObject,headers);

        ResponseEntity<Integer> response = restTemplate.exchange("http://localhost:" + port + "/api/grades/save?bookId=" + bookId + "&login=" + userLibrary.getLogin(), HttpMethod.PUT, entity, Integer.class);

        List<Grade> gradeList = gradeRepo.findAll();
        assertNotNull(gradeList.get(gradeList.size()-1));
        assertThat(gradeList.get(gradeList.size()-1).getRating()).isEqualTo(5);
        assertThat(gradeList.get(gradeList.size()-1).getComment()).isEqualTo("Comment3");
    }
}
