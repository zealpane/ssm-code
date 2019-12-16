package lombok.demo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class ConstructorTest {

	@NoArgsConstructor
	public class NoArgs {
		private String name;
		private String age;
	}
	
	@AllArgsConstructor
	public class AllArgs {
		private String name;
		private int age;
	}
	
	public static void main(String[] args) {
		ConstructorTest c = new ConstructorTest();
		// 调用无参构造
		NoArgs noArgs = c.new NoArgs();
		AllArgs allArgs = c.new AllArgs("段鹏松", 20);
	}
}
