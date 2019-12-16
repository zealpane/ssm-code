package designMode.demo;

public class SatietySingletonTest {
	public static void main(String[] args) {
		SatietySingleton s1 = SatietySingleton.getInstance();
		SatietySingleton s2 = SatietySingleton.getInstance();
		StringBuilder sr = new StringBuilder("两次调用的是否是同一个实例？").append("\n");
		sr.append("答：");
		if (s1 == s2) {
			sr.append("是");
		} else {
			sr.append("不是");
		}
		System.out.println(sr);
	}
}

/**
 * 单例类
 */
class SatietySingleton {
	
	private static final SatietySingleton instance = new SatietySingleton();
	
	// 限制产生多个对象
	private SatietySingleton() {
	}
	
	// 通过该方法获得实例对象
	public static SatietySingleton getInstance() {
		return instance;
	}
	
	public String getRet() {
		return "sington";
	}
	
	// 类中其他方法，尽量是static
	public static void doSomething() {
	}
}