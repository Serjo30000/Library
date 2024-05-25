package ru.shop.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.shop.exceptions.GenreNotFoundException;
import ru.shop.models.Book;
import ru.shop.models.Genre;
import ru.shop.models.Grade;
import ru.shop.models.Rental;
import ru.shop.repositories.*;

import java.io.File;
import java.util.List;

@Service
@AllArgsConstructor
public class GenreService {
    private final GenreRepo genreRepo;
    private final BookRepo bookRepo;
    private final RentalRepo rentalRepo;
    private final GradeRepo gradeRepo;

    public List<Genre> getAllGenre(){
        return genreRepo.findAll();
    }

    public Genre getByIdGenre(int id){
        return genreRepo.findById(id).orElseThrow(GenreNotFoundException::new);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public int deleteGenre(int id){
        if (genreRepo.findById(id).isPresent()) {
            for (Book b: genreRepo.findById(id).get().getBooks()){
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
            genreRepo.deleteById(id);
            return id;
        }
        return 0;
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public int saveGenre(Genre g){
        for (Genre gnr: genreRepo.findAll()){
            if (gnr.getNameGenre().equals(g.getNameGenre())){
                return 0;
            }
        }
        return genreRepo.save(g).getId();
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public int updateGenre(int id, Genre genre){
        if (genreRepo.findById(id).isPresent()) {
            for (Genre gnr: genreRepo.findAll()){
                if (gnr.getNameGenre().equals(genre.getNameGenre()) && gnr.getId()!=id){
                    return 0;
                }
            }
            genreRepo.updateGenre(genre.getNameGenre(),id);
            return id;
        }
        return 0;
    }
}
