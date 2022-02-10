package com.udd.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {
	private String firstName;
	private String lastName;
	private String email;
	private String city;
	private String country;
	private String education;
	private MultipartFile cv;
}
