package spring.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		// 读取Spring配置文件
		ApplicationContext act = new ClassPathXmlApplicationContext("applicationContext.xml");
		// 从Spring容器中获取id为p1的bean
		Person p1 = act.getBean("p1", Person.class);
		p1.information();
	}
}
