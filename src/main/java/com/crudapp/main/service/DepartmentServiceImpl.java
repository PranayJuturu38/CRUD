package com.crudapp.main.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crudapp.main.exception.CustomException;
import com.crudapp.main.model.Department;
import com.crudapp.main.repository.Departmentrepository;
@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService{

	@Autowired
	private Departmentrepository deptrepo;
	@Override
	public List<Department> getAlldept() throws CustomException{
		if(deptrepo == null){
			throw new CustomException("No departments available");
		}
		else{
		return deptrepo.findAll();
	}
}

	@Override
	public Department savedept(Department dept) {
		return deptrepo.save(dept);
	}

	@Override
	public Department getBydeptid(Integer dept_id) {
		Department dept = deptrepo.getById(dept_id);
		if(dept == null){
			throw new CustomException("No department with the id:"+dept_id);
		}
		else{
		return dept;
	}
}

	@Override
	public void delete(Integer dept_id) {
		deptrepo.deleteById(dept_id);
	}

	@Override
	public Department getByName(String name) throws CustomException{
		Department dept = deptrepo.getByName(name);
		if(dept != null)
		{
		return dept;	
	}
		else
		{

			throw new CustomException("data not found");
	    
	}
}

	@Override
	public List<Department> getByLocation(String location) {
		return deptrepo.getByLocation(location);
	}
	

}
