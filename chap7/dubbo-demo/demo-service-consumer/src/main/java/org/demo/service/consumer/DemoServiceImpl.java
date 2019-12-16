package org.demo.service.consumer;

import org.apache.dubbo.config.annotation.Service;
import org.demo.common.DemoService;

@Service
public class DemoServiceImpl implements DemoService {

	@Override
	public String sayHello(String name) {
		return "hello, " + name;
	}
}