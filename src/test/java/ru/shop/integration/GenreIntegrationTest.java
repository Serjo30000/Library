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
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GenreIntegrationTest {
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
    private GenreRepo genreRepo;
    @Autowired
    private RentalRepo rentalRepo;
    @Autowired
    private GradeRepo gradeRepo;
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private RoleRepo pRepository;

    @BeforeEach
    @Rollback(value = false)
    public void testGeneratedGenre(){
        userLibraryService.createDefaultAdmin();
        Genre genre1 = new Genre();
        genre1.setNameGenre("Genre1");
        genre1.setBooks(new ArrayList<>());
        genreRepo.save(genre1);
        Genre genre2 = new Genre();
        genre2.setNameGenre("Genre2");
        genre2.setBooks(new ArrayList<>());
        genreRepo.save(genre2);
    }

    @AfterEach
    @Rollback(value = false)
    public void testAllDeletedGenre(){
        for (Genre gnr: genreRepo.findAll()){
            for (Book b: genreRepo.findById(gnr.getId()).get().getBooks()){
                for (Rental rent: b.getRentals()){
                    rentalRepo.deleteById(rent.getId());
                }
                for (Grade gr: b.getGrades()){
                    gradeRepo.deleteById(gr.getId());
                }
                System.out.println("Delete Image");
                bookRepo.deleteById(b.getId());
            }
            genreRepo.deleteById(gnr.getId());
        }
        for (UserLibrary ur: userLibraryRepo.findAll()){
            userLibraryRepo.deleteById(ur.getId());
        }
        for (Role rl: pRepository.findAll()){
            pRepository.deleteById(rl.getId());
        }
    }

    @Test
    @Order(0)
    public void testGenreById() {
        int genreId = genreRepo.findAll().get(0).getId();

        ResponseEntity<Genre> response = restTemplate.exchange("http://localhost:" + port + "/api/genres/" + genreId, HttpMethod.GET, HttpEntity.EMPTY, Genre.class);

        Genre genre = response.getBody();
        assertNotNull(genre);
    }

    @Test
    @Order(1)
    public void testGenreByList() {
        ResponseEntity<List<Genre>> response = restTemplate.exchange("http://localhost:" + port + "/api/genres", HttpMethod.GET,HttpEntity.EMPTY,  new ParameterizedTypeReference<List<Genre>>() {});

        assertThat(genreRepo.findAll()).size().isGreaterThan(0);
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    public void testGenreDeleteById() {
        int genreId = genreRepo.findAll().get(0).getId();
        UserLibrary userLibrary = userLibraryRepo.findByLogin("DefaultAdmin").get();
        HttpHeaders headers = new HttpHeaders();
        AuthenticationDto authenticationDto = new AuthenticationDto(userLibrary.getLogin(),"1");
        headers.set("Authorization", "Bearer " + authController.logIn(authenticationDto).getBody().getToken());
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        restTemplate.exchange("http://localhost:" + port + "/api/genres/" + genreId, HttpMethod.DELETE, entity, Integer.class);

        assertFalse(genreRepo.findById(genreId).isPresent());
    }

    @Test
    @Order(3)
    @Rollback(value = false)
    public void testGenreUpdateById() {
        Genre genre = genreRepo.findAll().get(0);
        genre.setNameGenre("Genre3");
        UserLibrary userLibrary = userLibraryRepo.findByLogin("DefaultAdmin").get();
        HttpHeaders headers = new HttpHeaders();
        AuthenticationDto authenticationDto = new AuthenticationDto(userLibrary.getLogin(),"1");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nameGenre",genre.getNameGenre());
        headers.set("Authorization", "Bearer " + authController.logIn(authenticationDto).getBody().getToken());
        headers.set("Content-type", "application/json; charset=UTF-8");
        HttpEntity<Object> entity = new HttpEntity<>(jsonObject,headers);

        ResponseEntity<Integer> response = restTemplate.exchange("http://localhost:" + port + "/api/genres/update/" + genre.getId(), HttpMethod.PUT, entity, Integer.class);

        Genre updatedGenre = genreRepo.findById(genre.getId()).get();
        assertThat(updatedGenre.getNameGenre()).isEqualTo("Genre3");
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void testGenreCreate() {
        Genre savedGenre = new Genre();
        savedGenre.setNameGenre("Genre3");
        UserLibrary userLibrary = userLibraryRepo.findByLogin("DefaultAdmin").get();
        HttpHeaders headers = new HttpHeaders();
        AuthenticationDto authenticationDto = new AuthenticationDto(userLibrary.getLogin(),"1");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nameGenre",savedGenre.getNameGenre());
        headers.set("Authorization", "Bearer " + authController.logIn(authenticationDto).getBody().getToken());
        headers.set("Content-type", "application/json; charset=UTF-8");
        HttpEntity<Object> entity = new HttpEntity<>(jsonObject,headers);

        ResponseEntity<Integer> response = restTemplate.exchange("http://localhost:" + port + "/api/genres/save", HttpMethod.PUT, entity, Integer.class);

        List<Genre> genreList = genreRepo.findAll();
        assertNotNull(genreList.get(genreList.size()-1));
        assertThat(genreList.get(genreList.size()-1).getNameGenre()).isEqualTo("Genre3");
    }
}
