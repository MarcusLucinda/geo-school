package com.lucinda.geoschool.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.lucinda.geoschool.models.Skill;
import com.lucinda.geoschool.models.Student;
import com.lucinda.geoschool.repositories.StudentRepository;

@Controller
public class SkillController {
	
	@Autowired
	private StudentRepository sr;

	@GetMapping("/skill/register/{id}")
	public String register(@PathVariable String id, Model model) {
		Student student = sr.getById(id);
		model.addAttribute("student", student);
		model.addAttribute("skill", new Skill());
		return "skill/create";
	}
	
	@PostMapping("/skill/create/{id}")
	public String create(@PathVariable String id, @ModelAttribute Skill skill) {
		Student student = sr.getById(id);
		student = student.addSkill(skill);
		sr.update(student);
		return "redirect:/student/list";
	}
}
