package hello.spring.boot.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("s")
@Getter
@Setter
public class UserBean {
	private String name;
	private int age;
}
