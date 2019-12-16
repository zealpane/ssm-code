package lombok.demo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Slf4jTest {

	public void test() {
		log.debug("这里输出调试信息");
	}
}
