package dev.rinat.SpringBootApp2.services;

import dev.rinat.SpringBootApp2.models.Book;
import dev.rinat.SpringBootApp2.models.Person;
import dev.rinat.SpringBootApp2.repositories.PersonsRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonsService {
    private final PersonsRepository personsRepository;

    @Autowired
    public PersonsService(PersonsRepository personsRepository) {
        this.personsRepository = personsRepository;
    }

    public List<Person> findAll() {
        return personsRepository.findAll();
    }

    public Person findById(int id) {
        Optional<Person> person = personsRepository.findById(id);
        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());
            List<Book> books = person.get().getBooks();
            for (Book book : books) {
                Instant takenAtInstant = book.getTakenAt().toInstant();
                Instant nowInstant = Instant.now();
                long daysDiff = ChronoUnit.DAYS.between(takenAtInstant, nowInstant);
                if (daysDiff > 10) {
                    book.setExpired(true);
                }
            }
            return person.get();
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Transactional
    public void save(Person person) {
        personsRepository.save(person);
    }

    @Transactional
    public void update(int id, Person person) {
        person.setId(id);
        personsRepository.save(person);
    }

    @Transactional
    public void delete(int id) {
        personsRepository.deleteById(id);
    }

    public List<Book> getBooksByPersonId(int id) {
        return findById(id).getBooks();
    }

    public List<Person> findByFullName(String fullName) {
        return personsRepository.findByFullName(fullName);
    }
}
