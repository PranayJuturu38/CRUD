package com.crudapp.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.crudapp.main.exception.CustomException;
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
		Project actualProject = projectService.saveproject(expectedProject);
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

	@Test // project delete
	void projectDelete() {

		Project expectedProject = new Project();
		expectedProject.setId(1);
		expectedProject.setName("MobileApplication");
		expectedProject.setDescription("used by patients");

		projectService.saveproject(expectedProject);

		when(mockProjectRepository.findById(expectedProject.getId())).thenReturn(Optional.of(expectedProject));
		Project actualProject = projectService.getProjectById(expectedProject.getId());
		assertEquals(expectedProject.getId(), actualProject.getId());
		projectService.delete(expectedProject.getId());
		verify(mockProjectRepository).delete(expectedProject);
		reset(mockProjectRepository);
		when(mockProjectRepository.findById(expectedProject.getId())).thenReturn(Optional.empty());
		assertThrows(CustomException.class, () -> projectService.getProjectById(expectedProject.getId()));
		// verify(mockProjectRepository,times(1)).delete(expectedProject);
		// assertThat(mockProjectRepository.findById(expectedProject.getId()).isEmpty());

	}

	@Test // Get all projects
	void getAllProjects() {
		Project expectedProject1 = new Project();
		expectedProject1.setId(1);
		expectedProject1.setName("MobileApplication");
		expectedProject1.setDescription("patients");

		Project expectedProject = new Project();
		expectedProject.setId(2);
		expectedProject.setName("Mobile");
		expectedProject.setDescription("used by patients");

		projectService.saveproject(expectedProject);
		projectService.saveproject(expectedProject1);
		List<Project> projects = new ArrayList<Project>();
		projects.add(expectedProject);
		projects.add(expectedProject1);
		when(mockProjectRepository.findAll()).thenReturn(projects);

		List<Project> actualProjects = projectService.getAllproj();
		assertEquals(projects.size(), actualProjects.size());

	}
}
