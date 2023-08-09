package com.example.MultipleFilesUpload.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
@Service
public class FileUploadServiceImpl implements FileUploadService{
	public final Path rootDir = Paths.get("uploads");

	@Override
	public void init() {
		// TODO Auto-generated method stub
	
			try {
				File tempDir = new File(rootDir.toUri());
				boolean dirExists = tempDir.exists();
				if(!dirExists) {
				Files.createDirectory(rootDir);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}

	@Override
	public void save(MultipartFile file) {
		// TODO Auto-generated method stub
		try {
			Files.copy(file.getInputStream(), this.rootDir.resolve(file.getOriginalFilename()));
			
		}catch(Exception e) {
			throw new RuntimeException("Error uploading files");
		}
		
	}

	@Override
	public Resource getFileByName(String fileName) {
		// TODO Auto-generated method stub
		try {
			Path filePath = rootDir.resolve(fileName);
			Resource resource= new UrlResource(filePath.toUri());
			if(resource.exists() && resource.isReadable()) {
				return resource;
			}else {
				throw new RuntimeException("Could not read file");
			}
		}catch(MalformedURLException mal) {
			throw new RuntimeException("Error: "+mal.getMessage());
		}
		
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		FileSystemUtils.deleteRecursively(rootDir.toFile());
	}

	@Override
	public Stream<Path> loadAllFiles() {
		// TODO Auto-generated method stub
		try {
			return Files.walk(this.rootDir, 1).filter(path->!path.equals(this.rootDir));
		}catch(IOException e) {
			throw new RuntimeException("Could not load");
		}
		
	}

}
