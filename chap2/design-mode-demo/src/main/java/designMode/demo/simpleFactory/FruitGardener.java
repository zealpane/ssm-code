package designMode.demo.simpleFactory;

public class FruitGardener {
	public FruitIF factory(String which) throws BadFruitException {
		if (which.equalsIgnoreCase("apple")) {
			return new Apple();
		} else if (which.equalsIgnoreCase("strawberry")) {
			return new Strawberry();
		} else if (which.equalsIgnoreCase("grape")) {
			return new Grape();
		} else {
			throw new BadFruitException("Bad fruit request");
		}
	}
}