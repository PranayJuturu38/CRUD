package com.crudapp.main.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.crudapp.main.model.Department;

@Service
public interface DepartmentService {
 
	List<Department> getAlldept();
	Department savedept(Department dept);
	Department getBydeptid(Integer dept_id);
	void delete(Integer dept_id);
	Department getByName(String name);
	List<Department> getByLocation(String location);
}
