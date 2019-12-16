package lombok.demo;

import lombok.Builder;
import lombok.Getter;

@Builder
public class BuilderDemo {
	@Getter
	private String name;
	@Getter
    private int age;
    
    public static void main(String[] args) {
		BuilderDemo b = BuilderDemo.builder()
			.name("段鹏松")
			.age(18)
			.build();
		System.out.println("姓名：" + b.getName());
		System.out.println("年龄：" + b.getAge());
	}
}
