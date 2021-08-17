package com.crudapp.main.repository;

import com.crudapp.main.model.Project;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Projectrepository extends JpaRepository<Project, Integer> {

}
