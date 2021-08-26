package com.crudapp.main;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.crudapp.main.model.Project;
import com.crudapp.main.service.ProjectService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProjectTests {
   
    @Autowired
	private ProjectService projectService;


	@Test //POST Mapping "/projects" Happy TestCase
	void projectSaved(){
	 Project project = new Project();
	 project.setId(1);
	 project.setName("MobileApplication");
	 project.setDescription("used by patients");

	 Project savedproject = projectService.saveproject(project);
	 assertEquals(savedproject.getId(), project.getId());
	
	}

	@Test //Project get by id "/projects/{id}"
     void projectID(){

		Project project = new Project();
		project.setId(1);
		project.setName("MobileApplication");
		project.setDescription("used by patients");

		assertEquals(1,project.getId());
	 }



}
