package ru.shop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Table
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="surname", nullable = false)
    private String surname;

    @Column(name="patronymic")
    private String patronymic;

    @Column(name="date_birth", nullable = false)
    private Date dateBirth;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "author")
    private List<Book> books;
}
