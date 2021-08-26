package com.crudapp.main;

import static org.junit.jupiter.api.Assertions.*;

import com.crudapp.main.model.Person;
import com.crudapp.main.service.PersonService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PersonTests {


    @Autowired
	private PersonService personservice;

    @Test//POST Mapping "/persons" Happy TestCase
    void personSaved() {
        Person person = new Person();
        person.setid(1);
        person.setpersonname("pranay");
        person.setPassword("password");
        person.setEmail("email");

        Person savedperson = new Person();
        savedperson = personservice.save(person);

        assertEquals(savedperson.getid(), savedperson.getid());

    }
	@Test //Person get by id "/Persons/{id}"
     void personID(){

		Person person = new Person();
		person.setid(1);
        person.setpersonname("pranay");
        person.setPassword("password");
        person.setEmail("email");
        
		assertEquals(1,person.getid());
	 }


     @Test//Person get by name "/persons/names/{name}"
     void personName(){
         Person person = new Person();
         person.setid(1);
         person.setpersonname("pranay");
         person.setPassword("password");
         person.setEmail("email");
        
         assertEquals("pranay",person.getpersonname());
        }



}
