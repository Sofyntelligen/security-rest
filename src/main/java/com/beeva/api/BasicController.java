package com.beeva.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beeva.Person;

@RestController
public class BasicController {

	@GetMapping("/")
	public Person indexGet() {		
		return new Person(1,"Mario","Armenta");
	}
	
	@GetMapping("/1")
	public Person unoGet() {
		return new Person(1,"Mario","Armenta");
	}
	
}
