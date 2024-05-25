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
public class AuthorIntegrationTest {
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
    private AuthorRepo authorRepo;
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private RentalRepo rentalRepo;
    @Autowired
    private GradeRepo gradeRepo;
    @Autowired
    private RoleRepo pRepository;

    @BeforeEach
    @Rollback(value = false)
    public void testGeneratedAuthor(){
        userLibraryService.createDefaultAdmin();
        Author author1 = new Author();
        author1.setName("Name1");
        author1.setSurname("Surname1");
        author1.setPatronymic("Patronymic1");
        author1.setDateBirth(new Date(24,0,29));
        author1.setBooks(new ArrayList<>());
        authorRepo.save(author1);
        Author author2 = new Author();
        author2.setName("Name2");
        author2.setSurname("Surname2");
        author2.setPatronymic("Patronymic2");
        author2.setDateBirth(new Date(24,0,29));
        author2.setBooks(new ArrayList<>());
        authorRepo.save(author2);
    }

    @AfterEach
    @Rollback(value = false)
    public void testAllDeletedAuthor(){
        for (Author aut: authorRepo.findAll()){
            for (Book b: authorRepo.findById(aut.getId()).get().getBooks()){
                for (Rental rent: b.getRentals()){
                    rentalRepo.deleteById(rent.getId());
                }
                for (Grade gr: b.getGrades()){
                    gradeRepo.deleteById(gr.getId());
                }
                System.out.println("Delete Image");
                bookRepo.deleteById(b.getId());
            }
            authorRepo.deleteById(aut.getId());
        }

        for (UserLibrary usr: userLibraryRepo.findAll()){
            userLibraryRepo.deleteById(usr.getId());
        }
        for (Role rl: pRepository.findAll()){
            pRepository.deleteById(rl.getId());
        }
    }

    @Test
    @Order(0)
    public void testAuthorById() {
        int authorId = authorRepo.findAll().get(0).getId();

        ResponseEntity<Author> response = restTemplate.exchange("http://localhost:" + port + "/api/authors/" + authorId, HttpMethod.GET,HttpEntity.EMPTY, Author.class);

        Author author = response.getBody();
        assertNotNull(author);
    }

    @Test
    @Order(1)
    public void testAuthorByList() {
        ResponseEntity<List<Author>> response = restTemplate.exchange("http://localhost:" + port + "/api/authors", HttpMethod.GET,HttpEntity.EMPTY,  new ParameterizedTypeReference<List<Author>>() {});

        assertThat(response.getBody()).size().isGreaterThan(0);
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    public void testAuthorDeleteById(){
        int authorId = authorRepo.findAll().get(0).getId();
        UserLibrary userLibrary = userLibraryRepo.findByLogin("DefaultAdmin").get();
        HttpHeaders headers = new HttpHeaders();
        AuthenticationDto authenticationDto = new AuthenticationDto(userLibrary.getLogin(),"1");
        headers.set("Authorization", "Bearer " + authController.logIn(authenticationDto).getBody().getToken());
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        restTemplate.exchange("http://localhost:" + port + "/api/authors/" + authorId, HttpMethod.DELETE, entity, Integer.class);

        assertFalse(authorRepo.findById(authorId).isPresent());
    }

    @Test
    @Order(3)
    @Rollback(value = false)
    public void testAuthorUpdateById(){
        Author author = authorRepo.findAll().get(0);
        author.setName("Name3");
        author.setSurname("Surname3");
        author.setPatronymic("Patronymic3");
        author.setDateBirth(new Date(24,0,29));
        UserLibrary userLibrary = userLibraryRepo.findByLogin("DefaultAdmin").get();
        HttpHeaders headers = new HttpHeaders();
        AuthenticationDto authenticationDto = new AuthenticationDto(userLibrary.getLogin(),"1");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",author.getName());
        jsonObject.put("surname",author.getSurname());
        jsonObject.put("patronymic",author.getPatronymic());
        jsonObject.put("dateBirth",author.getDateBirth());
        headers.set("Authorization", "Bearer " + authController.logIn(authenticationDto).getBody().getToken());
        headers.set("Content-type", "application/json; charset=UTF-8");
        HttpEntity<Object> entity = new HttpEntity<>(jsonObject,headers);

        ResponseEntity<Integer> response = restTemplate.exchange("http://localhost:" + port + "/api/authors/update/" + author.getId(), HttpMethod.PUT, entity, Integer.class);

        Author updatedAuthor = authorRepo.findById(author.getId()).get();
        assertThat(updatedAuthor.getName()).isEqualTo("Name3");
        assertThat(updatedAuthor.getSurname()).isEqualTo("Surname3");
        assertThat(updatedAuthor.getPatronymic()).isEqualTo("Patronymic3");
        assertThat(updatedAuthor.getDateBirth()).isEqualTo(new Date(24,0,29));
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void testAuthorCreate(){
        Author savedAuthor = new Author();
        savedAuthor.setName("Name3");
        savedAuthor.setSurname("Surname3");
        savedAuthor.setPatronymic("Patronymic3");
        savedAuthor.setDateBirth(new Date(24,0,29));
        UserLibrary userLibrary = userLibraryRepo.findByLogin("DefaultAdmin").get();
        HttpHeaders headers = new HttpHeaders();
        AuthenticationDto authenticationDto = new AuthenticationDto(userLibrary.getLogin(),"1");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",savedAuthor.getName());
        jsonObject.put("surname",savedAuthor.getSurname());
        jsonObject.put("patronymic",savedAuthor.getPatronymic());
        jsonObject.put("dateBirth",savedAuthor.getDateBirth());
        headers.set("Authorization", "Bearer " + authController.logIn(authenticationDto).getBody().getToken());
        headers.set("Content-type", "application/json; charset=UTF-8");
        HttpEntity<Object> entity = new HttpEntity<>(jsonObject,headers);

        ResponseEntity<Integer> response = restTemplate.exchange("http://localhost:" + port + "/api/authors/save", HttpMethod.PUT, entity, Integer.class);

        List<Author> authorList = authorRepo.findAll();
        assertNotNull(authorList.get(authorList.size()-1));
        assertThat(authorList.get(authorList.size()-1).getName()).isEqualTo("Name3");
        assertThat(authorList.get(authorList.size()-1).getSurname()).isEqualTo("Surname3");
        assertThat(authorList.get(authorList.size()-1).getPatronymic()).isEqualTo("Patronymic3");
        assertThat(authorList.get(authorList.size()-1).getDateBirth()).isEqualTo(new Date(24,0,29));
    }
}
