package ref;

import java.lang.reflect.Field;

public class Test {
	private String str = "dps";

	public String getStr() {
		return str;
	}

	public static void main(String[] args) throws Exception {
		Test myTest = new Test();
		System.out.println(myTest.getStr());
		Class clazz = Test.class;
		Field field = clazz.getDeclaredField("str");
		field.setAccessible(true);
		field.set(myTest, "change");
		System.out.println(myTest.getStr());
	}
}