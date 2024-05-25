package ru.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shop.models.Book;
import ru.shop.models.Role;
import ru.shop.models.UserLibrary;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLibraryRepo extends JpaRepository<UserLibrary,Integer> {
    List<UserLibrary> findByRoleId(int role);
    @Modifying
    @Query("delete UserLibrary a where a.login=:login")
    void deleteUserByLogin(@Param("login") String login);
    @Modifying
    @Query("update UserLibrary a set a.role=:r where a.id=:id")
    void updateRoleUser(@Param("r") Role r,@Param("id") int id);

    @Modifying
    @Query("update UserLibrary a set a.role=:r where a.login=:login")
    void updateRoleUserByLogin(@Param("r") Role r,@Param("login") String login);
    Optional<UserLibrary> findByLogin(String  login);
}
