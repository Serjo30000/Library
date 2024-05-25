package ru.shop.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.shop.exceptions.GradeNotFoundException;
import ru.shop.models.Book;
import ru.shop.models.Grade;
import ru.shop.models.Rental;
import ru.shop.repositories.BookRepo;
import ru.shop.repositories.GradeRepo;
import ru.shop.repositories.UserLibraryRepo;

import java.util.List;

@Service
@AllArgsConstructor
public class GradeService {
    private final GradeRepo gradeRepo;
    private final BookRepo bookRepo;
    private final UserLibraryRepo userLibraryRepo;

    public List<Grade> getAllGrade(){
        return gradeRepo.findAll();
    }

    public List<Grade> getAllGradeByBookId(int book){
        return gradeRepo.findByBookId(book);
    }

    public List<Grade> getAllGradeByUserLibraryId(int userLibrary){
        return gradeRepo.findByUserLibraryId(userLibrary);
    }

    public Grade getByIdGrade(int id){
        return gradeRepo.findById(id).orElseThrow(GradeNotFoundException::new);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public int deleteGrade(int id){
        if (gradeRepo.findById(id).isPresent()) {
            gradeRepo.deleteById(id);
            return id;
        }
        return 0;
    }

    @Transactional
    @PreAuthorize("hasRole('USER')")
    public int saveGrade(Grade g, int bookId,String login){
        if (g.getRating()<1 || g.getRating()>5){
            return 0;
        }
        g.setBook(bookRepo.findById(bookId).get());
        g.setUserLibrary(userLibraryRepo.findByLogin(login).get());
        return gradeRepo.save(g).getId();
    }
}
