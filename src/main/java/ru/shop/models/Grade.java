package ru.shop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="rating", nullable = false)
    private int rating;

    @Column(name="comment")
    private String comment;


    @ManyToOne
    @JoinColumn(name = "bookId", referencedColumnName = "id")
    private Book book;


    @ManyToOne
    @JoinColumn(name = "userLibraryId", referencedColumnName = "id")
    private UserLibrary userLibrary;
}
