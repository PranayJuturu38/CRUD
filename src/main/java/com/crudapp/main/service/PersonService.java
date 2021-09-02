package com.crudapp.main.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.crudapp.main.model.Person;

@Service
public interface PersonService {

    List<Person> listAll();

    Person save(Person person);

    Object get(Integer id);

    void delete(Integer id);

    Person getByName(String personname);

    Person updateperson(Person person);
}
