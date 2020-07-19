package org.demo.service.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;

/**
 * Hello world!
 *
 */
@NacosPropertySource(dataId = "example", autoRefreshed = true)
@SpringBootApplication
public class App 
{
	public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
