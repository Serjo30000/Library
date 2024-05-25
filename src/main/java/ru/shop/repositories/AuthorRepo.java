package ru.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shop.models.Author;
import ru.shop.models.Book;

import java.sql.Date;
import java.util.List;

@Repository
public interface AuthorRepo extends JpaRepository<Author,Integer> {
    @Modifying
    @Query("update Author a set a.name=:name, a.surname=:surname, a.patronymic=:patronymic, a.dateBirth=:dateBirth where a.id=:id")
    void updateAuthor(@Param("name") String name, @Param("surname") String surname, @Param("patronymic") String patronymic, @Param("dateBirth") Date dateBirth, @Param("id") int id);
}
