package selfTest;

public class A {
	private B b;
	
	//의존하는 타입의 객체를 생성자의 파라미터를 통해서 전달받음
	public A(B b) {
		this.b = b;
	}
}


