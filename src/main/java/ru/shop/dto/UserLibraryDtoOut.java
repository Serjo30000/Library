package ru.shop.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import ru.shop.models.Grade;
import ru.shop.models.Rental;
import ru.shop.models.Role;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLibraryDtoOut {
    private String name;

    private String surname;

    private String patronymic;

    private String phone;

    private String email;

    private String login;

    private String role;
}
