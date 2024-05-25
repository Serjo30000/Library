package ru.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shop.models.Book;
import ru.shop.models.Genre;
import ru.shop.models.Grade;

import java.util.List;

@Repository
public interface GradeRepo extends JpaRepository<Grade,Integer> {
    List<Grade> findByBookId(int book);
    List<Grade> findByUserLibraryId(int userLibrary);
}
