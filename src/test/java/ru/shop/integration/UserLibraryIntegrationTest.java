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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import ru.shop.controllers.AuthController;
import ru.shop.dto.AuthenticationDto;
import ru.shop.dto.UserLibraryDto;
import ru.shop.dto.UserLibraryDtoOut;
import ru.shop.models.*;
import ru.shop.repositories.GradeRepo;
import ru.shop.repositories.RentalRepo;
import ru.shop.repositories.RoleRepo;
import ru.shop.repositories.UserLibraryRepo;
import ru.shop.services.UserLibraryService;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserLibraryIntegrationTest {
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
    private RoleRepo pRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RentalRepo rentalRepo;
    @Autowired
    private GradeRepo gradeRepo;

    @BeforeEach
    @Rollback(value = false)
    public void testGeneratedAuthor(){
        userLibraryService.createDefaultAdmin();
    }

    @AfterEach
    @Rollback(value = false)
    public void testAllDeletedAuthor(){
        for (UserLibrary usr: userLibraryRepo.findAll()){
            userLibraryRepo.deleteById(usr.getId());
        }
        for (Role rl: pRepository.findAll()){
            pRepository.deleteById(rl.getId());
        }
    }

    @Test
    @Order(0)
    public void testUserLibraryByList() {
        ResponseEntity<List<UserLibraryDtoOut>> response = restTemplate.exchange("http://localhost:" + port + "/api/userLibraries", HttpMethod.GET,HttpEntity.EMPTY,  new ParameterizedTypeReference<List<UserLibraryDtoOut>>() {});

        assertThat(response.getBody()).size().isGreaterThan(0);
    }

    @Test
    @Order(1)
    public void testGetAllUserLibraryByRoleId() {
        int roleId = 1;

        ResponseEntity<List<UserLibraryDtoOut>> response = restTemplate.exchange("http://localhost:" + port + "/api/userLibraries/role/" + roleId, HttpMethod.GET,HttpEntity.EMPTY,  new ParameterizedTypeReference<List<UserLibraryDtoOut>>() {});

        assertThat(response.getBody()).size().isGreaterThan(0);
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    public void testSaveUserLibrary(){
        UserLibraryDto userLibrary = new UserLibraryDto();
        userLibrary.setName("Name2");
        userLibrary.setSurname("Surname2");
        userLibrary.setPatronymic("Patronymic2");
        userLibrary.setPhone("70000000002");
        userLibrary.setEmail("Admin2@admin.ru");
        userLibrary.setLogin("Login2");
        userLibrary.setPassword("2");
        HttpHeaders headers = new HttpHeaders();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",userLibrary.getName());
        jsonObject.put("surname",userLibrary.getSurname());
        jsonObject.put("patronymic",userLibrary.getPatronymic());
        jsonObject.put("phone",userLibrary.getPhone());
        jsonObject.put("email",userLibrary.getEmail());
        jsonObject.put("login",userLibrary.getLogin());
        jsonObject.put("password",userLibrary.getPassword());
        headers.set("Content-type", "application/json; charset=UTF-8");
        HttpEntity<Object> entity = new HttpEntity<>(jsonObject,headers);

        ResponseEntity<Integer> response = restTemplate.exchange("http://localhost:" + port + "/api/userLibraries/save", HttpMethod.PUT, entity, Integer.class);

        List<UserLibrary> userLibraryList = userLibraryRepo.findAll();
        assertNotNull(userLibraryList.get(userLibraryList.size()-1));
        assertThat(userLibraryList.get(userLibraryList.size()-1).getName()).isEqualTo("Name2");
        assertThat(userLibraryList.get(userLibraryList.size()-1).getSurname()).isEqualTo("Surname2");
        assertThat(userLibraryList.get(userLibraryList.size()-1).getPatronymic()).isEqualTo("Patronymic2");
        assertThat(userLibraryList.get(userLibraryList.size()-1).getPhone()).isEqualTo("70000000002");
        assertThat(userLibraryList.get(userLibraryList.size()-1).getEmail()).isEqualTo("Admin2@admin.ru");
        assertThat(userLibraryList.get(userLibraryList.size()-1).getLogin()).isEqualTo("Login2");
    }

    @Test
    @Order(3)
    @Rollback(value = false)
    public void testCreateDefaultAdmin(){
        ResponseEntity<Integer> response = restTemplate.exchange("http://localhost:" + port + "/api/userLibraries/createDefaultAdmin", HttpMethod.PUT, HttpEntity.EMPTY, Integer.class);

        assertTrue(true);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void testUpdateRoleUser(){
        int id = 2;
        Role roleUser = new Role("ROLE_USER");
        pRepository.save(roleUser);
        UserLibrary userLibrary = new UserLibrary();
        userLibrary.setName("Name2");
        userLibrary.setSurname("Surname2");
        userLibrary.setPatronymic("Patronymic2");
        userLibrary.setPhone("70000000002");
        userLibrary.setEmail("Admin2@admin.ru");
        userLibrary.setLogin("Login2");
        userLibrary.setPassword("2");
        userLibraryService.saveUserLibrary(userLibrary);
        UserLibrary userLibraryAuth = userLibraryRepo.findByLogin("DefaultAdmin").get();
        HttpHeaders headers = new HttpHeaders();
        AuthenticationDto authenticationDto = new AuthenticationDto(userLibraryAuth.getLogin(),"1");
        headers.set("Authorization", "Bearer " + authController.logIn(authenticationDto).getBody().getToken());
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        ResponseEntity<Integer> response = restTemplate.exchange("http://localhost:" + port + "/api/userLibraries/" + id + "/updateRole", HttpMethod.GET,entity,  Integer.class);

        List<UserLibrary> userLibraryList = userLibraryRepo.findAll();
        assertNotNull(userLibraryList.get(userLibraryList.size()-1));
        assertThat(userLibraryList.get(userLibraryList.size()-1).getRole().getNameRole()).isEqualTo("ROLE_ADMIN");
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void testUpdateRoleUserByLogin(){
        String login = "Login2";
        Role roleUser = new Role("ROLE_USER");
        pRepository.save(roleUser);
        UserLibrary userLibrary = new UserLibrary();
        userLibrary.setName("Name2");
        userLibrary.setSurname("Surname2");
        userLibrary.setPatronymic("Patronymic2");
        userLibrary.setPhone("70000000002");
        userLibrary.setEmail("Admin2@admin.ru");
        userLibrary.setLogin("Login2");
        userLibrary.setPassword("2");
        userLibraryService.saveUserLibrary(userLibrary);
        UserLibrary userLibraryAuth = userLibraryRepo.findByLogin("DefaultAdmin").get();
        HttpHeaders headers = new HttpHeaders();
        AuthenticationDto authenticationDto = new AuthenticationDto(userLibraryAuth.getLogin(),"1");
        headers.set("Authorization", "Bearer " + authController.logIn(authenticationDto).getBody().getToken());
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/api/userLibraries/" + login + "/updateRoleByLogin", HttpMethod.GET,entity,  String.class);

        List<UserLibrary> userLibraryList = userLibraryRepo.findAll();
        assertNotNull(userLibraryList.get(userLibraryList.size()-1));
        assertThat(userLibraryList.get(userLibraryList.size()-1).getRole().getNameRole()).isEqualTo("ROLE_ADMIN");
    }

    @Test
    @Order(6)
    @Rollback(value = false)
    public void testGetByLogin(){
        String login = "DefaultAdmin";

        ResponseEntity<UserLibrary> response = restTemplate.exchange("http://localhost:" + port + "/api/userLibraries/user/" + login, HttpMethod.GET,HttpEntity.EMPTY,  UserLibrary.class);

        List<UserLibrary> userLibraryList = userLibraryRepo.findAll();
        assertNotNull(userLibraryList.get(userLibraryList.size()-1));
        assertThat(userLibraryList.get(userLibraryList.size()-1).getLogin()).isEqualTo("DefaultAdmin");
    }

    @Test
    @Order(7)
    @Rollback(value = false)
    public void testDeleteUserLibrary(){
        int id = 2;
        Role roleUser = new Role("ROLE_USER");
        pRepository.save(roleUser);
        UserLibrary userLibrary = new UserLibrary();
        userLibrary.setName("Name2");
        userLibrary.setSurname("Surname2");
        userLibrary.setPatronymic("Patronymic2");
        userLibrary.setPhone("70000000002");
        userLibrary.setEmail("Admin2@admin.ru");
        userLibrary.setLogin("Login2");
        userLibrary.setPassword("2");
        userLibraryService.saveUserLibrary(userLibrary);
        UserLibrary userLibraryAuth = userLibraryRepo.findByLogin("DefaultAdmin").get();
        HttpHeaders headers = new HttpHeaders();
        AuthenticationDto authenticationDto = new AuthenticationDto(userLibraryAuth.getLogin(),"1");
        headers.set("Authorization", "Bearer " + authController.logIn(authenticationDto).getBody().getToken());
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        restTemplate.exchange("http://localhost:" + port + "/api/userLibraries/" + id, HttpMethod.DELETE, entity, Integer.class);

        assertFalse(userLibraryRepo.findById(id).isPresent());
    }

    @Test
    @Order(8)
    @Rollback(value = false)
    public void testDeleteUserLibraryByLogin(){
        String login = "Login2";
        Role roleUser = new Role("ROLE_USER");
        pRepository.save(roleUser);
        UserLibrary userLibrary = new UserLibrary();
        userLibrary.setName("Name2");
        userLibrary.setSurname("Surname2");
        userLibrary.setPatronymic("Patronymic2");
        userLibrary.setPhone("70000000002");
        userLibrary.setEmail("Admin2@admin.ru");
        userLibrary.setLogin("Login2");
        userLibrary.setPassword("2");
        userLibraryService.saveUserLibrary(userLibrary);
        UserLibrary userLibraryAuth = userLibraryRepo.findByLogin("DefaultAdmin").get();
        HttpHeaders headers = new HttpHeaders();
        AuthenticationDto authenticationDto = new AuthenticationDto(userLibraryAuth.getLogin(),"1");
        headers.set("Authorization", "Bearer " + authController.logIn(authenticationDto).getBody().getToken());
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        restTemplate.exchange("http://localhost:" + port + "/api/userLibraries/delete/" + login, HttpMethod.DELETE, entity, String.class);

        assertFalse(userLibraryRepo.findByLogin("Login2").isPresent());
    }
}
