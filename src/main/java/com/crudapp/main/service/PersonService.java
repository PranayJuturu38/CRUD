package com.crudapp.main.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.crudapp.main.model.Person;


@Service
@Transactional
public interface PersonService  {
   
    List<Person> listAll(); 
    Person save(Person person);
    Person get(Integer id);
    void delete(Integer id);
	Person getByName(String personname);
}
