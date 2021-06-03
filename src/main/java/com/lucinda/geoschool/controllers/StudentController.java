package com.lucinda.geoschool.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.lucinda.geoschool.models.Student;
import com.lucinda.geoschool.repositories.StudentRepository;

@Controller
public class StudentController {
	
	@Autowired
	private StudentRepository sr;
	
	@GetMapping("/student/register")
	public String register(Model model) {
		model.addAttribute("student", new Student());
		return "student/register";
	}
	
	@PostMapping("/student/create")
	public String create(@ModelAttribute Student student) {
		sr.create(student);
		return "redirect:/";
	}

}
