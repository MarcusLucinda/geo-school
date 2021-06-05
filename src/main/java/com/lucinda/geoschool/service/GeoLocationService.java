package com.lucinda.geoschool.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;


import com.lucinda.geoschool.models.Contact;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import com.google.maps.model.LatLng;
@Service
public class GeoLocationService {

	public List<Double> getCoord(Contact contact) throws ApiException, InterruptedException, IOException{
		GeoApiContext context = new GeoApiContext.Builder().apiKey("API_KEY").build();
		GeocodingApiRequest request = GeocodingApi.newRequest(context).address(contact.getAddress());
		GeocodingResult[] results = request.await();
		GeocodingResult result = results[0];
		Geometry geometry = result.geometry;
		LatLng location = geometry.location;
		List<Double> coordinates = Arrays.asList(location.lat, location.lng);
		return coordinates;
	}
}
