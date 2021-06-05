package com.lucinda.geoschool.controllers;


import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.maps.errors.ApiException;
import com.lucinda.geoschool.models.Student;
import com.lucinda.geoschool.repositories.StudentRepository;
import com.lucinda.geoschool.service.GeoLocationService;

@Controller
public class StudentController {
	
	@Autowired
	private StudentRepository sr;
	@Autowired
	private GeoLocationService gs;
	
	@GetMapping("/student/register")
	public String register(Model model) {
		model.addAttribute("student", new Student());
		return "student/register";
	}
	
	@PostMapping("/student/create")
	public String create(@ModelAttribute Student student) {
		try {
			List<Double> coordinates = gs.getCoord(student.getContact());
			student.getContact().setCoordinates(coordinates);
			sr.create(student);
		} catch (ApiException | InterruptedException | IOException e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}
	
	@GetMapping("/student/list")
	public String list(Model model) {
		List<Student> students = sr.listAll();
		model.addAttribute("students", students);
		return "student/list";
	}
	
	@GetMapping("/student/info/{id}")
	public String infos(@PathVariable String id, Model model) {
		Student student = sr.getById(id);
		model.addAttribute("student", student);
		return "student/info";
	}
	
	@GetMapping("/student/searchname")
	public String searchName() {
		return "student/searchname";
	}
	
	@GetMapping("/student/search")
	public String search(@RequestParam("name") String name, Model model) {
		List<Student> students = sr.searchByName(name);
		model.addAttribute("students", students);
		return "student/searchname";
	}

}
