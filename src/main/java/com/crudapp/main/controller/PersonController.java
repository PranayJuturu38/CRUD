package com.crudapp.main.controller;

import java.util.List;

import com.crudapp.main.exception.CustomException;
import com.crudapp.main.model.Department;
import com.crudapp.main.model.Person;
import com.crudapp.main.repository.PersonRepository;

import com.crudapp.main.service.DepartmentService;
import com.crudapp.main.service.PersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class PersonController {
 
	@Autowired
	private PersonService service;

	@Autowired
	private DepartmentService deptservice;

	
	@GetMapping("/persons")
	public List<Person> list(){
		
		return service.listAll();
	
	}
	
	@GetMapping("/persons/{id}")
	public ResponseEntity<Person> get(@PathVariable Integer id) throws CustomException {
	    try {
	        Person person = service.get(id);
			return new ResponseEntity<Person>(person, HttpStatus.OK);
	        
	    } catch (Exception e) {
			throw e;
	    }

	    
	}
	
    @GetMapping("/persons/name/{personname}")
	public ResponseEntity<Person> getfromname(@PathVariable("personname") String personname) throws Exception {
		try{
			Person person = service.getByName(personname);
			return new ResponseEntity<Person>(person,HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/persons")
	public void add(@Validated @RequestBody Person person) throws Exception {
	
		try{

			 service.save(person); 
			
		}catch(Exception e)
		{
			throw e;
		}
	}
	
	@PostMapping("/persons/{id}/departments/{dept_id}")
	 Person assignDept(@PathVariable Integer id,@PathVariable Integer dept_id) throws Exception{
        Person person = service.get(id);
		Department dept = deptservice.getBydeptid(dept_id);
	    person.setDepartment(dept);
		return person;	
	}
	
	
	@PutMapping("/persons/{id}")
	public ResponseEntity<?> update(@Validated@RequestBody Person person, @PathVariable Integer id) throws Exception{
	    try {                                                                                     
	        service.save(person);
	        return new ResponseEntity<>(HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);//
	    }      
	}

	@DeleteMapping("/persons/{id}")
	public void delete(@PathVariable Integer id) throws Exception{
	    try {
		service.delete(id);
	}catch(Exception e) {
		throw e;
	}
	}
	
	
	}
	


