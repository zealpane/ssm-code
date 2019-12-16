package lombok.demo;

import lombok.Getter;
import lombok.Setter;

/**
 * 在类上添加Getter、Setter注解
 *
 */
@Getter
@Setter
public class ClassGetterSetter {
	private String name;
	private int age;
}
