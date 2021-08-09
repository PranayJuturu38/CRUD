package com.crudapp.main.model;



import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="department")
public class Department {
  
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="dept_id")
	private Integer dept_id;
	@Column(name="name")
	private String name;
	@Column(name="location")
	private String location;
	
     @JsonIgnore
	 @OneToMany(mappedBy="department", cascade = CascadeType.ALL)
	 private List<Person> persons = new ArrayList<Person>();
	
	public Department() {
		
	}

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

    
	

public Department(Integer dept_id, String name, String location) {
	this.dept_id = dept_id;
	this.name = name;
	this.location = location;
}
	public List<Person> getPersons(){
		return persons;
	}

}
