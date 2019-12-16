package designMode.demo.factoryMethod;

public class ConcreteCreateor extends Creator {
	public <T extends Product> T createProduct(Class<T> c) {
		Product product = null;
		try {
			product = (Product) Class.forName(c.getName()).newInstance();
		} catch (Exception e) {
			// 异常处理
		}
		return (T) product;
	}
}