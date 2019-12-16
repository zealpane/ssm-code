package lombok.demo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * 在类上添加Getter、Setter注解
 *
 */
public class MethodGetterSetter {

	@Getter
	@Setter
	private String name;
	
	@Getter(value = AccessLevel.PUBLIC)
    private int age;
}
