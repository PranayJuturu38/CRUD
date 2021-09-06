package com.crudapp.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.crudapp.main.model.Department;
import com.crudapp.main.service.DepartmentService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DepartmentTests {

	@Autowired
	private DepartmentService deptservice;

	@Test // POST Mapping "/departments" Happy Test Case
	void DeptSaved() {

		Department dept = new Department();
		dept.setDept_id(1);
		dept.setName("admin");
		dept.setLocation("main");

		Department savedDept = deptservice.savedept(dept);

		assertEquals(savedDept.getDept_id(), dept.getDept_id());
	}

	// @Test // POST Mapping "/departments" Unhappy Test Case
	// Department DeptNotSaved() throws Exception {
	// Department dept1 = new Department();
	// dept1.setDept_id(1);
	// dept1.setName("admin");
	// dept1.setLocation("main");

	// Department dept2 = new Department(1, "admin", "main");

	// Department savedDept1 = deptservice.savedept(dept1);
	// Department savedDept2 = deptservice.savedept(dept2);

	// try {
	// assertEquals(savedDept1.getDept_id(), savedDept2.getDept_id());
	// } catch (Exception e) {
	// throw e;
	// }
	// return savedDept2;
	// }

	@Test // Department get by id
	void deptId() {
		Department dept = new Department();
		dept.setDept_id(1);
		dept.setName("admin");
		dept.setLocation("main");

		assertEquals(1, dept.getDept_id());
	}

	@Test //
	void deptnoid() {
		Department dept = new Department();
		dept.setDept_id(1);
		dept.setName("admin");
		dept.setLocation("main");

		Object returneddept = new Department();
		try {
			deptservice.getBydeptid(2);

		} catch (Exception e) {
			assertFalse(returneddept == null, "Department not be found");
		}
	}

	@Test // Department get by name
	void deptName() {
		Department dept = new Department();
		dept.setDept_id(1);
		dept.setName("admin");
		dept.setLocation("main");

		assertEquals("admin", dept.getName());
	}

	@Test //
	void deptnoname() {
		Department dept = new Department();
		dept.setDept_id(1);
		dept.setName("admin");
		dept.setLocation("main");
		Object returneddept = new Department();

		try {
			deptservice.getByName("reddy");

		} catch (Exception e) {
			assertFalse(returneddept == null, "Department not be found");
		}

	}

	@Test // Department get by location

	void deptLocation() {

		Department dept = new Department();

		dept.setDept_id(1);
		dept.setName("admin");
		dept.setLocation("main");

		assertEquals("main", dept.getLocation());

	}

	@Test //
	void deptnolocation() {
		Department dept = new Department();
		dept.setDept_id(1);
		dept.setName("admin");
		dept.setLocation("main");
		Object returneddept = new Department();

		try {
			deptservice.getByLocation("location");

		} catch (Exception e) {
			assertFalse(returneddept == null, "Department not be found");
		}

	}
}
