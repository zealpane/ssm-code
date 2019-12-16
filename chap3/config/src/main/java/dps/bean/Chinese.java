package dps.bean;
//中国人
public class Chinese implements IPerson{
	private String name;
	private ICup cup;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ICup getCup() {
		return cup;
	}
	public void setCup(ICup cup) {
		this.cup = cup;
	}
	@Override
	public void sayHello()
	{
		System.out.println(name+"说，你好");
	}
	@Override
	public void drink() {
		this.cup.fillWater();
	}
}
 
//美国人
