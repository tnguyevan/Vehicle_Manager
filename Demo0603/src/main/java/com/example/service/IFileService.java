package com.example.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileService {

	String uploadImage(MultipartFile image) throws IOException;

}
