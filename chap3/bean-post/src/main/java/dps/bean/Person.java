package dps.bean;

import org.springframework.beans.factory.InitializingBean;

public class Person implements InitializingBean {
	private String name;

	public void setName(String name) {
		System.out.println("Spring 执行依赖关系注入------setName方法");
		this.name = name;
	}

	public Person() {
		System.out.println("Spring 实例化bean :Person bean实例------Person构造函数");
	}

	public void information() {
		System.out.print("这个人的名字是：" + name);
	}

	public void init() {
		System.out.println("正在执行初始化 ----------- init方法");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("正在执行 ----------- afterPropertiesSet方法");
	}
}