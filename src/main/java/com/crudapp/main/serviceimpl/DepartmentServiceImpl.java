package com.crudapp.main.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crudapp.main.exception.CustomException;
import com.crudapp.main.model.Department;
import com.crudapp.main.repository.Departmentrepository;
import com.crudapp.main.service.DepartmentService;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService{

	@Autowired
	private Departmentrepository deptrepo;
	@Override
	public List<Department> getAlldept(){
		List<Department> dept = deptrepo.findAll();
		if(dept.size() > 0){
			return dept;
	}else{
		return new ArrayList<Department>();
	}
}


	@Override
	public Department savedept(Department dept) throws CustomException{
	
		Optional<Department> newDept = deptrepo.findById(dept.getDept_id());
		if(!newDept.isPresent()){
            Department newDepartment = new Department();
			newDepartment.setDept_id(dept.getDept_id());
			newDepartment.setName(dept.getName());
		    newDepartment.setLocation(dept.getLocation());
			newDepartment.setPersons(dept.getPersons());

			newDepartment = deptrepo.save(newDepartment);
	        return newDepartment;
		}else{
            throw new CustomException("Department already exists");
		}
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
	    Department dept= deptrepo.getByName(name);
		if(dept !=null)
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
		if(dept.isEmpty()){
			throw new CustomException("No departments at location " + location);
	
	}
	else{
     	return dept;	
	}
}

	@Override
	public Department updatedept(Department dept) throws CustomException{
      Optional<Department> existingdept = deptrepo.findById(dept.getDept_id());
	  if(existingdept.isPresent()){
		  Department updateddept = existingdept.get();
		  updateddept.setName(dept.getName());
		  updateddept.setLocation(dept.getLocation());
		  updateddept.setPersons(dept.getPersons());

		 updateddept = deptrepo.save(updateddept);
		 return updateddept;
	  }

	else{
         throw new CustomException("Department does not exist");
	}
		
	}
	

}