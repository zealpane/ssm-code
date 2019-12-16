package dps.bean;
public class GlassCup implements ICup {
	//杯子的颜色
	private String color;
	
	public void setColor(String color) {
		this.color = color;
	}
	@Override
	public void fillWater() {
		System.out.println("使用"+this.color+"颜色的玻璃杯喝水。");
	}
}