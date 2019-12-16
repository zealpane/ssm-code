package designMode.demo.abstractFactory;

public class Creator1 extends AbstractCreator {
	// 只生产产品等级为1的A产品
	public AbstractProductA createProductA() {
		return new ProductA1();
	}

	// 只生产产品等级为1的B产品
	public AbstractProductB createProductB() {
		return new ProductB1();
	}
}