package ru.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shop.models.Author;
import ru.shop.models.Book;
import ru.shop.models.Genre;
import ru.shop.models.Publisher;

import java.sql.Date;
import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book,Integer> {
    List<Book> findByGenreId(int genre);
    List<Book> findByAuthorId(int author);
    List<Book> findByPublisherId(int publisher);
    @Modifying
    @Query("update Book b set b.nameBook=:nameBook, b.datePublication=:datePublication, b.countBook=:countBook, b.price=:price, b.imageBook=:imageBook, b.genre=:genre, b.author=:author, b.publisher=:publisher where b.id=:id")
    void updateBook(@Param("nameBook") String nameBook, @Param("datePublication") Date datePublication , @Param("countBook") int countBook, @Param("price") double price, @Param("imageBook") String imageBook, @Param("genre") Genre genre, @Param("author") Author author, @Param("publisher") Publisher publisher, @Param("id") int id);
}
