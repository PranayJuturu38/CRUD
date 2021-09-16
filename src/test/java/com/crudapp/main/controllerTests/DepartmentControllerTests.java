package com.crudapp.main.controllerTests;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.crudapp.main.controller.DepartmentController;
import com.crudapp.main.model.Department;
import com.crudapp.main.service.DepartmentService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DepartmentController.class)
public class DepartmentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private DepartmentService deptService;

    List<Department> departmentList = new ArrayList<Department>();

    @BeforeEach
    void setUp(){
        this.departmentList = new ArrayList<Department>();
        this.departmentList.add(new Department(1,"HR","backgate"));
        this.departmentList.add(new Department(2,"R&D","main"));
        this.departmentList.add(new Department(3,"Admin","main"));
    }

    @Test
    void getAllDepts() throws Exception {
        
        given(deptService.getAllDept()).willReturn(departmentList);

        this.mockMvc.perform(get("/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(departmentList.size())));
    }

    @Test
    void getDeptById() throws Exception {
        Department department = new Department(1,"HR","backgate");
        given(deptService.getByDeptId(department.getDeptId())).willReturn(department);
        
        this.mockMvc.perform(get("/departments/{id}",department.getDeptId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", is(department.getName())))
                    .andExpect(jsonPath("$.location", is(department.getLocation())));
    }
    @Test
    void getByName() throws Exception{
        Department department = new Department(1,"HR","backgate");
        given(deptService.getByName(department.getName())).willReturn(department);
   
        this.mockMvc.perform(get("/departments/name/{name}",department.getName()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(department.getName())))
        .andExpect(jsonPath("$.location", is(department.getLocation())));

    }

    @Test
    void getByLocation() throws Exception{
        List<Department> departmentList = new ArrayList<Department>();
        Department department = new Department(1,"HR","backgate");
        departmentList.add(department);
        String location = department.getLocation();
        given(deptService.getByLocation(location)).willReturn(departmentList);

        this.mockMvc.perform(get("/departments/locations/{location}",department.getLocation()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.deptId",is(department.getDeptId())))
                    .andExpect(jsonPath("$.name", is(department.getName())))
        .andExpect(jsonPath("$.location", is(department.getLocation())));
    }

    @Test
    void getDeptByIdFailure() throws Exception {
        final Integer deptId = 4;
        given(deptService.getByDeptId(deptId)).willReturn(null);

        this.mockMvc.perform(get("/departments/{id}", deptId))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void shouldCreateNewDeptartment() throws Exception {
        given(deptService.saveDept(any(Department.class))).willAnswer((invocation) -> invocation.getArgument(0));

        Department department = new Department(1,"HR","backgate");
        
        this.mockMvc.perform(post("/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(department)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(department.getName())))
                .andExpect(jsonPath("$.location", is(department.getLocation())))
                ;
    }
}
