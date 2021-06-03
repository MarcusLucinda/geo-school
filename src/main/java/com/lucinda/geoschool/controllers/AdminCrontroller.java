package com.lucinda.geoschool.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminCrontroller {
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	

}
