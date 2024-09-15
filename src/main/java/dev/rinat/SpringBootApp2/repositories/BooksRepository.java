package dev.rinat.SpringBootApp2.repositories;

import dev.rinat.SpringBootApp2.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByTitleContainingIgnoreCase(String query);
}
