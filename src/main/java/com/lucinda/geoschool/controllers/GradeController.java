package com.lucinda.geoschool.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.lucinda.geoschool.models.Grade;
import com.lucinda.geoschool.models.Student;
import com.lucinda.geoschool.repositories.StudentRepository;

@Controller
public class GradeController {

	@Autowired
	private StudentRepository sr;
	
	@GetMapping("/grade/register/{id}")
	public String register(@PathVariable String id, Model model) {
		Student student = sr.getById(id);
		model.addAttribute("student", student);
		model.addAttribute("grade", new Grade());
		return "grade/register";
	}
	
	@PostMapping("/grade/create/{id}")
	public String create(@PathVariable String id, @ModelAttribute Grade grade) {
		Student student = sr.getById(id);
		student.addGrade(grade);
		sr.update(student);
		return "redirect:/student/list";
		
	}
}
