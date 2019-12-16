package designMode.demo.cmd;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SwitchLight {

	public void turnOnLight() {
		log.info("开灯");
	}
	public void turnOffLight() {
		log.info("关灯");
	}
}
