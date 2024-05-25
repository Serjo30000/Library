package ru.shop.unit;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import ru.shop.models.Role;
import ru.shop.repositories.RoleRepo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RoleRepoUnitTest {
    @InjectMocks
    private RoleRepo roleRepo = mock(RoleRepo.class);

    @Test
    @Order(0)
    public void testFindById(){
        Optional<Role> role = Optional.of(new Role());
        role.get().setId(1);
        role.get().setNameRole("ROLE_ADMIN");
        role.get().setUserLibraries(new ArrayList<>());

        when(roleRepo.findById(1)).thenReturn(role);
        Optional<Role> resultRole = roleRepo.findById(1);

        assertEquals(role.get().getId(), resultRole.get().getId());
        verify(roleRepo, times(1)).findById(1);
    }

    @Test
    @Order(1)
    public void testFindAll(){
        Role role = new Role();
        role.setId(1);
        role.setNameRole("ROLE_ADMIN");
        role.setUserLibraries(new ArrayList<>());
        List<Role> roleList = Arrays.asList(role);

        when(roleRepo.findAll()).thenReturn(Arrays.asList(role));
        List<Role> resultRoleList = roleRepo.findAll();

        assertEquals(roleList.size(), resultRoleList.size());
        verify(roleRepo, times(1)).findAll();
    }

    @Test
    @Order(2)
    public void testDeleteById(){
        int id = 1;

        roleRepo.deleteById(1);

        verify(roleRepo, times(1)).deleteById(id);
    }

    @Test
    @Order(3)
    public void testSave(){
        Role role = new Role();
        role.setNameRole("ROLE_ADMIN");
        role.setUserLibraries(new ArrayList<>());

        when(roleRepo.save(role)).thenReturn(role);
        Role savedRole = roleRepo.save(role);

        assertEquals(role.getNameRole(), savedRole.getNameRole());
        verify(roleRepo, times(1)).save(role);
    }

    @Test
    @Order(4)
    public void testFindByNameRole(){
        Optional<Role> role = Optional.of(new Role());
        role.get().setId(1);
        role.get().setNameRole("ROLE_ADMIN");
        role.get().setUserLibraries(new ArrayList<>());

        when(roleRepo.findByNameRole("ROLE_ADMIN")).thenReturn(role);
        Optional<Role> resultRole = roleRepo.findByNameRole("ROLE_ADMIN");

        assertEquals(role.get().getId(), resultRole.get().getId());
        verify(roleRepo, times(1)).findByNameRole("ROLE_ADMIN");
    }
}
