package ru.shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shop.dto.UserLibraryDto;
import ru.shop.dto.UserLibraryDtoOut;
import ru.shop.mapers.UserLibraryMapper;
import ru.shop.models.Book;
import ru.shop.models.UserLibrary;
import ru.shop.services.UserLibraryService;

import java.util.List;

@RestController
@RequestMapping("/api/userLibraries")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*", methods = {RequestMethod.GET,  RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class UserLibraryController {
    private final UserLibraryService aService;
    private final UserLibraryMapper mapper;

    @PutMapping("/save")
    public ResponseEntity<Integer> saveUserLibraries(@RequestBody UserLibraryDto aDto){
        var a = mapper.toEntity(aDto);
        return ResponseEntity.ok().body(aService.saveUserLibrary(a));

    }

    @PutMapping("/createDefaultAdmin")
    public ResponseEntity<Integer> createDefaultAdmins(){
        return ResponseEntity.ok().body(aService.createDefaultAdmin());

    }

    @GetMapping
    public ResponseEntity<List<UserLibraryDtoOut>> getAllUserLibraries(){
        return ResponseEntity.ok().body(aService.getAllUserLibrary().stream().map(mapper::toDto).toList());
    }

    @GetMapping("/user/{login}")
    public ResponseEntity<UserLibrary> getByLoginUserLibrary(@PathVariable("login") String login){
        return ResponseEntity.ok().body(aService.getByLogin(login));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserLibraryDtoOut>> getAllUserLibraryByRoleId(@PathVariable("role") int role){
        return ResponseEntity.ok().body(aService.getAllUserLibraryByRoleId(role).stream().map(mapper::toDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserLibraryDtoOut> getByIdUserLibrary(@PathVariable("id") int id){
        return ResponseEntity.ok().body(mapper.toDto(aService.getByIdUserLibrary(id)));
    }

    @GetMapping("/{id}/updateRole")
    public ResponseEntity<Integer> updateRole(@PathVariable("id") int id){
        return ResponseEntity.ok().body(aService.updateRoleUser(id));
    }

    @GetMapping("/{login}/updateRoleByLogin")
    public ResponseEntity<String> updateRoleByLogin(@PathVariable("login") String login){
        return ResponseEntity.ok().body(aService.updateRoleUserByLogin(login));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteUserLibrary(@PathVariable("id") int id){
        return ResponseEntity.ok().body(aService.deleteUserLibrary(id));
    }

    @DeleteMapping("/delete/{login}")
    public ResponseEntity<String> deleteUserLibraryByLogin(@PathVariable("login") String login){
        return ResponseEntity.ok().body(aService.deleteUserLibraryByLogin(login));
    }
}
