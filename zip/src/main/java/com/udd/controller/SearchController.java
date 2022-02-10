package com.udd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.udd.dto.GeoLocationRequest;
import com.udd.dto.SearchRequestDTO;
import com.udd.lucene.service.SearchService;

@RestController
@RequestMapping(value = "/api/search")
@CrossOrigin(origins = "http://localhost:8070", maxAge = 3600, allowedHeaders = "*")
public class SearchController {

	@Autowired
	private SearchService searchService;

	@RequestMapping(value = "/simple-search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> simpleSearch(@RequestParam String criterion, Pageable pageable) {

		try {
			return new ResponseEntity<>(searchService.simpleSearch(criterion, pageable), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/advanced-search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> advancedSearch(@RequestBody SearchRequestDTO request, Pageable pageable) {
		try {
			return new ResponseEntity<>(searchService.advancedSearch(request.getFields(), pageable), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/geo-search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> geoSearch(@RequestBody GeoLocationRequest request, Pageable pageable) {

		try {
			return new ResponseEntity<>(searchService.geoLocationSearch(request, pageable), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
}
