package com.crudapp.main.controller;

import java.util.List;

import com.crudapp.main.exception.CustomException;
import com.crudapp.main.model.Person;
import com.crudapp.main.service.PersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	@GetMapping("/persons")
	public List<Person> list(){
		
		return service.listAll();
	}
	
	@GetMapping("/persons/{id}")
	public ResponseEntity<Person> get(@PathVariable Integer id) throws Exception {
	    try {
	        Person person = service.get(id);
	        return new ResponseEntity<Person>(person, HttpStatus.OK);
	        
	    } catch (CustomException customException) {
	        System.out.print(customException);
	    	return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
	    }     
	    
	}
	
    @GetMapping("/persons/name/{personname}")
	public ResponseEntity<Person> getfromname(@PathVariable("personname") String personname) throws Exception {
		try{
			Person person = service.getByName(personname);
			return new ResponseEntity<Person>(person,HttpStatus.OK);
		}catch (CustomException customException) {
			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/persons")
	public void add(@RequestBody Person person) throws Exception {
	
		try{
			service.save(person);
		}catch(CustomException customException)
		{
			throw new CustomException (customException);
		}
	}
	
	@PutMapping("/persons/{id}")
	public ResponseEntity<?> update(@RequestBody Person person, @PathVariable Integer id) throws Exception{
	    try {
	        Person existingPerson = service.get(id);
	        service.save(person);
	        return new ResponseEntity<>(HttpStatus.OK);
	    } catch (CustomException customException) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }      
	}
	
	@DeleteMapping("/persons/{id}")
	public void delete(@PathVariable Integer id) throws Exception{
	    try {
		service.delete(id);
	}catch(CustomException customException) {
		throw new CustomException (customException);
	}
	}
	
	
	}
	


