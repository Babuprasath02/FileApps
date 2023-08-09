package com.example.MultipleFilesUpload;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.MultipleFilesUpload.service.FileUploadService;

@SpringBootApplication
public class MultipleFilesUploadApplication implements CommandLineRunner{
	
	@Resource
	private FileUploadService fileUploadService;

	public static void main(String[] args) {
		SpringApplication.run(MultipleFilesUploadApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		fileUploadService.init();
		
	}

}
