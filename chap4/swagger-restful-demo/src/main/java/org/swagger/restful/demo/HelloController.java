package org.swagger.restful.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

	@GetMapping("/welcome")
	public String welcome(String name) {
		return "hello, Welcome, " + name;
	}
}
