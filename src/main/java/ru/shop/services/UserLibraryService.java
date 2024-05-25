package ru.shop.services;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.shop.models.*;
import ru.shop.repositories.GradeRepo;
import ru.shop.repositories.RentalRepo;
import ru.shop.repositories.RoleRepo;
import ru.shop.repositories.UserLibraryRepo;
import ru.shop.exceptions.UserLibraryNotFoundException;
import ru.shop.exceptions.RoleNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.transaction.Transactional;

@Repository
@AllArgsConstructor
public class UserLibraryService {
    private final UserLibraryRepo userLibraryRepo;
    private final RoleRepo pRepository;
    private final PasswordEncoder passwordEncoder;
    private final RentalRepo rentalRepo;
    private final GradeRepo gradeRepo;

    public List<UserLibrary> getAllUserLibrary(){
        return userLibraryRepo.findAll();
    }

    public List<UserLibrary> getAllUserLibraryByRoleId(int role){
        return userLibraryRepo.findByRoleId(role);
    }

    public UserLibrary getByIdUserLibrary(int id){
        return userLibraryRepo.findById(id)
                .orElseThrow(UserLibraryNotFoundException::new);
    }

    @Transactional
    public int saveUserLibrary(UserLibrary a){
        var r = pRepository.findByNameRole("ROLE_USER")
                .orElse(new Role("ROLE_USER"));
        a.setRole(r);
        var us = Optional.ofNullable(r.getUserLibraries());
        var l = us.orElse(new ArrayList<>());
        l.add(a);
        r.setUserLibraries(l);
        a.setPassword(passwordEncoder.encode(a.getPassword()));

        Pattern pattern1 = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        Matcher matcher1 = pattern1.matcher(a.getEmail());
        Pattern pattern2 = Pattern.compile("^[+]?\\d{11}$");
        Matcher matcher2 = pattern2.matcher(a.getPhone());
        if (a.getPhone().equals("70000000000") || a.getEmail().equals("Admin@admin.ru") || a.getLogin().equals("DefaultAdmin")){
            return 0;
        }
        if (!matcher1.matches() || !matcher2.matches()){
            return 0;
        }
        for (UserLibrary usr: userLibraryRepo.findAll()){
            if (a.getLogin().equals(usr.getLogin()) || a.getPhone().equals(usr.getPhone()) || a.getEmail().equals(usr.getEmail())){
                return 0;
            }
        }
        return userLibraryRepo.save(a).getId();
    }

    @Transactional
    public int createDefaultAdmin(){
        UserLibrary a = new UserLibrary();
        var r = pRepository.findByNameRole("ROLE_ADMIN")
                .orElse(new Role("ROLE_ADMIN"));
        a.setRole(r);
        var us = Optional.ofNullable(r.getUserLibraries());
        var l = us.orElse(new ArrayList<>());
        l.add(a);
        r.setUserLibraries(l);
        a.setPassword("1");
        a.setName("DefaultName");
        a.setSurname("DefaultSurname");
        a.setPatronymic("DefaultPatronymic");
        a.setPhone("70000000000");
        a.setEmail("Admin@admin.ru");
        a.setLogin("DefaultAdmin");
        a.setPassword(passwordEncoder.encode(a.getPassword()));
        if (!userLibraryRepo.findByLogin(a.getLogin()).isPresent()) {
            return userLibraryRepo.save(a).getId();
        }
        return 0;
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public int updateRoleUser(int id){
        var r = pRepository.findByNameRole("ROLE_ADMIN").orElse(new Role("ROLE_ADMIN"));
        userLibraryRepo.updateRoleUser(r, id);
        return id;
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public String updateRoleUserByLogin(String login){
        var r = pRepository.findByNameRole("ROLE_ADMIN").orElse(new Role("ROLE_ADMIN"));
        userLibraryRepo.updateRoleUserByLogin(r, login);
        return login;
    }

    public UserLibrary getByLogin(String login){
        return userLibraryRepo.findByLogin(login)
                .orElseThrow(UserLibraryNotFoundException::new);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public int deleteUserLibrary(int id){
        if (userLibraryRepo.findById(id).isPresent()) {
            for (Rental rent: userLibraryRepo.findById(id).get().getRentals()){
                rentalRepo.deleteById(rent.getId());
            }
            for (Grade gr: userLibraryRepo.findById(id).get().getGrades()){
                gradeRepo.deleteById(gr.getId());
            }
            userLibraryRepo.deleteById(id);
            return id;
        }
        return 0;
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUserLibraryByLogin(String login){
        if (userLibraryRepo.findByLogin(login).isPresent()) {
            for (Rental rent: userLibraryRepo.findByLogin(login).get().getRentals()){
                rentalRepo.deleteById(rent.getId());
            }
            for (Grade gr: userLibraryRepo.findByLogin(login).get().getGrades()){
                gradeRepo.deleteById(gr.getId());
            }
            userLibraryRepo.deleteUserByLogin(login);
            return login;
        }
        return "";
    }
}
