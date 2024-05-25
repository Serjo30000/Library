package ru.shop.unit;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import ru.shop.models.*;
import ru.shop.repositories.GradeRepo;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GradeRepoUnitTest {
    @InjectMocks
    private GradeRepo gradeRepo = mock(GradeRepo.class);

    @Test
    @Order(0)
    public void testFindById(){
        Optional<Grade> grade = Optional.of(new Grade());
        grade.get().setId(1);
        grade.get().setRating(5);
        grade.get().setComment("Comment1");
        grade.get().setUserLibrary(new UserLibrary());
        grade.get().setBook(new Book());

        when(gradeRepo.findById(1)).thenReturn(grade);
        Optional<Grade> resultGrade = gradeRepo.findById(1);

        assertEquals(grade.get().getId(), resultGrade.get().getId());
        verify(gradeRepo, times(1)).findById(1);
    }

    @Test
    @Order(1)
    public void testFindAll(){
        Grade grade = new Grade();
        grade.setId(1);
        grade.setRating(5);
        grade.setComment("Comment1");
        grade.setUserLibrary(new UserLibrary());
        grade.setBook(new Book());
        List<Grade> gradeList = Arrays.asList(grade);

        when(gradeRepo.findAll()).thenReturn(Arrays.asList(grade));
        List<Grade> resultGradeList = gradeRepo.findAll();

        assertEquals(gradeList.size(), resultGradeList.size());
        verify(gradeRepo, times(1)).findAll();
    }

    @Test
    @Order(2)
    public void testDeleteById(){
        int id = 1;

        gradeRepo.deleteById(1);

        verify(gradeRepo, times(1)).deleteById(id);
    }

    @Test
    @Order(3)
    public void testSave(){
        Grade grade = new Grade();
        grade.setRating(5);
        grade.setComment("Comment1");
        grade.setUserLibrary(new UserLibrary());
        grade.setBook(new Book());

        when(gradeRepo.save(grade)).thenReturn(grade);
        Grade savedGrade = gradeRepo.save(grade);

        assertEquals(grade.getRating(), savedGrade.getRating());
        assertEquals(grade.getComment(), savedGrade.getComment());
        verify(gradeRepo, times(1)).save(grade);
    }

    @Test
    @Order(4)
    public void testFindByBookId(){
        Optional<Grade> grade = Optional.of(new Grade());
        grade.get().setId(1);
        grade.get().setRating(5);
        grade.get().setComment("Comment1");
        grade.get().setUserLibrary(new UserLibrary());
        int bookId =1;
        Book book = new Book();
        book.setId(bookId);
        book.setNameBook("NameBook1");
        book.setDatePublication(new Date(24,0,31));
        book.setCountBook(5);
        book.setPrice(1000);
        book.setImageBook("default1.png");
        book.setAuthor(new Author());
        book.setGenre(new Genre());
        book.setPublisher(new Publisher());
        book.setGrades(Arrays.asList(grade.get()));
        book.setRentals(new ArrayList<>());
        grade.get().setBook(book);

        when(gradeRepo.findByBookId(bookId)).thenReturn(book.getGrades());
        List<Grade> resultGradeList = gradeRepo.findByBookId(bookId);

        assertEquals(book.getGrades().size(), resultGradeList.size());
        verify(gradeRepo, times(1)).findByBookId(bookId);
    }

    @Test
    @Order(5)
    public void testFindByUserLibraryId(){
        Optional<Grade> grade = Optional.of(new Grade());
        grade.get().setId(1);
        grade.get().setRating(5);
        grade.get().setComment("Comment1");
        UserLibrary userLibrary = new UserLibrary();
        int userLibraryId = 1;
        userLibrary.setId(userLibraryId);
        userLibrary.setName("Name2");
        userLibrary.setSurname("Surname2");
        userLibrary.setPatronymic("Patronymic2");
        userLibrary.setPhone("70000000002");
        userLibrary.setEmail("Admin2@admin.ru");
        userLibrary.setLogin("Login2");
        userLibrary.setPassword("2");
        userLibrary.setRole(new Role());
        userLibrary.setRentals(new ArrayList<>());
        userLibrary.setGrades(Arrays.asList(grade.get()));
        grade.get().setUserLibrary(userLibrary);
        grade.get().setBook(new Book());

        when(gradeRepo.findByUserLibraryId(userLibraryId)).thenReturn(userLibrary.getGrades());
        List<Grade> resultUserLibraryList = gradeRepo.findByUserLibraryId(userLibraryId);

        assertEquals(userLibrary.getGrades().size(), resultUserLibraryList.size());
        verify(gradeRepo, times(1)).findByUserLibraryId(userLibraryId);
    }
}
