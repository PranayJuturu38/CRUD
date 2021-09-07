package com.crudapp.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
		expectedDept.setDept_id(1);
		expectedDept.setName("admin");
		expectedDept.setLocation("main");

		when(mockDeptRepo.save(expectedDept)).thenReturn(expectedDept);
		Department actualDept = deptService.savedept(expectedDept);
		assertEquals(expectedDept.getDept_id(), actualDept.getDept_id());
	}

	@Test // POST Mapping "/departments" Unhappy Test Case
	void DeptNotSaved() throws Exception {

		Department expectedDept = new Department();
		expectedDept.setDept_id(1);
		expectedDept.setName("admin");
		expectedDept.setLocation("main");

		when(mockDeptRepo.save(expectedDept)).thenReturn(expectedDept);
		Department actualDept = deptService.savedept(expectedDept);
		assertFalse(expectedDept.equals(actualDept));
	}

	@Test // Department get by id //mockito
	void deptId() {
		Department expectedDept = new Department();
		expectedDept.setDept_id(1);
		expectedDept.setName("admin");
		expectedDept.setLocation("main");

		when(mockDeptRepo.findById(expectedDept.getDept_id())).thenReturn(Optional.of(expectedDept));

		deptService.savedept(expectedDept);

		Department actualDept = deptService.getBydeptid(expectedDept.getDept_id());

		assertEquals(expectedDept.getDept_id(), actualDept.getDept_id());
	}

	@Test //Mockito
	void deptnoid() {
		Department expectedDept = new Department();
		expectedDept.setDept_id(1);
		expectedDept.setName("admin");
		expectedDept.setLocation("main");

		when(mockDeptRepo.findById(expectedDept.getDept_id())).thenReturn(Optional.of(expectedDept));
		deptService.savedept(expectedDept);
		Department actualDepartment = new Department();
		try {
			actualDepartment = deptService.getBydeptid(2);

		} catch (Exception e) {
			assertFalse(expectedDept.getDept_id().equals(actualDepartment.getDept_id()));
		}
	}

	@Test // Department get by name //mockito
	void deptName() {
		Department expectedDept = new Department();
		expectedDept.setDept_id(1);
		expectedDept.setName("admin");
		expectedDept.setLocation("main");

		when(mockDeptRepo.getByName(expectedDept.getName())).thenReturn(expectedDept);

		deptService.savedept(expectedDept);
		Department actualDept = deptService.getByName(expectedDept.getName());
		assertEquals(expectedDept.getName(), actualDept.getName());
	}

	@Test //
	void deptnoname() {
		Department expectedDept = new Department();
		expectedDept.setDept_id(1);
		expectedDept.setName("admin");
		expectedDept.setLocation("main");
        
		when(mockDeptRepo.getByName(expectedDept.getName())).thenReturn(expectedDept);
        deptService.savedept(expectedDept);
		Department actualDept = new Department();
		try {

			actualDept = deptService.getByName("reddy");

		} catch (Exception e) {
			assertFalse(expectedDept.getName().equals(actualDept.getName()));
		}

	}

	@Test // Department get by location

	void deptLocation() {

		Department expectedDept = new Department();

		expectedDept.setDept_id(1);
		expectedDept.setName("admin");
		expectedDept.setLocation("main");
		List<Department> deptList = new ArrayList<Department>();
		deptList.add(expectedDept);
		when(mockDeptRepo.getByLocation(expectedDept.getLocation())).thenReturn(deptList);
		deptService.savedept(expectedDept);

		List<Department> actualDept = deptService.getByLocation(expectedDept.getLocation());

		assertEquals(deptList.size(), actualDept.size());
	}

	@Test //
	void deptNoLocation() {
		Department expectedDept = new Department();
		expectedDept.setDept_id(1);
		expectedDept.setName("admin");
		expectedDept.setLocation("main");
		List<Department> expectedList = new ArrayList<Department>();
		expectedList.add(expectedDept);
		when(mockDeptRepo.getByLocation(expectedDept.getLocation())).thenReturn(expectedList);
		deptService.savedept(expectedDept);
        
		List<Department> actualList = new ArrayList<Department>();
		
		try {
		
		actualList = deptService.getByLocation("location");

		} catch (Exception e) {
			assertTrue(actualList.isEmpty());
		}

	}
}
