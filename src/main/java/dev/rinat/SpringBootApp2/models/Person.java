package dev.rinat.SpringBootApp2.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "person")
@Getter
@Setter
@NoArgsConstructor
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_name")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 8, max = 50, message = "Name should be between 8 and 50 characters")
    private String fullName;

    @Column(name = "birth_year")
    @Min(value = 1900, message = "Birth year should be greater than 1900")
    private int birthYear;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Book> books;

    public Person(String fullName, int birthYear) {
        this.fullName = fullName;
        this.birthYear = birthYear;
    }

}
