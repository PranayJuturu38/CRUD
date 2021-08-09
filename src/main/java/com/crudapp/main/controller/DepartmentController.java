package com.crudapp.main.controller;

import java.util.List;

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

import com.crudapp.main.exception.CustomException;
import com.crudapp.main.model.Department;
import com.crudapp.main.service.DepartmentService;

@RestController
public class DepartmentController {

	@Autowired
	private DepartmentService deptservice;
		
	@GetMapping("/departments")
	public List<Department> list(){
		return deptservice.getAlldept();
	}
	
	@GetMapping("/departments/{dept_id}")
	public ResponseEntity<Department> get(@PathVariable Integer dept_id) throws Exception {
	    try {
	        Department dept = deptservice.getBydeptid(dept_id);
	        return new ResponseEntity<Department>(dept, HttpStatus.OK);
	        
	    } catch (Exception e) {
	    	return new ResponseEntity<Department>(HttpStatus.NOT_FOUND);
	    }     
	  
	}

	@GetMapping("/departments/name/{name}")
	public ResponseEntity<Object> getfromname(@PathVariable("name") String name) throws Exception {
		try{
			Department dept = deptservice.getByName(name);
			return new ResponseEntity<>(dept, HttpStatus.OK);
		}catch (Exception customException) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/departments/locations/{location}")
	public List<Department> getByLocation(@PathVariable("location") String location) throws Exception {
		return deptservice.getByLocation(location);
	}
	
	
	@PostMapping("/departments")
	public void add(@RequestBody Department dept ) throws Exception{
		try{
			deptservice.savedept(dept);
		}catch(CustomException customException)
		
		{
			throw new CustomException (customException);
		}
	 
	
	}
	
	@PutMapping("/departments/{dept_id}")
	public ResponseEntity<?> update(@RequestBody Department dept, @PathVariable Integer dept_id) throws Exception{
	    try {
	        Department existingdept = deptservice.getBydeptid(dept_id);
	        deptservice.savedept(dept);
	        return new ResponseEntity<>(HttpStatus.OK);
	    } catch (CustomException customException) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }      
	}

	
	
	@DeleteMapping("/departments/{dept_id}")
	public void delete(@PathVariable Integer dept_id) throws Exception{
	    try {
		deptservice.delete(dept_id);
	}catch(CustomException customException) {
		throw new CustomException (customException);
	}
	}
	
}
