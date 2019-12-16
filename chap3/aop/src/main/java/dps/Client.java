package dps;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import dps.service.UserService;

//测试代码
public class Client {
	public static void main(String[] args) {
		XmlBeanFactory factory = 
			new XmlBeanFactory(new ClassPathResource("applicationContext.xml"));
		     UserService p = (UserService)factory.getBean("test");
			p.view();
			p.modify();
	}
}