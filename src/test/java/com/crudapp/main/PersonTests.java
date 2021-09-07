package com.crudapp.main;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.crudapp.main.exception.CustomException;
import com.crudapp.main.model.Department;
import com.crudapp.main.model.Person;
import com.crudapp.main.model.Project;
import com.crudapp.main.repository.PersonRepository;
import com.crudapp.main.service.PersonService;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class PersonTests {

    @Autowired
    private PersonService personService;
    @Mock
    PersonRepository personMockRepo;

    @Test // POST Mapping "/persons" Happy TestCase //mockito
    void personSaved() {
        Person expectedPerson = new Person();

        expectedPerson.setid(1);
        expectedPerson.setpersonname("pranay");
        expectedPerson.setPassword("password");
        expectedPerson.setEmail("email");

        when(personMockRepo.save(expectedPerson)).thenReturn(expectedPerson);

        Person actualPerson = personService.save(expectedPerson);

        assertEquals(expectedPerson.getid(), actualPerson.getid());

    }

    @Test // Person SAve Unhappy TestCases //mockito //
    void personNotSaved() throws Exception {
        Person expectedPerson = new Person();
        expectedPerson.setid(1);
        expectedPerson.setpersonname("pranay");
        expectedPerson.setPassword("password");
        expectedPerson.setEmail("email");

        when(personMockRepo.save(expectedPerson)).thenReturn(expectedPerson);

        Person actualPerson = personService.save(expectedPerson);
        assertFalse(expectedPerson.equals(actualPerson));
    }

    @Test // Person get by id "/Persons/{id}" //Parse through ResponseEntity
    void personID() throws IOException {

        Person expectedPerson = new Person();
        expectedPerson.setid(1);
        expectedPerson.setpersonname("pranay");
        expectedPerson.setPassword("password");
        expectedPerson.setEmail("email");

        when(personMockRepo.findById(expectedPerson.getid())).thenReturn(Optional.of(expectedPerson));

        personService.save(expectedPerson);

        ResponseEntity<Object> actualPerson = (ResponseEntity<Object>) personService.get(1);
        // Gson gson = new Gson();
        // gson.toJson(actualPerson,new FileWriter("C:\\Users\\Dev\\Documents\\Kpi
        // Stuff\\obj.json"));
        // Gson gsonbuilder = new GsonBuilder().setPrettyPrinting().create();
        assertEquals(actualPerson.getStatusCode(), expectedPerson);
        // Json

    }

    @Test // Person get by id "/Persons/{id}" unhappy
    void personNoID() {

        // Person person = new Person();
        // person.setid(1);
        // person.setpersonname("pranay");
        // person.setPassword("password");
        // person.setEmail("email");
        // person.setDepartment(new Department(1, "admin", "main"));
        // Object returnedPerson = new Person();
        // try {
        // returnedPerson = personservice.get(2);
        // } catch (Exception e) {

        // assertFalse(returnedPerson != null, "Person not be found");

        // }

    }

    @Test // GetAll Persons
    void getAllPersons() {

        List<Person> expectedPerson = new ArrayList<Person>();
        Person p1 = new Person(1, "Pranay", "123", "abc@gmail.com", new Department(1, "HR", "main"),
                new Project(1, "mobile", "mobile application"));
        Person p2 = new Person(2, "Reddy", "123", "abc@gmail.com", new Department(2, "Admin", "main"),
                new Project(2, "web", "web application"));
        Person p3 = new Person(3, "Juturu", "123", "abc@gmail.com", new Department(1, "HR", "main"),
                new Project(3, "local", "local application"));

        expectedPerson.add(p1);
        expectedPerson.add(p2);
        expectedPerson.add(p3);

        personService.save(p1);
        personService.save(p2);
        personService.save(p3);

        when(personMockRepo.findAll()).thenReturn(expectedPerson);

        List<Person> actualPerson = new ArrayList<Person>();
        actualPerson = personService.listAll();
        assertEquals(expectedPerson.size(), actualPerson.size());
    }

    @Test // Person get by name "/persons/names/{name}" //mockito
    void personName() {
        Person expectedPerson = new Person();
        expectedPerson.setid(1);
        expectedPerson.setpersonname("pranay");
        expectedPerson.setPassword("password");
        expectedPerson.setEmail("email");

        when(personMockRepo.getByName(expectedPerson.getpersonname())).thenReturn(expectedPerson);

        personService.save(expectedPerson);
        Person actualPerson = personService.getByName(expectedPerson.getpersonname());
        assertEquals(expectedPerson.getpersonname(), actualPerson.getpersonname());
    }

    @Test // Unhappy get person by name "/persons/names/{name}"
    void personnoName() throws CustomException {
        Person expectedPerson = new Person();
        expectedPerson.setid(1);
        expectedPerson.setpersonname("pranay");
        expectedPerson.setPassword("password");
        expectedPerson.setEmail("email");

        when(personMockRepo.getByName(expectedPerson.getpersonname())).thenThrow(new CustomException("Not Found"));

        personService.save(expectedPerson);

        Person actualPerson = new Person();
        try {
            actualPerson = personService.getByName("reddy");
        } catch (Exception e) {
            assertFalse(expectedPerson.getpersonname().equals(actualPerson.getpersonname()));
        }
    }

    @Test // person updated //Mockito
    void updatePerson() {
        Person expectedPerson = new Person();
        expectedPerson.setid(1);
        expectedPerson.setpersonname("pranay");
        expectedPerson.setPassword("password");
        expectedPerson.setEmail("email");

        personService.save(expectedPerson);

        when(personMockRepo.findById(expectedPerson.getid())).thenReturn(Optional.of(expectedPerson));
        Object actualPerson = personService.get(expectedPerson.getid());
        assertEquals(expectedPerson.getid(), ((Person) actualPerson).getid());
        Person updatedPerson = personService.updateperson(expectedPerson);
        assertTrue(expectedPerson.getid().equals(updatedPerson.getid()));
    }

}
