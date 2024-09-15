package dev.rinat.SpringBootApp2.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    @NotEmpty(message = "Title should not be empty")
    @Size(min = 2, max = 30, message = "Title should be between 2 and 30 characters")
    private String title;

    @Column(name = "author")
    @NotEmpty(message = "Author name should not be empty")
    @Size(min = 2, max = 30, message = "Author name should be between 2 and 30 characters")
    private String author;

    @Column(name = "year")
    @Min(value = 0, message = "Year should be greater than 0")
    private int year;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;
    @Transient
    private boolean expired;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

}
