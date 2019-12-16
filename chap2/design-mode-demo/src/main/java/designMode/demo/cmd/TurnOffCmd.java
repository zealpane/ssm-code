package designMode.demo.cmd;

public class TurnOffCmd implements Cmd {

	SwitchLight s;
	
	public void switchStatus(SwitchLight s) {
		this.s = s;
	}
	
	@Override
	public void execute() {
		this.s.turnOnLight();
	}

}
