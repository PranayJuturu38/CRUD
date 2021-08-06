package com.crudapp.main.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crudapp.main.model.Person;
import com.crudapp.main.repository.PersonRepository;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonRepository repo;

	
	@Override
	public List<Person> listAll() {
		return repo.findAll();
	}

	@Override
	public Person save(Person person) {
		return repo.save(person);
	}

	@Override
	public Person get(Integer id) {
		return repo.getById(id);
	}

	public void delete(Integer id) {
		repo.deleteById(id);
	}

	@Override
	public Person getByName(String personname) {
		return repo.getByName(personname);
	}
}
