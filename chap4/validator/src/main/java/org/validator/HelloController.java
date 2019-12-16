package org.validator;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

	@GetMapping("/welcome")
	public String welcome() {
		return "hello, Welcome";
	}
	
	@PostMapping("/user")
	public String createUser(@RequestBody @Valid UserBean u) {
		return "请求成功";
	}
	
	@PostMapping("/user2")
	public String createUser2(@RequestBody @Valid UserBean u, 
			BindingResult ret) {
		if(ret.hasErrors()){
            for (ObjectError error : ret.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }
            return "参数校验未通过";
        } else {        	
        	return "请求成功";
        }
	}
}
