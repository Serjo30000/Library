package ru.shop.mapers;

import org.springframework.stereotype.Component;
import ru.shop.dto.UserLibraryDto;
import ru.shop.dto.UserLibraryDtoOut;
import ru.shop.models.UserLibrary;

@Component
public class UserLibraryMapper {
    public UserLibraryDtoOut toDto(UserLibrary aDto){
        var a = new UserLibraryDtoOut();
        a.setName(aDto.getName());
        a.setSurname(aDto.getSurname());
        a.setPatronymic(aDto.getPatronymic());
        a.setPhone(aDto.getPhone());
        a.setEmail(aDto.getEmail());
        a.setLogin(aDto.getLogin());
        a.setRole(aDto.getRole().getNameRole());
        return a;
    }

    public UserLibrary toEntity(UserLibraryDto aDto){
        var a = new UserLibrary();
        a.setName(aDto.getName());
        a.setSurname(aDto.getSurname());
        a.setPatronymic(aDto.getPatronymic());
        a.setPhone(aDto.getPhone());
        a.setEmail(aDto.getEmail());
        a.setLogin(aDto.getLogin());
        a.setPassword(aDto.getPassword());
        return a;
    }
}
