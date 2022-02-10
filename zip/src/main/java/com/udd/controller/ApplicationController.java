package com.udd.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.udd.dto.ApplicationDTO;
import com.udd.dto.CVRequestDTO;
import com.udd.service.ApplicationService;
import com.udd.service.FileService;

@RestController
@RequestMapping("/api/application")
@CrossOrigin(origins = "http://localhost:8070", maxAge = 3600, allowedHeaders = "*")
public class ApplicationController {

	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private FileService fileService;
	
	@PostMapping(value = "/apply", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> applyForJob(@ModelAttribute ApplicationDTO jobApplication) {
		try {
			return new ResponseEntity<>(applicationService.upload(jobApplication), HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@RequestMapping(value = "/cv", method = RequestMethod.POST)
    public ResponseEntity<?> getApplicationCv(@RequestBody CVRequestDTO dto) {
        byte[] content;
		try {
			content = fileService.readFile(dto.getUrl());
			return new ResponseEntity<>(content, HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
    }

	
}
