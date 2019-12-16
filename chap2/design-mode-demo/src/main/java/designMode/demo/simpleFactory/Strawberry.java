package designMode.demo.simpleFactory;

public class Strawberry implements FruitIF {
	public void grow() {
		log("Strawberry is growing...");
	}

	public void harvest() {
		log("Strawberry has been harvested.");
	}

	public void plant() {
		log("Strawberry has been planted.");
	}

	public static void log(String msg) {
		System.out.println(msg);
	}
}