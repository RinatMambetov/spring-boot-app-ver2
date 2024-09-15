package dev.rinat.SpringBootApp2.util;

import dev.rinat.SpringBootApp2.models.Person;
import dev.rinat.SpringBootApp2.services.PersonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PersonsService personsService;

    @Autowired
    public PersonValidator(PersonsService personsService) {
        this.personsService = personsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (!personsService.findByFullName(person.getFullName()).isEmpty()) {
            errors.rejectValue("fullName", "person.fullName.exists",
                    "Person with this full name already exists");
        }
    }
}
