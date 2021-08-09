package com.crudapp.main.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
	public List<Department> getAlldept(){
		return deptrepo.findAll();
	}


	@Override
	public Department savedept(Department dept) throws CustomException{
		Department obj = new Department();
		obj.setDept_id(dept.getDept_id());
		obj.setLocation(dept.getLocation());
		obj.setName(dept.getName());
		return deptrepo.save(obj);
	}

	@Override
	public Department getBydeptid(Integer dept_id) throws CustomException{
       Optional<Department> dept = deptrepo.findById(dept_id);
	   if(dept.isPresent()) {
		   return dept.get();
	   }
	   else{
		   throw new CustomException("No department found with id " + dept_id);
	   }
        
	}


	@Override
	public void delete(Integer dept_id) throws CustomException{
		Optional<Department> dept = deptrepo.findById(dept_id);
		if(dept.isPresent()) {

              deptrepo.deleteById(dept_id);

		}
		else{
			throw new CustomException("No department found with id: " + dept_id);
		}
	}

	@Override
	public Department getByName(String name) throws CustomException{
	    Department dept=deptrepo.getByName(name);
		if(dept != null)
		{
		return dept;	
	}
		else
		{

			throw new CustomException("No department found with name: " + name);
	    
	}
}

	@Override
	public List<Department> getByLocation(String location) throws CustomException
	{
	    List<Department> dept = deptrepo.getByLocation(location);
		if(dept != null){
		return dept;	
	}
	else{
		throw new CustomException("No departments at location " + location);
	}
}
	

}