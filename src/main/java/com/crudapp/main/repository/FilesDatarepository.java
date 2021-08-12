package com.crudapp.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.crudapp.main.exception.CustomException;
import com.crudapp.main.model.FileData;
@Repository
public interface FilesDatarepository extends JpaRepository<FileData, String> {

    @Query("SELECT f FROM FileData f WHERE f.name = :name")
    public FileData getByName(@Param("name") String name);
}