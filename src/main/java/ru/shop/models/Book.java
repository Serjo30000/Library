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
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name_product", nullable = false, unique=true)
    private String nameBook;

    @Column(name="date_publication", nullable = false)
    private Date datePublication;

    @Column(name="count_book", nullable = false)
    private int countBook;

    @Column(name="price", nullable = false)
    private double price;

    @Column(name="image_book", unique=true, nullable = false)
    private String imageBook;


    @ManyToOne
    @JoinColumn(name = "genreId", referencedColumnName = "id")
    private Genre genre;


    @ManyToOne
    @JoinColumn(name = "authorId", referencedColumnName = "id")
    private Author author;


    @ManyToOne
    @JoinColumn(name = "publisherId", referencedColumnName = "id")
    private Publisher publisher;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "book")
    private List<Rental> rentals;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "book")
    private List<Grade> grades;
}
