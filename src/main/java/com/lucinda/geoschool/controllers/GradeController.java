package com.lucinda.geoschool.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@GetMapping("/grade/searchgrade")
	public String search() {
		return "grade/search";
	}
	
	@GetMapping("/grade/search")
	public String search(@RequestParam("status") String status, @RequestParam("cut") String cut, Model model) {
		List<Student> students = sr.searchByGrade(status, Double.parseDouble(cut));
		model.addAttribute("students", students);
		return "grade/search";
	}
}
