package selfTest;

public class Assembler {
	
	
//	private FileEncryptor fileEnc;
//	
//	//FileEncryptor 클래스와 같은 enc가 필요한 다른 클래스
//	private OtherClass other;
//	
//	
//	private Encryptor enc;
//	
	
	private A a;
	private B b;
	
	public Assembler() {
		
		
		b = new C();
		a = new A(b);
		
//		//첫예제의 enc는 Encryptor()를 매개변수로 받아 씀
//		//enc = new Encryptor();
//		
//		//얘를 보여주면서 다른 객체가 받을때
//		enc = new FastEncryptor();
//		
//		//얘는 기존에 그냥 Encryptor();를 받았었음
//		fileEnc = new FileEncryptor(enc);
//		
//		//얘는 기존에 Encryptor();를 받아쓰다가 
//		//FastEncryptor();를 받고싶어서 위에 선언해서 고쳐줌
//		//이걸 설명하면서 Encryptor 객체를 DI로 전달받는 코드는 영향을 받지 않는다함
//		other = new OtherClass(enc);
	}
}
