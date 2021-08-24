package com.crudapp.main;

import static org.junit.jupiter.api.Assertions.*;

import com.crudapp.main.service.*;
import com.crudapp.main.model.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Crud1ApplicationTests {

	@Autowired
	private DepartmentService deptservice;

	@Autowired
	private ProjectService projectService;

	@Test
	void DeptSaved() {
	
		Department dept = new Department();
		dept.setDept_id(1);
		dept.setName("admin");
		dept.setLocation("main");

		Department savedDept = deptservice.savedept(dept);
        
		assertEquals(savedDept.getDept_id(), dept.getDept_id());
	}

    @Test
	void DeptNotSaved() throws Exception{
		Department dept1 = new Department();
		dept1.setDept_id(1);
		dept1.setName("admin");
		dept1.setLocation("main");

		Department dept2 = new Department(1,"admin","main");

		Department savedDept1 = deptservice.savedept(dept1);
		Department savedDept2 = deptservice.savedept(dept2);
        
		try{
			assertEquals(savedDept1.getDept_id(), savedDept2.getDept_id());
		}
		catch(Exception e){
			throw e;
		}
	}

	@Test
	void projectSaved(){
	 Project project = new Project();
	 project.setId(1);
	 project.setName("MobileApplication");
	 project.setDescription("used by patients");

	 Project savedproject = projectService.saveproject(project);
	 assertEquals(savedproject.getId(), project.getId());
	
	}

}
