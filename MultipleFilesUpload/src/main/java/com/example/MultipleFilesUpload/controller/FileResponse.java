package com.example.MultipleFilesUpload.controller;

public class FileResponse {
	private String fileName;
	private String url;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public FileResponse(String fileName, String url) {
		super();
		this.fileName = fileName;
		this.url = url;
	}
	
	

}
