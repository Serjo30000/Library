package ru.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shop.models.Book;
import ru.shop.models.Publisher;
import ru.shop.models.UserLibrary;

import java.sql.Date;

@Repository
public interface PublisherRepo extends JpaRepository<Publisher,Integer> {
    @Modifying
    @Query("update Publisher p set p.namePublisher=:namePublisher, p.address=:address where p.id=:id")
    void updatePublisher(@Param("namePublisher") String namePublisher, @Param("address") String address, @Param("id") int id);

}
