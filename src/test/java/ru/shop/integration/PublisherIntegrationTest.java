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
public class PublisherIntegrationTest {
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
    private PublisherRepo publisherRepo;
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
    public void testGeneratedPublisher(){
        userLibraryService.createDefaultAdmin();
        Publisher publisher1 = new Publisher();
        publisher1.setNamePublisher("Publisher1");
        publisher1.setAddress("Address1");
        publisher1.setBooks(new ArrayList<>());
        publisherRepo.save(publisher1);
        Publisher publisher2 = new Publisher();
        publisher2.setNamePublisher("Publisher2");
        publisher2.setAddress("Address2");
        publisher2.setBooks(new ArrayList<>());
        publisherRepo.save(publisher2);
    }

    @AfterEach
    @Rollback(value = false)
    public void testAllDeletedPublisher(){
        for (Publisher pbl: publisherRepo.findAll()){
            for (Book b: publisherRepo.findById(pbl.getId()).get().getBooks()){
                for (Rental rent: b.getRentals()){
                    rentalRepo.deleteById(rent.getId());
                }
                for (Grade gr: b.getGrades()){
                    gradeRepo.deleteById(gr.getId());
                }
                System.out.println("Delete Image");
                bookRepo.deleteById(b.getId());
            }
            publisherRepo.deleteById(pbl.getId());
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
    public void testPublisherById() {
        int publisherId = publisherRepo.findAll().get(0).getId();

        ResponseEntity<Publisher> response = restTemplate.exchange("http://localhost:" + port + "/api/publishers/" + publisherId, HttpMethod.GET, HttpEntity.EMPTY, Publisher.class);

        Publisher publisher = response.getBody();
        assertNotNull(publisher);
    }

    @Test
    @Order(1)
    public void testPublisherByList() {
        ResponseEntity<List<Publisher>> response = restTemplate.exchange("http://localhost:" + port + "/api/publishers", HttpMethod.GET,HttpEntity.EMPTY,  new ParameterizedTypeReference<List<Publisher>>() {});

        assertThat(publisherRepo.findAll()).size().isGreaterThan(0);
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    public void testPublisherDeleteById() {
        int publisherId = publisherRepo.findAll().get(0).getId();
        UserLibrary userLibrary = userLibraryRepo.findByLogin("DefaultAdmin").get();
        HttpHeaders headers = new HttpHeaders();
        AuthenticationDto authenticationDto = new AuthenticationDto(userLibrary.getLogin(),"1");
        headers.set("Authorization", "Bearer " + authController.logIn(authenticationDto).getBody().getToken());
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        restTemplate.exchange("http://localhost:" + port + "/api/publishers/" + publisherId, HttpMethod.DELETE, entity, Integer.class);

        assertFalse(publisherRepo.findById(publisherId).isPresent());
    }

    @Test
    @Order(3)
    @Rollback(value = false)
    public void testPublisherUpdateById() {
        Publisher publisher = publisherRepo.findAll().get(0);
        publisher.setNamePublisher("Publisher3");
        publisher.setAddress("Address3");
        UserLibrary userLibrary = userLibraryRepo.findByLogin("DefaultAdmin").get();
        HttpHeaders headers = new HttpHeaders();
        AuthenticationDto authenticationDto = new AuthenticationDto(userLibrary.getLogin(),"1");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("namePublisher",publisher.getNamePublisher());
        jsonObject.put("address",publisher.getAddress());
        headers.set("Authorization", "Bearer " + authController.logIn(authenticationDto).getBody().getToken());
        headers.set("Content-type", "application/json; charset=UTF-8");
        HttpEntity<Object> entity = new HttpEntity<>(jsonObject,headers);

        ResponseEntity<Integer> response = restTemplate.exchange("http://localhost:" + port + "/api/publishers/update/" + publisher.getId(), HttpMethod.PUT, entity, Integer.class);

        Publisher updatedPublisher = publisherRepo.findById(publisher.getId()).get();
        assertThat(updatedPublisher.getNamePublisher()).isEqualTo("Publisher3");
        assertThat(updatedPublisher.getAddress()).isEqualTo("Address3");
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void testPublisherCreate() {
        Publisher savedPublisher = new Publisher();
        savedPublisher.setNamePublisher("Publisher3");
        savedPublisher.setAddress("Address3");
        savedPublisher.setBooks(new ArrayList<>());
        UserLibrary userLibrary = userLibraryRepo.findByLogin("DefaultAdmin").get();
        HttpHeaders headers = new HttpHeaders();
        AuthenticationDto authenticationDto = new AuthenticationDto(userLibrary.getLogin(),"1");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("namePublisher",savedPublisher.getNamePublisher());
        jsonObject.put("address",savedPublisher.getAddress());
        headers.set("Authorization", "Bearer " + authController.logIn(authenticationDto).getBody().getToken());
        headers.set("Content-type", "application/json; charset=UTF-8");
        HttpEntity<Object> entity = new HttpEntity<>(jsonObject,headers);

        ResponseEntity<Integer> response = restTemplate.exchange("http://localhost:" + port + "/api/publishers/save", HttpMethod.PUT, entity, Integer.class);

        List<Publisher> publisherList = publisherRepo.findAll();
        assertNotNull(publisherList.get(publisherList.size()-1));
        assertThat(publisherList.get(publisherList.size()-1).getNamePublisher()).isEqualTo("Publisher3");
        assertThat(publisherList.get(publisherList.size()-1).getAddress()).isEqualTo("Address3");
    }
}
