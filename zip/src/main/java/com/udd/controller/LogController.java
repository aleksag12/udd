package com.udd.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.udd.service.LocationService;

@RestController
@RequestMapping(value = "/api/logs", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:8070", maxAge = 3600, allowedHeaders = "*")
public class LogController {
	
	@Autowired
	private LocationService locationService;
	
	private static final Logger logger = LoggerFactory.getLogger(LogController.class.getName());

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> log(@RequestParam(required = true) double lat, @RequestParam(required = true) double lon) {
		try {
			String place = locationService.reverseGeocoding(lat, lon);
			if (place == null || place.equals("")) {
				return new ResponseEntity<>("Nepostojeca lokacija.", HttpStatus.BAD_REQUEST);
			}
			logger.info("Neko je aplicirao za posao. City=" + place);
			return new ResponseEntity<>("Zabelezeno.", HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Nepostojeca lokacija.", HttpStatus.BAD_REQUEST);
		}
	}
	
}
