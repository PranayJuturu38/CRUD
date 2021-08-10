package com.crudapp.main.serviceimpl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.crudapp.main.exception.CustomException;
import com.crudapp.main.model.FileData;
import com.crudapp.main.repository.FilesDatarepository;
import com.crudapp.main.service.FileService;

@Service
@Transactional
public class FileServiceImpl implements FileService {
    @Autowired
    private FilesDatarepository filerepo;
    
    @Override
    public FileData store(MultipartFile file) throws IOException{
        String fileName = StringUtils.cleanPath(file.getOriginalFilename()); 
        FileData FileData = new FileData(fileName,file.getContentType(),file.getBytes());
        return filerepo.save(FileData);
    }

    @Override
    public FileData getFile(String id) {
       return filerepo.findById(id).get();
    }

    @Override
    public Stream<FileData> getAllFile() {
     return filerepo.findAll().stream();
    }

}