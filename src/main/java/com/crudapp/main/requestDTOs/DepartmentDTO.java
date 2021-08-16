package com.crudapp.main.requestDTOs;

import java.util.ArrayList;
import java.util.List;

import com.crudapp.main.model.Person;

public class DepartmentDTO {
    
    private Integer dept_id;
	private String name;
	private String location;
	
	private List<Person> persons = new ArrayList<Person>();
	

	public Integer getDept_id() {
		return dept_id;
	}


	public void setDept_id(Integer dept_id) {
		this.dept_id = dept_id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

    
	

public List<Person> getPersons(){
		return persons;
	}

	public void setPersons(List<Person> persons){
		this.persons = persons;
	}
}
