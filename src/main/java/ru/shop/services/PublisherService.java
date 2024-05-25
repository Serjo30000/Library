package ru.shop.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.shop.exceptions.PublisherNotFoundException;
import ru.shop.models.Book;
import ru.shop.models.Grade;
import ru.shop.models.Publisher;
import ru.shop.models.Rental;
import ru.shop.repositories.BookRepo;
import ru.shop.repositories.GradeRepo;
import ru.shop.repositories.PublisherRepo;
import ru.shop.repositories.RentalRepo;

import java.io.File;
import java.util.List;

@Service
@AllArgsConstructor
public class PublisherService {
    private final PublisherRepo publisherRepo;
    private final BookRepo bookRepo;
    private final RentalRepo rentalRepo;
    private final GradeRepo gradeRepo;

    public List<Publisher> getAllPublisher(){
        return publisherRepo.findAll();
    }

    public Publisher getByIdPublisher(int id){
        return publisherRepo.findById(id).orElseThrow(PublisherNotFoundException::new);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public int deletePublisher(int id){
        if (publisherRepo.findById(id).isPresent()) {
            for (Book b: publisherRepo.findById(id).get().getBooks()){
                for (Rental rent: b.getRentals()){
                    rentalRepo.deleteById(rent.getId());
                }
                for (Grade gr: b.getGrades()){
                    gradeRepo.deleteById(gr.getId());
                }
                String currentDir = System.getProperty("user.dir");
                File currentDirectory = new File(currentDir);
                String uploadDir = (new File(currentDirectory.getAbsolutePath() + "/client/src/assets").getAbsolutePath()+"\\"+b.getImageBook());
                File imageFile = new File(uploadDir);
                if (imageFile.exists()) {
                    imageFile.delete();
                }
                bookRepo.deleteById(b.getId());
            }
            publisherRepo.deleteById(id);
            return id;
        }
        return 0;
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public int savePublisher(Publisher p){
        for (Publisher publish: publisherRepo.findAll()){
            if (publish.getNamePublisher().equals(p.getNamePublisher())){
                return 0;
            }
        }
        return publisherRepo.save(p).getId();
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public int updatePublisher(int id, Publisher publisher){
        if (publisherRepo.findById(id).isPresent()) {
            for (Publisher publish: publisherRepo.findAll()){
                if (publish.getNamePublisher().equals(publisher.getNamePublisher()) && publish.getId()!=id){
                    return 0;
                }
            }
            publisherRepo.updatePublisher(publisher.getNamePublisher(),publisher.getAddress(),id);
            return id;
        }
        return 0;
    }
}
