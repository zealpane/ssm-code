package dps.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("Bean后处理器在初始化之前对" + beanName + "进行增强处理");
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("Bean后处理器在初始化之后对" + beanName + "进行增强处理");
		// 如果该bean是Person类的实例，则改变其属性值
		if (bean instanceof Person) {
			Person p = (Person) bean;
			p.setName("段鹏松");
		}
		return bean;
	}
}