package proxy.demo;

import org.junit.Test;

import proxy.demo.cglib.CglibProxy;
import proxy.demo.cglib.UserServiceImpl;

public class CglibTest {

	@Test
	public void test() {
		CglibProxy cglibProxy = new CglibProxy();
		UserServiceImpl p = (UserServiceImpl) cglibProxy.newProxyObj(UserServiceImpl.class);
		p.sayHi();
		System.out.println();
		p.sayHello();
	}
}
