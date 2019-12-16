package org.validator;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserBean {
	@NotBlank(message = "用户名不能为空")
	private String name;

	@Min(0)
	@Max(9999)
	private int age;
}