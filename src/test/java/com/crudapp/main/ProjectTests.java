package com.crudapp.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.crudapp.main.model.Project;
import com.crudapp.main.repository.Projectrepository;
import com.crudapp.main.service.ProjectService;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProjectTests {

	@Autowired
	private ProjectService projectService;

	@Mock
	Projectrepository mockProjectRepository;

	@Test // POST Mapping "/projects" Happy TestCase //Mockito
	void projectSaved() {
		Project expectedProject = new Project();
		expectedProject.setId(1);
		expectedProject.setName("MobileApplication");
		expectedProject.setDescription("used by patients");

		// projectService.saveproject(expectedproject);

		when(mockProjectRepository.save(expectedProject)).thenReturn(expectedProject);
		Project actualProject = projectService.saveproject(expectedProject);
		assertEquals(expectedProject.getId(), actualProject.getId());
	}

	@Test // Project Not saved //Mockito
	void projectNotSaved() throws Exception {
		Project expectedProject = new Project();
		expectedProject.setId(1);
		expectedProject.setName("MobileApplication");
		expectedProject.setDescription("used by patients");

		when(mockProjectRepository.save(expectedProject)).thenReturn(expectedProject);

		Project actualProject = new Project();
		actualProject = projectService.saveproject(expectedProject);
		assertFalse(expectedProject.equals(actualProject));
	}

	@Test // Project get by id "/projects/{id}"//Mockito
	void projectID() {

		Project expectedProject = new Project();
		expectedProject.setId(1);
		expectedProject.setName("MobileApplication");
		expectedProject.setDescription("used by patients");

		// Checking the project in the repo
		when(mockProjectRepository.findById(expectedProject.getId())).thenReturn(Optional.of(expectedProject));

		projectService.saveproject(expectedProject);// saving in the db

		Project actualProject = projectService.getProjectById(expectedProject.getId());

		assertEquals(expectedProject.getId(), actualProject.getId());

	}

	@Test // Project get by id unhappy //mockito //
	void projectNoID() throws Exception {

		Project expectedProject = new Project();
		expectedProject.setId(1);
		expectedProject.setName("MobileApplication");
		expectedProject.setDescription("used by patients");

		when(mockProjectRepository.findById(expectedProject.getId())).thenReturn(Optional.empty());
		projectService.saveproject(expectedProject);
		Project actualProject = new Project();
		try {
			actualProject = projectService.getProjectById(2);

		} catch (Exception e) {

			assertFalse(expectedProject.getId().equals(actualProject.getId()));// try with other project
		}
	}

	@Test // project updated //Mockito
	void updateProject() {
		Project expectedProject = new Project();
		expectedProject.setId(1);
		expectedProject.setName("MobileApplication");
		expectedProject.setDescription("used by patients");

		projectService.saveproject(expectedProject);

		when(mockProjectRepository.findById(expectedProject.getId())).thenReturn(Optional.of(expectedProject));
		Project actualProject = projectService.getProjectById(expectedProject.getId());
		assertEquals(expectedProject.getId(), actualProject.getId());
		Project updatedProject = projectService.updateproject(expectedProject);
		assertTrue(expectedProject.getId().equals(updatedProject.getId()));
	}

	@Test // project update failed //mockito //
	void updateProjectFails() throws Exception {
		Project expectedProject = new Project();
		expectedProject.setId(1);
		expectedProject.setName("MobileApplication");
		expectedProject.setDescription("used by patients");

		Project dummyProject = new Project(2, "web", "used by patients");
		projectService.saveproject(expectedProject);
		projectService.saveproject(dummyProject);

		when(mockProjectRepository.findById(expectedProject.getId())).thenReturn(Optional.of(expectedProject));
		Project actualProject = projectService.getProjectById(expectedProject.getId());
		assertEquals(expectedProject.getId(), actualProject.getId());
		Project updatedProject = new Project();
		try {
			updatedProject = projectService.saveproject(dummyProject);

		} catch (Exception e) {
			assertFalse(expectedProject.getId().equals(updatedProject.getId()));
		}

	}

	@Test // project delete
	void projectDelete() {

		Project expectedProject = new Project();
		expectedProject.setId(1);
		expectedProject.setName("MobileApplication");
		expectedProject.setDescription("used by patients");

		projectService.saveproject(expectedProject);

		doNothing().when(mockProjectRepository).deleteById(expectedProject.getId());
		boolean actualProject = projectService.delete(expectedProject.getId());
		assertEquals(actualProject, true);
	}

	@Test // Get all //mockito //
	void getAllProjects() {
		Project expectedProject = new Project(1, "local", "used by staff");
		;
		Project p1 = new Project(2, "web", "used by patients");
		Project p2 = new Project(3, "mobile", "used by patients");

		List<Project> expectedList = new ArrayList<Project>();
		expectedList.add(expectedProject);
		expectedList.add(p1);
		expectedList.add(p2);

		when(mockProjectRepository.findAll()).thenReturn(expectedList);
		projectService.saveproject(expectedProject);
		projectService.saveproject(p1);
		projectService.saveproject(p2);

		List<Project> actualList = projectService.getAllproj();

		assertEquals(expectedList.size(), actualList.size());

	}

	@Test // mockito //
	void getAllProjectsFails() throws Exception {
		Project expectedProject = new Project(1, "local", "used by staff");
		;
		Project p1 = new Project(2, "web", "used by patients");
		Project p2 = new Project(3, "mobile", "used by patients");

		List<Project> expectedList = new ArrayList<Project>();
		expectedList.add(expectedProject);
		expectedList.add(p1);
		expectedList.add(p2);

		when(mockProjectRepository.findAll()).thenReturn(expectedList);
		projectService.saveproject(expectedProject);
		projectService.saveproject(p1);

		List<Project> actualList = new ArrayList<Project>();

		try {
			actualList = projectService.getAllproj();
		} catch (Exception e) {
			assertFalse(expectedList.equals(actualList));
		}
	}
}
