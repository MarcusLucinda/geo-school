package com.lucinda.geoschool.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lucinda.geoschool.models.Student;
import com.lucinda.geoschool.repositories.StudentRepository;

@Controller
public class GeoLocationController {

	@Autowired
	private StudentRepository sr;
	
	@GetMapping("/geolocation/searchloc")
	public String geoSelect(Model model) {
		List<Student> students = sr.listAll();
		model.addAttribute("students", students);
		return "geolocation/search";
	}
	
	@GetMapping("/geolocation/search")
	public String search(@RequestParam("studentId") String id, Model model) {
		Student student = sr.getById(id);
		List<Student> regionStudents = sr.searchByGeo(student);
		model.addAttribute("regionStudents", regionStudents);
		return "geolocation/search";
	}
}
