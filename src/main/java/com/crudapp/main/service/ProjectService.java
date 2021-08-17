package com.crudapp.main.service;

import java.util.List;

import com.crudapp.main.model.Project;

import org.springframework.stereotype.Service;

@Service
public interface ProjectService {
    List<Project> getAllproj();

    Project saveproject(Project project);

    Project getProjectById(Integer id);
    
    void delete(Integer project_id);

    Project updateproject(Project project);

    Project getByName(String name);
}