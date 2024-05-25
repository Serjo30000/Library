package ru.shop.unit;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import ru.shop.models.*;
import ru.shop.repositories.UserLibraryRepo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
public class UserLibraryRepoUnitTest {
    @InjectMocks
    private UserLibraryRepo userLibraryRepo = mock(UserLibraryRepo.class);

    @Test
    @Order(0)
    public void testFindById(){
        Optional<UserLibrary> userLibrary = Optional.of(new UserLibrary());
        userLibrary.get().setId(1);
        userLibrary.get().setName("Name2");
        userLibrary.get().setSurname("Surname2");
        userLibrary.get().setPatronymic("Patronymic2");
        userLibrary.get().setPhone("70000000002");
        userLibrary.get().setEmail("Admin2@admin.ru");
        userLibrary.get().setLogin("Login2");
        userLibrary.get().setPassword("2");
        userLibrary.get().setRole(new Role());
        userLibrary.get().setGrades(new ArrayList<>());
        userLibrary.get().setRentals(new ArrayList<>());

        when(userLibraryRepo.findById(1)).thenReturn(userLibrary);
        Optional<UserLibrary> resultUserLibrary = userLibraryRepo.findById(1);

        assertEquals(userLibrary.get().getId(), resultUserLibrary.get().getId());
        verify(userLibraryRepo, times(1)).findById(1);
    }

    @Test
    @Order(1)
    public void testFindAll(){
        UserLibrary userLibrary = new UserLibrary();
        userLibrary.setId(1);
        userLibrary.setName("Name2");
        userLibrary.setSurname("Surname2");
        userLibrary.setPatronymic("Patronymic2");
        userLibrary.setPhone("70000000002");
        userLibrary.setEmail("Admin2@admin.ru");
        userLibrary.setLogin("Login2");
        userLibrary.setPassword("2");
        userLibrary.setRole(new Role());
        userLibrary.setGrades(new ArrayList<>());
        userLibrary.setRentals(new ArrayList<>());
        List<UserLibrary> userLibraryList = Arrays.asList(userLibrary);

        when(userLibraryRepo.findAll()).thenReturn(Arrays.asList(userLibrary));
        List<UserLibrary> resultUserLibraryList = userLibraryRepo.findAll();

        assertEquals(userLibraryList.size(), resultUserLibraryList.size());
        verify(userLibraryRepo, times(1)).findAll();
    }

    @Test
    @Order(2)
    public void testDeleteById(){
        int id = 1;

        userLibraryRepo.deleteById(1);

        verify(userLibraryRepo, times(1)).deleteById(id);
    }

    @Test
    @Order(3)
    public void testSave(){
        UserLibrary userLibrary = new UserLibrary();
        userLibrary.setName("Name2");
        userLibrary.setSurname("Surname2");
        userLibrary.setPatronymic("Patronymic2");
        userLibrary.setPhone("70000000002");
        userLibrary.setEmail("Admin2@admin.ru");
        userLibrary.setLogin("Login2");
        userLibrary.setPassword("2");
        userLibrary.setRole(new Role());
        userLibrary.setGrades(new ArrayList<>());
        userLibrary.setRentals(new ArrayList<>());

        when(userLibraryRepo.save(userLibrary)).thenReturn(userLibrary);
        UserLibrary savedUserLibrary = userLibraryRepo.save(userLibrary);

        assertEquals(userLibrary.getName(), savedUserLibrary.getName());
        assertEquals(userLibrary.getSurname(), savedUserLibrary.getSurname());
        assertEquals(userLibrary.getPatronymic(), savedUserLibrary.getPatronymic());
        assertEquals(userLibrary.getPhone(), savedUserLibrary.getPhone());
        assertEquals(userLibrary.getEmail(), savedUserLibrary.getEmail());
        assertEquals(userLibrary.getLogin(), savedUserLibrary.getLogin());
        verify(userLibraryRepo, times(1)).save(userLibrary);
    }

    @Test
    @Order(4)
    public void testFindByRoleId(){
        Optional<UserLibrary> userLibrary = Optional.of(new UserLibrary());
        userLibrary.get().setId(1);
        userLibrary.get().setName("Name2");
        userLibrary.get().setSurname("Surname2");
        userLibrary.get().setPatronymic("Patronymic2");
        userLibrary.get().setPhone("70000000002");
        userLibrary.get().setEmail("Admin2@admin.ru");
        userLibrary.get().setLogin("Login2");
        userLibrary.get().setPassword("2");
        int roleId = 1;
        Role role = new Role();
        role.setId(roleId);
        role.setNameRole("ROLE_ADMIN");
        role.setUserLibraries(Arrays.asList(userLibrary.get()));
        userLibrary.get().setRole(role);
        userLibrary.get().setGrades(new ArrayList<>());
        userLibrary.get().setRentals(new ArrayList<>());

        when(userLibraryRepo.findByRoleId(roleId)).thenReturn(role.getUserLibraries());
        List<UserLibrary> resultRoleList = userLibraryRepo.findByRoleId(roleId);

        assertEquals(role.getUserLibraries().size(), resultRoleList.size());
        verify(userLibraryRepo, times(1)).findByRoleId(roleId);
    }

    @Test
    @Order(5)
    public void testDeleteUserByLogin(){
        String login = "DefaultAdmin";

        userLibraryRepo.deleteUserByLogin(login);

        verify(userLibraryRepo, times(1)).deleteUserByLogin(login);
    }

    @Test
    @Order(6)
    public void testUpdateRoleUser(){
        UserLibrary userLibrary = new UserLibrary();
        int id = 1;
        userLibrary.setId(id);
        userLibrary.setName("Name2");
        userLibrary.setSurname("Surname2");
        userLibrary.setPatronymic("Patronymic2");
        userLibrary.setPhone("70000000002");
        userLibrary.setEmail("Admin2@admin.ru");
        userLibrary.setLogin("Login2");
        userLibrary.setPassword("2");
        userLibrary.setRole(new Role());
        userLibrary.setGrades(new ArrayList<>());
        userLibrary.setRentals(new ArrayList<>());
        Role role = new Role();
        int roleId = 1;
        role.setId(roleId);
        role.setNameRole("ROLE_ADMIN");
        role.setUserLibraries(new ArrayList<>());

        userLibraryRepo.updateRoleUser(role, id);
        userLibrary.setRole(role);
        when(userLibraryRepo.findById(id)).thenReturn(Optional.of(userLibrary));
        UserLibrary updatedUserLibrary = userLibraryRepo.findById(id).get();

        assertEquals(userLibrary.getId(), updatedUserLibrary.getId());
        assertEquals(userLibrary.getName(), updatedUserLibrary.getName());
        assertEquals(userLibrary.getSurname(), updatedUserLibrary.getSurname());
        assertEquals(userLibrary.getPatronymic(), updatedUserLibrary.getPatronymic());
        assertEquals(userLibrary.getPhone(), updatedUserLibrary.getPhone());
        assertEquals(userLibrary.getEmail(), updatedUserLibrary.getEmail());
        assertEquals(userLibrary.getLogin(), updatedUserLibrary.getLogin());
        verify(userLibraryRepo, times(1)).updateRoleUser(role, id);
    }

    @Test
    @Order(7)
    public void testUpdateRoleUserByLogin(){
        UserLibrary userLibrary = new UserLibrary();
        String login = "Login2";
        int id = 1;
        userLibrary.setId(id);
        userLibrary.setName("Name2");
        userLibrary.setSurname("Surname2");
        userLibrary.setPatronymic("Patronymic2");
        userLibrary.setPhone("70000000002");
        userLibrary.setEmail("Admin2@admin.ru");
        userLibrary.setLogin("Login2");
        userLibrary.setPassword("2");
        userLibrary.setRole(new Role());
        userLibrary.setGrades(new ArrayList<>());
        userLibrary.setRentals(new ArrayList<>());
        Role role = new Role();
        int roleId = 1;
        role.setId(roleId);
        role.setNameRole("ROLE_ADMIN");
        role.setUserLibraries(new ArrayList<>());

        userLibraryRepo.updateRoleUserByLogin(role, login);
        userLibrary.setRole(role);
        when(userLibraryRepo.findByLogin(login)).thenReturn(Optional.of(userLibrary));
        UserLibrary updatedUserLibrary = userLibraryRepo.findByLogin(login).get();

        assertEquals(userLibrary.getId(), updatedUserLibrary.getId());
        assertEquals(userLibrary.getName(), updatedUserLibrary.getName());
        assertEquals(userLibrary.getSurname(), updatedUserLibrary.getSurname());
        assertEquals(userLibrary.getPatronymic(), updatedUserLibrary.getPatronymic());
        assertEquals(userLibrary.getPhone(), updatedUserLibrary.getPhone());
        assertEquals(userLibrary.getEmail(), updatedUserLibrary.getEmail());
        assertEquals(userLibrary.getLogin(), updatedUserLibrary.getLogin());
        verify(userLibraryRepo, times(1)).updateRoleUserByLogin(role, login);
    }

    @Test
    @Order(8)
    public void testFindByLogin(){
        Optional<UserLibrary> userLibrary = Optional.of(new UserLibrary());
        String login = "Login2";
        userLibrary.get().setId(1);
        userLibrary.get().setName("Name2");
        userLibrary.get().setSurname("Surname2");
        userLibrary.get().setPatronymic("Patronymic2");
        userLibrary.get().setPhone("70000000002");
        userLibrary.get().setEmail("Admin2@admin.ru");
        userLibrary.get().setLogin("Login2");
        userLibrary.get().setPassword("2");
        userLibrary.get().setRole(new Role());
        userLibrary.get().setGrades(new ArrayList<>());
        userLibrary.get().setRentals(new ArrayList<>());

        when(userLibraryRepo.findByLogin(login)).thenReturn(userLibrary);
        Optional<UserLibrary> resultUserLibrary = userLibraryRepo.findByLogin(login);

        assertEquals(userLibrary.get().getLogin(), resultUserLibrary.get().getLogin());
        verify(userLibraryRepo, times(1)).findByLogin(login);
    }
}
