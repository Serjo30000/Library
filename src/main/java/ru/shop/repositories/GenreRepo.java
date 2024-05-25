package ru.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shop.models.Genre;

@Repository
public interface GenreRepo extends JpaRepository<Genre,Integer> {
    @Modifying
    @Query("update Genre g set g.nameGenre=:nameGenre where g.id=:id")
    void updateGenre(@Param("nameGenre") String nameGenre, @Param("id") int id);
}
