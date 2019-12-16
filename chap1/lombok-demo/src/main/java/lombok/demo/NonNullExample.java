package lombok.demo;

import lombok.Data;
import lombok.NonNull;

@Data
public class NonNullExample {
	@NonNull
	private String name;
    private int age;
    
    public static void main(String[] args) {
		NonNullExample n = new NonNullExample(null);
	}
}

class NoNonNullExample {
	private String name;
    private int age;
    
    public void setName(String name) {
    	if (name == null) {
    		throw new NullPointerException("name is marked @NonNull but is null");
    	}
    	this.name = name;
    }
}