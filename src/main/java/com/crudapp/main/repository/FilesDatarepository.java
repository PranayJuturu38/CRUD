package com.crudapp.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import com.crudapp.main.model.FileData;

@Repository
public interface FilesDatarepository extends JpaRepository<FileData, String> {

    @Query("SELECT f FROM FileData f WHERE f.name = ?1")
    public FileData getByName(String name);
}