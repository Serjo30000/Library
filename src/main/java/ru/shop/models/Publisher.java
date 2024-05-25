package ru.shop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name_publisher", unique=true, nullable = false)
    private String namePublisher;

    @Column(name="address", nullable = false)
    private String address;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "publisher")
    private List<Book> books;
}
