package dev.rinat.SpringBootApp2.services;

import dev.rinat.SpringBootApp2.models.Book;
import dev.rinat.SpringBootApp2.models.Person;
import dev.rinat.SpringBootApp2.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(Boolean sortByYear) {
        if (sortByYear != null) {
            return booksRepository.findAll(Sort.by("year"));
        } else {
            return booksRepository.findAll();
        }
    }

    public List<Book> findPage(Integer page, Integer booksPerPage, Boolean sortByYear) {
        if (page == null || booksPerPage == null) {
            return findAll(sortByYear);
        } else if (sortByYear != null) {
            return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        } else {
            return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
        }
    }

    public Book findById(int id) {
        Optional<Book> book = booksRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book) {
        Book oldBook = findById(id);
        Person person = oldBook.getPerson();
        book.setId(id);
        book.setPerson(person);
        book.setTakenAt(oldBook.getTakenAt());
        book.setExpired(oldBook.isExpired());
        booksRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    public Optional<Person> getPerson(int id) {
        return Optional.ofNullable(findById(id).getPerson());
    }

    @Transactional
    public void release(int id) {
        Book book = findById(id);
        Person person = book.getPerson();
        person.getBooks().remove(book);
        book.setPerson(null);
        book.setExpired(false);
        book.setTakenAt(null);
        booksRepository.save(book);
    }

    @Transactional
    public void assign(int id, Person person) {
        Book book = findById(id);
        book.setPerson(person);
        book.setTakenAt(new Date());
        List<Book> books = person.getBooks();
        if (books == null) {
            books = new ArrayList<>();
        }
        books.add(book);
        update(id, book);
    }

    public List<Book> searchByTitle(String query) {
        return booksRepository.findByTitleContainingIgnoreCase(query);
    }
}
