package ru.shop.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.shop.exceptions.AuthorNotFoundException;
import ru.shop.models.Author;
import ru.shop.models.Book;
import ru.shop.models.Grade;
import ru.shop.models.Rental;
import ru.shop.repositories.AuthorRepo;
import ru.shop.repositories.BookRepo;
import ru.shop.repositories.GradeRepo;
import ru.shop.repositories.RentalRepo;

import java.io.File;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthorService {
    private final AuthorRepo authorRepo;
    private final BookRepo bookRepo;
    private final RentalRepo rentalRepo;
    private final GradeRepo gradeRepo;

    public List<Author> getAllAuthor(){
        return authorRepo.findAll();
    }

    public Author getByIdAuthor(int id){
        return authorRepo.findById(id).orElseThrow(AuthorNotFoundException::new);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public int deleteAuthor(int id){
        if (authorRepo.findById(id).isPresent()) {
            for (Book b: authorRepo.findById(id).get().getBooks()){
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
            authorRepo.deleteById(id);
            return id;
        }
        return 0;
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public int saveAuthor(Author a){
        return authorRepo.save(a).getId();
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public int updateAuthor(int id, Author author){
        if (authorRepo.findById(id).isPresent()) {
            authorRepo.updateAuthor(author.getName(),author.getSurname(),author.getPatronymic(),author.getDateBirth(),id);
            return id;
        }
        return 0;
    }
}
