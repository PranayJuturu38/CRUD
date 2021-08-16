package com.crudapp.main.serviceimpl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crudapp.main.exception.CustomException;
import com.crudapp.main.model.Person;
import com.crudapp.main.repository.PersonRepository;
import com.crudapp.main.service.PersonService;
import com.crudapp.main.service.DepartmentService;


@Service
@Transactional
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonRepository repo;

	@Autowired
	private DepartmentService deptservice;

	
	@Override
	public List<Person> listAll()  {
	
		if(repo.findAll() != null) {
			return repo.findAll();
		} else{
			
			throw new CustomException ("No records found");
		}
	}

	
	@Override
	public Person save(Person person) {
	
		Optional<Person> exisitngPerson = repo.findById(person.getid());
		if(!exisitngPerson.isPresent()){
            Person newPerson = new Person();

			newPerson.setid(person.getid());
			newPerson.setpersonname(person.getpersonname());
            newPerson.setPassword(person.getPassword());
            newPerson.setEmail(person.getEmail());
			newPerson.setDepartment(person.getDepartment());
			
			newPerson = repo.save(newPerson);
	        
			return newPerson;
		
		}else{
            throw new CustomException("Person already exists");
		}
	}

	@Override
	public Person get(Integer id) throws CustomException{
		Optional<Person> person = repo.findById(id);
		if(person.isPresent()){
	
			return person.get();
	}
	else{
		throw new CustomException("No person with id " + id );
	}
}

	public void delete(Integer id) throws CustomException{
		Optional<Person> person = repo.findById(id);
		if(person.isPresent()){
			repo.deleteById(id);
		}
		else
		{
			throw new CustomException("No person with id " + id);
		}
	}

	@Override
	public Person getByName(String personname) throws CustomException{
		Person person = repo.getByName(personname);
		if(person != null){

			return repo.getByName(personname);
		}
		else{
			throw new CustomException("No person with name " + personname);

		}
	}
}
