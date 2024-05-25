package ru.shop.unit;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import ru.shop.models.Author;
import ru.shop.repositories.AuthorRepo;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthorRepoUnitTest {
    @InjectMocks
    private AuthorRepo authorRepo = mock(AuthorRepo.class);

    @Test
    @Order(0)
    public void testFindById(){
        Optional<Author> author = Optional.of(new Author());
        author.get().setId(1);
        author.get().setName("Name1");
        author.get().setSurname("Surname1");
        author.get().setPatronymic("Patronymic1");
        author.get().setDateBirth(new Date(24,0,29));
        author.get().setBooks(new ArrayList<>());

        when(authorRepo.findById(1)).thenReturn(author);
        Optional<Author> resultAuthor = authorRepo.findById(1);

        assertEquals(author.get().getId(), resultAuthor.get().getId());
        verify(authorRepo, times(1)).findById(1);
    }

    @Test
    @Order(1)
    public void testFindAll(){
        Author author = new Author();
        author.setId(1);
        author.setName("Name1");
        author.setSurname("Surname1");
        author.setPatronymic("Patronymic1");
        author.setDateBirth(new Date(24,0,29));
        author.setBooks(new ArrayList<>());
        List<Author> authorList = Arrays.asList(author);

        when(authorRepo.findAll()).thenReturn(Arrays.asList(author));
        List<Author> resultAuthorList = authorRepo.findAll();

        assertEquals(authorList.size(), resultAuthorList.size());
        verify(authorRepo, times(1)).findAll();
    }

    @Test
    @Order(2)
    public void testDeleteById(){
        int id = 1;

        authorRepo.deleteById(1);

        verify(authorRepo, times(1)).deleteById(id);
    }

    @Test
    @Order(3)
    public void testSave(){
        Author author = new Author();
        author.setName("Name1");
        author.setSurname("Surname1");
        author.setPatronymic("Patronymic1");
        author.setDateBirth(new Date(24,0,29));
        author.setBooks(new ArrayList<>());

        when(authorRepo.save(author)).thenReturn(author);
        Author savedAuthor = authorRepo.save(author);

        assertEquals(author.getName(), savedAuthor.getName());
        assertEquals(author.getSurname(), savedAuthor.getSurname());
        assertEquals(author.getPatronymic(), savedAuthor.getPatronymic());
        assertEquals(author.getDateBirth(), savedAuthor.getDateBirth());
        verify(authorRepo, times(1)).save(author);
    }

    @Test
    @Order(4)
    public void testUpdate(){
        Author author = new Author();
        int id = 1;
        author.setId(id);
        author.setName("Name1");
        author.setSurname("Surname1");
        author.setPatronymic("Patronymic1");
        author.setDateBirth(new Date(24,0,29));
        author.setBooks(new ArrayList<>());

        authorRepo.updateAuthor(author.getName(), author.getSurname(), author.getPatronymic(), author.getDateBirth(), id);
        when(authorRepo.findById(1)).thenReturn(Optional.of(author));
        Author updatedAuthor = authorRepo.findById(1).get();

        assertEquals(author.getId(), updatedAuthor.getId());
        assertEquals(author.getName(), updatedAuthor.getName());
        assertEquals(author.getSurname(), updatedAuthor.getSurname());
        assertEquals(author.getPatronymic(), updatedAuthor.getPatronymic());
        assertEquals(author.getDateBirth(), updatedAuthor.getDateBirth());
        verify(authorRepo, times(1)).updateAuthor(author.getName(), author.getSurname(), author.getPatronymic(), author.getDateBirth(), id);
    }
}
