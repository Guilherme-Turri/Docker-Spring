package com.apirestdocker.course.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HelloCotroller {
	@GetMapping(value="/hello")
	public String hello() {
		return "Hello, user";
	}
}