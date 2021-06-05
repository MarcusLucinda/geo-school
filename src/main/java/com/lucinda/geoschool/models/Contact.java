package com.lucinda.geoschool.models;

import java.util.List;

public class Contact {

	private String address;
	private List<Double> coordinates;
	private String type = "Point";
	
	public Contact() {}
	
	public Contact(String address, List<Double> coordinates) {
		this.address = address;
		this.coordinates = coordinates;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<Double> getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(List<Double> coordinates) {
		this.coordinates = coordinates;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
