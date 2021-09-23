package com.crudapp.main.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.crudapp.main.model.Department;
import com.crudapp.main.repository.Departmentrepository;
import com.crudapp.main.service.DepartmentService;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DepartmentTests {

	@Autowired
	private DepartmentService deptService;

	@Mock
	Departmentrepository mockDeptRepo;

	@Test // POST Mapping "/departments" Happy Test Case //Mockito
	void DeptSaved() {

		Department expectedDept = new Department();
		expectedDept.setDeptId(3);
		expectedDept.setName("production");
		expectedDept.setLocation("main");

		when(mockDeptRepo.save(expectedDept)).thenReturn(expectedDept);
		Department actualDept = deptService.saveDept(expectedDept);
		assertEquals(expectedDept.getDeptId(), actualDept.getDeptId());
	}

	@Test // POST Mapping "/departments" Unhappy Test Case //mockito
	void DeptNotSaved() throws Exception {

		Department expectedDept = new Department();
		expectedDept.setDeptId(1);
		expectedDept.setName("admin");
		expectedDept.setLocation("main");
		Department dummyDept = new Department(2, "HR", "main");

		when(mockDeptRepo.save(expectedDept)).thenReturn(expectedDept);
		Department actualDept = new Department();
		try {

			actualDept = deptService.saveDept(dummyDept);

		} catch (Exception e) {

			assertFalse(expectedDept.equals(actualDept));
		}
	}

	@Test // Get all Depts //mockito
	void getAllDepts() {
		Department expectedDept = new Department(1, "main", "admin");
		Department d1 = new Department(2, "HR", "admin");
		Department d2 = new Department(3, "R&D", "admin");

		List<Department> expectedDeptList = new ArrayList<Department>();
		expectedDeptList.add(d1);
		expectedDeptList.add(d2);
		expectedDeptList.add(expectedDept);

		when(mockDeptRepo.findAll()).thenReturn(expectedDeptList);
		
		List<Department> actualDeptList = deptService.getAllDept();

		assertEquals(expectedDeptList.size(), actualDeptList.size());

	}

	@Test // Get all depts Failure //mockito
	void getAllDeptsFailure() throws Exception {
		Department expectedDept = new Department(1, "main", "admin");
		Department d1 = new Department(2, "HR", "admin");
		Department d2 = new Department(3, "R&D", "admin");

		List<Department> expectedDeptList = new ArrayList<Department>();
		expectedDeptList.add(d1);
		expectedDeptList.add(d2);
		expectedDeptList.add(expectedDept);

		when(mockDeptRepo.findAll()).thenReturn(expectedDeptList);
		deptService.saveDept(expectedDept);
		deptService.saveDept(d1);
		// deptService.savedept(d2);
		List<Department> actualDeptList = new ArrayList<Department>();
		try {
			actualDeptList = deptService.getAllDept();
		} catch (Exception e) {
			assertFalse(expectedDeptList.equals(actualDeptList));
		}
	}

	@Test // Department get by id //mockito
	void deptId() {
		Department expectedDept = new Department();
		expectedDept.setDeptId(1);
		expectedDept.setName("admin");
		expectedDept.setLocation("main");

		when(mockDeptRepo.findById(expectedDept.getDeptId())).thenReturn(Optional.of(expectedDept));

		deptService.saveDept(expectedDept);

		Department actualDept = deptService.getByDeptId(expectedDept.getDeptId());

		assertEquals(expectedDept.getDeptId(), actualDept.getDeptId());
	}

	@Test // Mockito
	void deptnoid() {
		Department expectedDept = new Department();
		expectedDept.setDeptId(1);
		expectedDept.setName("admin");
		expectedDept.setLocation("main");

		when(mockDeptRepo.findById(expectedDept.getDeptId())).thenReturn(Optional.of(expectedDept));
		deptService.saveDept(expectedDept);
		Department actualDepartment = new Department();
		try {
			actualDepartment = deptService.getByDeptId(2);

		} catch (Exception e) {
			assertFalse(expectedDept.getDeptId().equals(actualDepartment.getDeptId()));
		}
	}

	@Test // Department get by name //mockito
	void deptName() {
		Department expectedDept = new Department();
		expectedDept.setDeptId(1);
		expectedDept.setName("admin");
		expectedDept.setLocation("main");

		when(mockDeptRepo.getByName(expectedDept.getName())).thenReturn(expectedDept);

		deptService.saveDept(expectedDept);
		Department actualDept = deptService.getByName(expectedDept.getName());
		assertEquals(expectedDept.getName(), actualDept.getName());
	}

	@Test // mockito
	void deptnoname() {
		Department expectedDept = new Department();
		expectedDept.setDeptId(1);
		expectedDept.setName("admin");
		expectedDept.setLocation("main");

		when(mockDeptRepo.getByName(expectedDept.getName())).thenReturn(expectedDept);
		deptService.saveDept(expectedDept);
		Department actualDept = new Department();
		try {

			actualDept = deptService.getByName("reddy");

		} catch (Exception e) {
			assertFalse(expectedDept.getName().equals(actualDept.getName()));
		}

	}

	@Test // Department get by location //mockito

	void deptLocation() {

		Department expectedDept = new Department();

		expectedDept.setDeptId(1);
		expectedDept.setName("admin");
		expectedDept.setLocation("main");
		List<Department> deptList = new ArrayList<Department>();
		deptList.add(expectedDept);
		when(mockDeptRepo.getByLocation(expectedDept.getLocation())).thenReturn(deptList);
		//deptService.saveDept(expectedDept);

		List<Department> actualDept = deptService.getByLocation(expectedDept.getLocation());

		assertEquals(deptList.size(), actualDept.size());
	}

	@Test // mockito
	void deptNoLocation() {
		Department expectedDept = new Department();
		expectedDept.setDeptId(1);
		expectedDept.setName("admin");
		expectedDept.setLocation("main");
		List<Department> expectedList = new ArrayList<Department>();
		expectedList.add(expectedDept);
		when(mockDeptRepo.getByLocation(expectedDept.getLocation())).thenReturn(expectedList);
		deptService.saveDept(expectedDept);

		List<Department> actualList = new ArrayList<Department>();

		try {

			actualList = deptService.getByLocation("location");

		} catch (Exception e) {
			assertTrue(actualList.isEmpty());
		}

	}

	@Test // update department details //mockito
	void updateDepartment() {
		Department expectedDept = new Department();
		expectedDept.setDeptId(1);
		expectedDept.setName("admin");
		expectedDept.setLocation("main");

		deptService.saveDept(expectedDept);
		when(mockDeptRepo.findById(expectedDept.getDeptId())).thenReturn(Optional.of(expectedDept));
		Department actualDept = deptService.getByDeptId(expectedDept.getDeptId());
		assertEquals(expectedDept.getDeptId(), actualDept.getDeptId());
		Department updatedDept = deptService.updateDept(expectedDept);
		assertTrue(expectedDept.getDeptId().equals(updatedDept.getDeptId()));

	}

	@Test
	void updateFailedDept() {
		Department expectedDept = new Department();
		expectedDept.setDeptId(1);
		expectedDept.setName("admin");
		expectedDept.setLocation("main");

		Department dummyDept = new Department(2, "HR", "main");

		deptService.saveDept(expectedDept);
		deptService.saveDept(dummyDept);

		when(mockDeptRepo.findById(expectedDept.getDeptId())).thenReturn(Optional.of(expectedDept));
		Department actualDept = deptService.getByDeptId(expectedDept.getDeptId());
		assertEquals(expectedDept.getDeptId(), actualDept.getDeptId());

		Department updatedDept = new Department();
		try {
			updatedDept = deptService.updateDept(dummyDept);

		} catch (Exception e) {
			assertFalse(expectedDept.getDeptId().equals(updatedDept.getDeptId()));
		}
	}

	@Test // Delete Department //mockito
	void deleteDepartment() {
		Department expectedDept = new Department();
		expectedDept.setDeptId(1);
		expectedDept.setName("admin");
		expectedDept.setLocation("main");

		deptService.saveDept(expectedDept);
		doNothing().when(mockDeptRepo).deleteById(expectedDept.getDeptId());

		boolean actualDepartment = deptService.delete(expectedDept.getDeptId());
		assertEquals(actualDepartment, true);
	}

}
