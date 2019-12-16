package designMode.demo.cmd;

public class CmdTest {

	public static void main(String[] args) {
		SwitchLight s = new SwitchLight();
		
		TurnOffCmd turnOff = new TurnOffCmd();
		turnOff.switchStatus(s);
		turnOff.execute();
		
		TurnOnCmd turnOn = new TurnOnCmd();
		turnOn.switchStatus(s);
		turnOn.execute();
	}
}
