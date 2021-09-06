package com.crudapp.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.crudapp.main.exception.CustomException;
import com.crudapp.main.model.FileData;
import com.crudapp.main.repository.FilesDatarepository;
import com.crudapp.main.service.FileService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FileTestCases {

	@Autowired
	private FilesDatarepository fileRepository;

	FileService mock = org.mockito.Mockito.mock(FileService.class);

	// Saving File happy testcase
	@Test
	public void saveFile() throws IOException {

		Path path = Paths.get("C:/Users/Dev/Documents/Kpi Stuff/test.txt");
		byte[] data = Files.readAllBytes(path);

		FileData fd = new FileData("test.txt", "text/plain", data);

		FileData fd1 = fileRepository.save(fd);
		assertEquals("text/plain", fd1.getType());

	}

	// Getting the file happy test
	@Test
	public void getFileTest() throws IOException {
		Path path = Paths.get("C:/Users/Dev/Documents/Kpi Stuff/test.txt");
		byte[] data = Files.readAllBytes(path);

		when(mock.getFile("C:/Users/Dev/Documents/Kpi Stuff/test.txt"))
				.thenReturn(new FileData("test.txt", "text/plain", data));

		FileData file = mock.getFile("C:/Users/Dev/Documents/Kpi Stuff/test.txt");
		assertEquals("text/plain", file.getType());
	}

	@Test
	public void getFileNameTest() throws IOException {
	
		when(mock.getByName("test.txt")).thenThrow(new CustomException("File not found"));

		FileData file = mock.getByName("test.txt");
		
		
	}
}
