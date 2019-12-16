package designMode.demo.simpleFactory;

public class Apple implements FruitIF {
	public void grow() {
		log("Apple is growing...");
	}

	public void harvest() {
		log("Apple has been harvested.");
	}

	public void plant() {
		log("Apple has been planted.");
	}

	public static void log(String msg) {
		System.out.println(msg);
	}

	public int getTreeAge() {
		return treeAge;
	}

	public void setTreeAge(int treeAge) {
		this.treeAge = treeAge;
	}

	private int treeAge;
}