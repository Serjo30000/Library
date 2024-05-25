package ru.shop.unit;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import ru.shop.models.*;
import ru.shop.repositories.RentalRepo;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
public class RentalRepoUnitTest {
    @InjectMocks
    private RentalRepo rentalRepo = mock(RentalRepo.class);

    @Test
    @Order(0)
    public void testFindById(){
        Optional<Rental> rental = Optional.of(new Rental());
        rental.get().setId(1);
        rental.get().setCountBook(3);
        rental.get().setDateStart(new Date(24,1,29));
        rental.get().setDateEnd(new Date(24,2,29));
        rental.get().setBook(new Book());
        rental.get().setUserLibrary(new UserLibrary());

        when(rentalRepo.findById(1)).thenReturn(rental);
        Optional<Rental> resultRental = rentalRepo.findById(1);

        assertEquals(rental.get().getId(), resultRental.get().getId());
        verify(rentalRepo, times(1)).findById(1);
    }

    @Test
    @Order(1)
    public void testFindAll(){
        Rental rental = new Rental();
        rental.setId(1);
        rental.setCountBook(3);
        rental.setDateStart(new Date(24,1,29));
        rental.setDateEnd(new Date(24,2,29));
        rental.setBook(new Book());
        rental.setUserLibrary(new UserLibrary());
        List<Rental> rentalList = Arrays.asList(rental);

        when(rentalRepo.findAll()).thenReturn(Arrays.asList(rental));
        List<Rental> resultRentalList = rentalRepo.findAll();

        assertEquals(rentalList.size(), resultRentalList.size());
        verify(rentalRepo, times(1)).findAll();
    }

    @Test
    @Order(2)
    public void testDeleteById(){
        int id = 1;

        rentalRepo.deleteById(1);

        verify(rentalRepo, times(1)).deleteById(id);
    }

    @Test
    @Order(3)
    public void testSave(){
        Rental rental = new Rental();
        rental.setCountBook(3);
        rental.setDateStart(new Date(24,1,29));
        rental.setDateEnd(new Date(24,2,29));
        rental.setBook(new Book());
        rental.setUserLibrary(new UserLibrary());

        when(rentalRepo.save(rental)).thenReturn(rental);
        Rental savedRental = rentalRepo.save(rental);

        assertEquals(rental.getCountBook(), savedRental.getCountBook());
        assertEquals(rental.getDateStart(), savedRental.getDateStart());
        assertEquals(rental.getDateEnd(), savedRental.getDateEnd());
        verify(rentalRepo, times(1)).save(rental);
    }

    @Test
    @Order(4)
    public void testFindByBookId(){
        Optional<Rental> rental = Optional.of(new Rental());
        rental.get().setId(1);
        rental.get().setCountBook(3);
        rental.get().setDateStart(new Date(24,1,29));
        rental.get().setDateEnd(new Date(24,2,29));
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
        book.setGrades(new ArrayList<>());
        book.setRentals(Arrays.asList(rental.get()));
        rental.get().setBook(book);
        rental.get().setUserLibrary(new UserLibrary());

        when(rentalRepo.findByBookId(bookId)).thenReturn(book.getRentals());
        List<Rental> resultBookList = rentalRepo.findByBookId(bookId);

        assertEquals(book.getRentals().size(), resultBookList.size());
        verify(rentalRepo, times(1)).findByBookId(bookId);
    }

    @Test
    @Order(5)
    public void testFindByUserLibraryId(){
        Optional<Rental> rental = Optional.of(new Rental());
        rental.get().setId(1);
        rental.get().setCountBook(3);
        rental.get().setDateStart(new Date(24,1,29));
        rental.get().setDateEnd(new Date(24,2,29));
        rental.get().setBook(new Book());
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
        userLibrary.setGrades(new ArrayList<>());
        userLibrary.setRentals(Arrays.asList(rental.get()));
        rental.get().setUserLibrary(userLibrary);

        when(rentalRepo.findByUserLibraryId(userLibraryId)).thenReturn(userLibrary.getRentals());
        List<Rental> resultUserLibraryList = rentalRepo.findByUserLibraryId(userLibraryId);

        assertEquals(userLibrary.getRentals().size(), resultUserLibraryList.size());
        verify(rentalRepo, times(1)).findByUserLibraryId(userLibraryId);
    }

    @Test
    @Order(6)
    public void testUpdate(){
        Rental rental = new Rental();
        int id = 1;
        rental.setId(id);
        rental.setCountBook(3);
        rental.setDateStart(new Date(24,1,29));
        rental.setDateEnd(new Date(24,2,29));
        rental.setBook(new Book());
        rental.setUserLibrary(new UserLibrary());

        rentalRepo.updateRental(rental.getCountBook(), rental.getDateStart(), rental.getDateEnd(), rental.getBook(), rental.getUserLibrary(), id);
        when(rentalRepo.findById(1)).thenReturn(Optional.of(rental));
        Rental updatedRental = rentalRepo.findById(1).get();

        assertEquals(rental.getId(), updatedRental.getId());
        assertEquals(rental.getCountBook(), updatedRental.getCountBook());
        assertEquals(rental.getDateStart(), updatedRental.getDateStart());
        assertEquals(rental.getDateEnd(), updatedRental.getDateEnd());
        verify(rentalRepo, times(1)).updateRental(rental.getCountBook(), rental.getDateStart(), rental.getDateEnd(), rental.getBook(), rental.getUserLibrary(), id);
    }
}
