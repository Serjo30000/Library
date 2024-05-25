package ru.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLibraryDto {
    private String name;

    private String surname;

    private String patronymic;

    private String phone;

    private String email;

    private String login;

    private String password;
}
