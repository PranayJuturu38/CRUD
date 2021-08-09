package com.crudapp.main.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crudapp.main.exception.CustomException;
import com.crudapp.main.model.Department;
import com.crudapp.main.model.Person;
import com.crudapp.main.repository.PersonRepository;

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

			throw new CustomException ("No records found");
		} else{
			return repo.findAll();
		}
	}

	
	@Override
	public Person save(Person person) {
		Person obj = new Person();
		obj.setid(person.getid());
		obj.setpersonname(person.getpersonname());
		obj.setEmail(person.getEmail());
		obj.setPassword(person.getPassword());
		obj.setDepartment(person.getDepartment());
		
		return repo.save(obj);
	}

	@Override
	public Person get(Integer id) throws CustomException{
		Person person = repo.getById(id);
		if(person != null){
		return repo.getById(id);
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
