package com.crudapp.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.crudapp.main.model.Project;
import com.crudapp.main.service.ProjectService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProjectTests {

	@Autowired
	private ProjectService projectService;

	// @Autowired
	// private MockMvc mockMvc;

	@Test // POST Mapping "/projects" Happy TestCase
	void projectSaved() {
		Project project = new Project();
		project.setId(1);
		project.setName("MobileApplication");
		project.setDescription("used by patients");

		Project savedproject = projectService.saveproject(project);
		assertEquals(savedproject.getId(), project.getId());

	}

	@Test // Project get by id "/projects/{id}"
	void projectID() {

		Project project = new Project();
		project.setId(1);
		project.setName("MobileApplication");
		project.setDescription("used by patients");

		assertEquals(1, project.getId());
	}

	@Test // Project get by id unhappy
	void projectNoID() {

		Project project = new Project();
		project.setId(1);
		project.setName("MobileApplication");
		project.setDescription("used by patients");

		Object returnedproject = new Project();
		try {
			returnedproject = projectService.getProjectById(2);
		} catch (Exception e) {

			assertFalse(returnedproject == null, "project not be found");

		}
	}

	// @Test // Get all projects
	// void getallTest() {
	// 	Project project = new Project();
	// 	project.setId(1);
	// 	project.setName("MobileApplication");
	// 	project.setDescription("used by patients");

	// 	List<Project> projects = new ArrayList<>();
	// 	projects.add(project);

	// 	// MvcResult result = mockMvc
	// 	// .perform(MockMvcRequestBuilders.get("/projects")
	// 	// .accept(MediaType.APPLICATION_JSON))
	// 	// .andExpect(status().isOk())
	// 	// .andExpect(MockMvcResultMatchers.jsonPath("$.projects").exists())
	// 	// .andExpect(MockMvcResultMatchers.jsonPath("$.projects[*].project_id").isNotEmpty());
	// 	// }

	// }
}
