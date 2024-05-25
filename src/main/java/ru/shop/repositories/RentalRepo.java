package ru.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shop.models.*;

import java.sql.Date;
import java.util.List;

@Repository
public interface RentalRepo extends JpaRepository<Rental,Integer> {
    List<Rental> findByBookId(int book);
    List<Rental> findByUserLibraryId(int userLibrary);
    @Modifying
    @Query("update Rental r set r.countBook=:countBook, r.dateStart=:dateStart, r.dateEnd=:dateEnd, r.book=:book, r.userLibrary=:userLibrary where r.id=:id")
    void updateRental(@Param("countBook") int countBook, @Param("dateStart") Date dateStart, @Param("dateEnd") Date dateEnd, @Param("book") Book book, @Param("userLibrary") UserLibrary userLibrary, @Param("id") int id);
}
