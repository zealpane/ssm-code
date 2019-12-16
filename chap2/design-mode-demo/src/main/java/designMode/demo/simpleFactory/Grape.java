package designMode.demo.simpleFactory;

public class Grape implements FruitIF {
	public void grow() {
		log("Grape is growing...");
	}

	public void harvest() {
		log("Grape has been harvested.");
	}

	public void plant() {
		log("Grape has been planted.");
	}

	public static void log(String msg) {
		System.out.println(msg);
	}

	public boolean getSeedful() {
		return seedful;
	}

	public void setSeedful(boolean seedful) {
		this.seedful = seedful;
	}

	private boolean seedful;
}