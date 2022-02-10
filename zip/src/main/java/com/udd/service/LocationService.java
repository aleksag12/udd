package com.udd.service;

import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.udd.model.Location;

@Service
public class LocationService {

	private static final String API_KEY = "pk.455391702df4d0827a840a7e14ce15aa";
    private static final String API_URL = "https://us1.locationiq.com/v1/";
    private static final String FORWARD_URL = "search.php";
    private static final String REVERSE_URL = "reverse.php";

    public GeoPoint forwardGeocoding(String place) throws Exception {
    	RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Location[]> locationResponse = restTemplate.getForEntity(API_URL + FORWARD_URL + "?key=" + API_KEY + "&q=" + place + "&format=json", Location[].class);
            double lat = locationResponse.getBody()[0].getLat();
            double lon = locationResponse.getBody()[0].getLon();
            return new GeoPoint(lat, lon);
        } catch (Exception e) {
            throw new Exception("Uneli ste nepostojecu lokaciju.");
        }
    }

    public String reverseGeocoding(double lat, double lon) throws Exception {
    	try {
    		RestTemplate restTemplate = new RestTemplate();
    		ResponseEntity<Response> locationResponse = restTemplate.getForEntity(API_URL + REVERSE_URL + "?key=" + API_KEY + "&lat=" + lat + "&lon=" + lon + "&format=json", Response.class);
            return locationResponse.getBody().city.city;
    	} catch (Exception e) {
            throw new Exception("Uneli ste nepostojecu lokaciju.");
        }
    }
    
    public class Response {
    	public City city;
    }
    
    public class City {
    	public String city;
    }
}
