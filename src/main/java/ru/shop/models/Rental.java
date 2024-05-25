package ru.shop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Table
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="count_book", nullable = false)
    private int countBook;

    @Column(name="date_start", nullable = false)
    private Date dateStart;

    @Column(name="date_end", nullable = false)
    private Date dateEnd;


    @ManyToOne
    @JoinColumn(name = "bookId", referencedColumnName = "id")
    private Book book;


    @ManyToOne
    @JoinColumn(name = "userLibraryId", referencedColumnName = "id")
    private UserLibrary userLibrary;
}
