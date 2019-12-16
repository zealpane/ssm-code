package proxy.demo;

import org.junit.Test;

import proxy.demo.jdk.DeviceProxy;
import proxy.demo.jdk.DeviceService;
import proxy.demo.jdk.DeviceServiceImpl;

public class JdkReflectTest {

	@Test
	public void newProxy() {
		
		DeviceServiceImpl deviceServiceImpl = new DeviceServiceImpl();
		DeviceProxy h = new DeviceProxy();
		DeviceService dService = (DeviceService) h.newProxy(deviceServiceImpl);
		dService.online();
		System.out.println();
		dService.offline();
	}
}
