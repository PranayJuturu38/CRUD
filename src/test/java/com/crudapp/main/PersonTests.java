package com.crudapp.main;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.json.Json;

import com.crudapp.main.exception.CustomException;
import com.crudapp.main.model.Department;
import com.crudapp.main.model.Person;
import com.crudapp.main.model.Project;
import com.crudapp.main.repository.PersonRepository;
import com.crudapp.main.service.PersonService;
import com.google.gson.Gson;

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
        Gson gson = new Gson();
        String jsonString = gson.toJson(actualPerson);
        String index = "id";
          int actualPersonIndex =   jsonString.indexOf(index);
          int actualPersonId = jsonString.charAt(actualPersonIndex);
        assertEquals(expectedPerson.getid(), actualPersonId);

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

    @Test //Delete Person happy //mockito //
    void deletePerson(){
        Person expectedPerson = new Person();
        expectedPerson.setid(1);
        expectedPerson.setpersonname("pranay");
        expectedPerson.setPassword("password");
        expectedPerson.setEmail("email");
        
        personService.save(expectedPerson);

        doNothing().when(personMockRepo).deleteById(expectedPerson.getid());
        boolean actualPerson = personService.delete(expectedPerson.getid());
        assertEquals(actualPerson,true);
    }

    @Test // GetAll Persons //mockito //
    void getAllPersons() {

       Person p1 = new Person();
       p1.setid(1);
       p1.setpersonname("pranay");
       p1.setPassword("password");
       p1.setEmail("email");
       
       Person p2 = new Person();
       p2.setid(2);
       p2.setpersonname("reddy");
       p2.setPassword("password");
       p2.setEmail("email");
       
       List<Person> expectedPersonList = new ArrayList<Person>();

        expectedPersonList.add(p1);
        expectedPersonList.add(p2);
        
        when(personMockRepo.findAll()).thenReturn(expectedPersonList);

        personService.save(p1);
        personService.save(p2);
        List<Person> actualPersonList = personService.listAll();
        assertEquals(expectedPersonList.size(), actualPersonList.size());
    }

    @Test //unhappy mockito
    void getAllPersonsFailure(){
        Person p1 = new Person();
       p1.setid(1);
       p1.setpersonname("pranay");
       p1.setPassword("password");
       p1.setEmail("email");
       
       Person p2 = new Person();
       p2.setid(2);
       p2.setpersonname("reddy");
       p2.setPassword("password");
       p2.setEmail("email");
       
       List<Person> expectedPersonList = new ArrayList<Person>();

        expectedPersonList.add(p1);
        expectedPersonList.add(p2);
        
        when(personMockRepo.findAll()).thenReturn(expectedPersonList);

        personService.save(p1);
        personService.save(p2);
        
        List<Person> actualPersonList = new ArrayList<Person>();
        try{
          actualPersonList = personService.listAll();
        }catch(Exception e){
           assertFalse(expectedPersonList.equals(actualPersonList));
        }
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
        Person actualPerson = (Person) personService.get(expectedPerson.getid());
        assertEquals(expectedPerson.getid(), actualPerson.getid());
        Person updatedPerson = personService.updateperson(expectedPerson);
        assertTrue(expectedPerson.getid().equals(updatedPerson.getid()));
    }

}
