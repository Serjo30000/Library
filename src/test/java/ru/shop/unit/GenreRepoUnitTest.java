package ru.shop.unit;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import ru.shop.models.Genre;
import ru.shop.repositories.GenreRepo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GenreRepoUnitTest {
    @InjectMocks
    private GenreRepo genreRepo = mock(GenreRepo.class);

    @Test
    @Order(0)
    public void testFindById(){
        Optional<Genre> genre = Optional.of(new Genre());
        genre.get().setId(1);
        genre.get().setNameGenre("NameGenre1");
        genre.get().setBooks(new ArrayList<>());

        when(genreRepo.findById(1)).thenReturn(genre);
        Optional<Genre> resultGenre = genreRepo.findById(1);

        assertEquals(genre.get().getId(), resultGenre.get().getId());
        verify(genreRepo, times(1)).findById(1);
    }

    @Test
    @Order(1)
    public void testFindAll(){
        Genre genre = new Genre();
        genre.setId(1);
        genre.setNameGenre("NameGenre1");
        genre.setBooks(new ArrayList<>());
        List<Genre> genreList = Arrays.asList(genre);

        when(genreRepo.findAll()).thenReturn(Arrays.asList(genre));
        List<Genre> resultGenreList = genreRepo.findAll();

        assertEquals(genreList.size(), resultGenreList.size());
        verify(genreRepo, times(1)).findAll();
    }

    @Test
    @Order(2)
    public void testDeleteById(){
        int id = 1;

        genreRepo.deleteById(1);

        verify(genreRepo, times(1)).deleteById(id);
    }

    @Test
    @Order(3)
    public void testSave(){
        Genre genre = new Genre();
        genre.setNameGenre("NameGenre1");
        genre.setBooks(new ArrayList<>());

        when(genreRepo.save(genre)).thenReturn(genre);
        Genre savedGenre = genreRepo.save(genre);

        assertEquals(genre.getNameGenre(), savedGenre.getNameGenre());
        verify(genreRepo, times(1)).save(genre);
    }

    @Test
    @Order(4)
    public void testUpdate(){
        Genre genre = new Genre();
        int id = 1;
        genre.setId(id);
        genre.setNameGenre("NameGenre1");
        genre.setBooks(new ArrayList<>());

        genreRepo.updateGenre(genre.getNameGenre(), id);
        when(genreRepo.findById(1)).thenReturn(Optional.of(genre));
        Genre updatedGenre = genreRepo.findById(1).get();

        assertEquals(genre.getId(), updatedGenre.getId());
        assertEquals(genre.getNameGenre(), updatedGenre.getNameGenre());
        verify(genreRepo, times(1)).updateGenre(genre.getNameGenre(), id);
    }
}
