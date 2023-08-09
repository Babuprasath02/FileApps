package com.example.MultipleFilesUpload.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.SSLEngineResult.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.example.MultipleFilesUpload.service.FileUploadService;

@RestController
public class FileUploadController {
	@Autowired
	FileUploadService fileUploadService;
	@PostMapping("/upload-files")
	public ResponseEntity<FileResponseMessage> uploadFiles(@RequestParam("file") MultipartFile[] files){
		String message = null;
		try {
			List<String> fileNames = new ArrayList<>();
			
			Arrays.stream(files).forEach(file->{fileUploadService.save(file);
			fileNames.add(file.getOriginalFilename());
			});
			message ="File(s) uploaded successfully"+fileNames;
			return  ResponseEntity.status(HttpStatus.OK).body(new FileResponseMessage(message));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new FileResponseMessage(e.getMessage()));
		}
		
	}
	@GetMapping("/file/{fileName}")
	public ResponseEntity<Resource> getFileByName(@PathVariable String fileName){
		Resource resource = fileUploadService.getFileByName(fileName);
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName=\""+resource.getFilename()+"\"").body(resource);
	}
	
	@GetMapping("/all-files")
	public ResponseEntity<List<FileResponse>> loadAllFiles(){
		List<FileResponse> files = fileUploadService.loadAllFiles().map(path->{
			String fileName = path.getFileName().toString();
			String url = MvcUriComponentsBuilder.fromMethodName(FileUploadController.class, "getFileByName",path.getFileName().toString()).build().toString();
			return new FileResponse(fileName,url);
		}).toList();
		return ResponseEntity.status(HttpStatus.OK).body(files);
		
	}
	@GetMapping("/deleteAll")
	public void delete() {
		fileUploadService.deleteAll();
	}
}
