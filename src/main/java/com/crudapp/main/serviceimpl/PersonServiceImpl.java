package com.crudapp.main.serviceimpl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.crudapp.main.ApiResponse.UtilMethods;
import com.crudapp.main.exception.CustomException;
import com.crudapp.main.model.Person;
import com.crudapp.main.repository.PersonRepository;
import com.crudapp.main.service.PersonService;
import com.google.gson.Gson;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonRepository repo;

	private UtilMethods util = new UtilMethods();

	@Override
	public List<Person> listAll() {

		if (repo.findAll() != null) {
			return repo.findAll();
		} else {

			throw new CustomException("No records found");
		}
	}

	@Override
	public Person save(Person person) {

		Optional<Person> exisitngPerson = repo.findById(person.getid());

		if (!exisitngPerson.isPresent()) {
			Person newPerson = new Person();

			newPerson.setid(person.getid());
			newPerson.setpersonname(person.getpersonname());
			newPerson.setPassword(person.getPassword());
			newPerson.setEmail(person.getEmail());
			newPerson.setDepartment(person.getDepartment());
			newPerson.setProject(person.getProject());
			newPerson = repo.save(newPerson);

			return newPerson;

		} else {
			throw new CustomException("Person already exists");
		}
	}

	@Override
	public ResponseEntity<Object> get(Integer id) throws CustomException {

		Optional<Person> person = repo.findById(id);

		if (person.isPresent()) {

			return util.modifyResponseObject(person.get(), "Found Data");

		} else {

			return new ResponseEntity<Object>("Not Found", HttpStatus.NOT_FOUND);
		}
	}

	public boolean delete(Integer id) throws CustomException {
		Optional<Person> person = repo.findById(id);
		if (person.isPresent()) {
			repo.deleteById(id);
			return true;
		} else {
			throw new CustomException("No person with id " + id);
		}
	}

	@Override
	public Person getByName(String personname) throws CustomException {
		Person person = repo.getByName(personname);
		if (person != null) {

			return repo.getByName(personname);
		} else {
			throw new CustomException("No person with name " + personname);

		}
	}

	@Override
	public Person updateperson(Person person) {
		Optional<Person> existingperson = repo.findById(person.getid());
		if (existingperson.isPresent()) {
			Person updatedperson = existingperson.get();
			updatedperson.setpersonname(person.getpersonname());
			updatedperson.setPassword(person.getPassword());
			updatedperson.setEmail(person.getEmail());
			updatedperson.setDepartment(person.getDepartment());
			updatedperson.setProject(person.getProject());

			updatedperson = repo.save(updatedperson);
			return updatedperson;
		}

		else {
			throw new CustomException("Person does not exist");
		}
	}
}
