package bubble.test.ex00;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

class Dog{
	private String name; // 상태는 행위를 통해서  자바에서 모든 변수는 private로	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}

public class LombokTest {

	public static void main(String[] args) {
		Dog d = new Dog();
		System.out.println(d.getName());

	}

}
