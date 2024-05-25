package ru.shop.unit;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import ru.shop.models.Publisher;
import ru.shop.repositories.PublisherRepo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PublisherRepoUnitTest {
    @InjectMocks
    private PublisherRepo publisherRepo = mock(PublisherRepo.class);

    @Test
    @Order(0)
    public void testFindById(){
        Optional<Publisher> publisher = Optional.of(new Publisher());
        publisher.get().setId(1);
        publisher.get().setNamePublisher("NamePublisher1");
        publisher.get().setAddress("Address1");
        publisher.get().setBooks(new ArrayList<>());

        when(publisherRepo.findById(1)).thenReturn(publisher);
        Optional<Publisher> resultPublisher = publisherRepo.findById(1);

        assertEquals(publisher.get().getId(), resultPublisher.get().getId());
        verify(publisherRepo, times(1)).findById(1);
    }

    @Test
    @Order(1)
    public void testFindAll(){
        Publisher publisher = new Publisher();
        publisher.setId(1);
        publisher.setNamePublisher("NamePublisher1");
        publisher.setAddress("Address1");
        publisher.setBooks(new ArrayList<>());
        List<Publisher> publisherList = Arrays.asList(publisher);

        when(publisherRepo.findAll()).thenReturn(Arrays.asList(publisher));
        List<Publisher> resultPublisherList = publisherRepo.findAll();

        assertEquals(publisherList.size(), resultPublisherList.size());
        verify(publisherRepo, times(1)).findAll();
    }

    @Test
    @Order(2)
    public void testDeleteById(){
        int id = 1;

        publisherRepo.deleteById(1);

        verify(publisherRepo, times(1)).deleteById(id);
    }

    @Test
    @Order(3)
    public void testSave(){
        Publisher publisher = new Publisher();
        publisher.setNamePublisher("NamePublisher1");
        publisher.setAddress("Address1");
        publisher.setBooks(new ArrayList<>());

        when(publisherRepo.save(publisher)).thenReturn(publisher);
        Publisher savedPublisher = publisherRepo.save(publisher);

        assertEquals(publisher.getNamePublisher(), savedPublisher.getNamePublisher());
        assertEquals(publisher.getAddress(), savedPublisher.getAddress());
        verify(publisherRepo, times(1)).save(publisher);
    }

    @Test
    @Order(4)
    public void testUpdate(){
        Publisher publisher = new Publisher();
        int id = 1;
        publisher.setId(id);
        publisher.setNamePublisher("NamePublisher1");
        publisher.setAddress("Address1");
        publisher.setBooks(new ArrayList<>());

        publisherRepo.updatePublisher(publisher.getNamePublisher(), publisher.getAddress(), id);
        when(publisherRepo.findById(1)).thenReturn(Optional.of(publisher));
        Publisher updatedPublisher = publisherRepo.findById(1).get();

        assertEquals(publisher.getId(), updatedPublisher.getId());
        assertEquals(publisher.getNamePublisher(), updatedPublisher.getNamePublisher());
        assertEquals(publisher.getAddress(), updatedPublisher.getAddress());
        verify(publisherRepo, times(1)).updatePublisher(publisher.getNamePublisher(), publisher.getAddress(), id);
    }
}
