package designMode.demo.cmd;

public class TurnOnCmd implements Cmd {

	SwitchLight s;

	public void switchStatus(SwitchLight s) {
		this.s = s;
	}

	@Override
	public void execute() {
		this.s.turnOffLight();
	}

}
