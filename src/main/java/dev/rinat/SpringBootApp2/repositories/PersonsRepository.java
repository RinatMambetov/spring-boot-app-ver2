package dev.rinat.SpringBootApp2.repositories;

import dev.rinat.SpringBootApp2.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonsRepository extends JpaRepository<Person, Integer> {
    List<Person> findByFullName(String fullName);
}
