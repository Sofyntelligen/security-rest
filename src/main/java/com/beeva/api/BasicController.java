package com.beeva.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.beeva.authentication.JwtUtility;
import com.beeva.authentication.model.JwtUserData;
import com.beeva.model.Person;

@RestController
public class BasicController {

	@Autowired
	private JwtUtility theJwtUtiliy;
	
	@GetMapping("/")
	public Person indexGet() {		
		return new Person(1,"Mario","Armenta");
	}
	
	@GetMapping("/1")
	public Person unoGet() {
		return new Person(1,"Mario","Armenta");
	}
	
	@PostMapping("/login")
	public String generate(@RequestBody JwtUserData theUser) {
		return theJwtUtiliy.generateToken(theUser); 
	}
}
