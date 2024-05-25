package ru.shop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.List;

@Table
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserLibrary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="surname", nullable = false)
    private String surname;

    @Column(name="patronymic")
    private String patronymic;

    @Column(name="phone", nullable = false, unique=true)
    private String phone;

    @Column(name="email", nullable = false, unique=true)
    private String email;

    @Column(name="login", nullable = false, unique=true)
    private String login;

    @Column(name="password", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "id")
    @Cascade(CascadeType.PERSIST)
    private Role role;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userLibrary")
    private List<Rental> rentals;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userLibrary")
    private List<Grade> grades;
}
