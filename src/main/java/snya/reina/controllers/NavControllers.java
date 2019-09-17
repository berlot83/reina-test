package snya.reina.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavControllers {
	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/index")
	public String index2() {
		return "index";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/Rol")
	public String rol() {
		return "Rol";
	}
	
	@GetMapping("/rolconsulta")
	public String rolconsulta() {
		return "rolconsulta";
	}
	
	@GetMapping("/swagger-ui.html#!")
	public String swagger() {
		return"swagger-ui.html#!";
	}
	
	
}
