package com.crudapp.main.controllerTests;

import java.util.ArrayList;
import java.util.List;

import com.crudapp.main.controller.DepartmentController;
import com.crudapp.main.model.Department;
import com.crudapp.main.repository.Departmentrepository;
import com.crudapp.main.service.DepartmentService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

@WebMvcTest(controllers = DepartmentController.class)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public class DepartmentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private DepartmentService departmentService;
    
    @MockBean
    private Departmentrepository deptRepo;

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
        
        given(deptRepo.findAll()).willReturn(departmentList);

        this.mockMvc.perform(get("/departments"))
                
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(departmentList.size())))
                .andDo(document("{methodName}",
                           preprocessRequest(prettyPrint()),
                           preprocessResponse(prettyPrint())));
            }

    @Test
    void getDeptById() throws Exception {
        Department department = new Department(1,"HR","backgate");
        given(deptRepo.getById(department.getDeptId())).willReturn(department);
        
        this.mockMvc.perform(get("/departments/{id}",department.getDeptId()))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.name", is(department.getName())))
                    .andExpect(jsonPath("$.location", is(department.getLocation())))
                    .andDo(document("{methodName}",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint())));
     
    }
    @Test
    void getByName() throws Exception{
        Department department = new Department(1,"HR","backgate");
        given(deptRepo.getByName(department.getName())).willReturn(department);
   
        this.mockMvc.perform(get("/departments/name/{name}",department.getName()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(department.getName())))
        .andExpect(jsonPath("$.location", is(department.getLocation())))
        .andDo(document("{methodName}",
        preprocessRequest(prettyPrint()),
        preprocessResponse(prettyPrint())));


    }

    @Test
    void getByLocation() throws Exception{
        List<Department> departmentList = new ArrayList<Department>();
        Department department = new Department(1,"HR","backgate");
        Department department2 = new Department(2,"R","backgate");
        departmentList.add(department);
        departmentList.add(department2);
        String location = department.getLocation();
        given(deptRepo.getByLocation(location)).willReturn(departmentList);

        this.mockMvc.perform(get("/departments/locations/{location}",department.getLocation()))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.deptId",is(department.getDeptId())))
                    .andExpect(jsonPath("$.name", is(department.getName())))
        .andExpect(jsonPath("$.location", is(department.getLocation())));
    }

    @Test
    void getDeptByIdFailure() throws Exception {
        final Integer deptId = 4;
        given(deptRepo.findById(deptId)).willReturn(null);

        this.mockMvc.perform(get("/departments/{id}", deptId))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void shouldCreateNewDeptartment() throws Exception {
        Department department = new Department(1,"HR","backgate");
        given(deptRepo.save(department)).willReturn(department);
        String departmentJson=new ObjectMapper().writeValueAsString(department);
        this.mockMvc.perform(post("/departments"))
                    .andExpect(status().isOk())
                    .andDo(print());
}
}
