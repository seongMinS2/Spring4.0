# DI(DependencyInjection)와 스프링

------

Di는 Dependency Injection의 약자로서, 우리말로는 '의존 주입'이라는 단어로 번역되어 사용된다.

'의존'이라는 단어에서 알 수 있듯이 DI는 의존을 처리하는 방법에 대한 내용이다.

스프링은 기본적으로 DI를 기반으로 동작하기 때문에, 스프링을 잘 사용하려면 DI에 대한 이해가 필수적이다.

### 의존(Dependency)이란?

특정 기능을 실행하기 위해 다른 클래스(혹은 타입)를 필요로 할 때 이를 의존(dependency)한다고 말한다.

<u>타입에 의존한다는 것은 다른 말로 해당 타입의 객체를 사용한다는 것을 뜻한다.</u>

의존하는 타입의 객체는 직접 생성해서 사용할 수 있지만 외부에서 전달 받을수도 있다.

직접 생성해서 전달 받는 경우

```
public class A {
	private B b = new B();
}
```



의존하는 객체를 생성자의 파라미터를 이용해서 외부에서 전달 받는 경우

```
public class A {
	private B b;
	
	//의존하는 타입의 객체를 생성자의 파라미터를 통해서 전달받음
	public A(B b) {
		this.b = b;
	}
}


class Main {
	//의존 객체를 직접 생성
	A a = new A(new B());
}
```

위와 같이 생성자를 통해서 의존 객체를 전달받는 경우, A객체를 생성할때 의존하는 객체를 생성자의

파라미터로 전달해야 한다.



### 의존 객체를 직접 생성하는 방식의 단점

위 코드는 B코드를 직접 생성하고 있다. 이 상황에서 요구사항의 변화로 B클래스의 하위 클래스인

BB클래스를 사용해야 하는 상황이 발생했다고 하자. 이 경우 Main클래스의 코드를 변경해 주어야 한다.

```
class Main{
//B클래스 대신에 BB를 사용하려면 Main을 변경해주어야함
A a = new A(new BB());
}
```

사실 Main클래스는 사용할 객체 타입이 B 클래스인지 BB클래스인지 중요하지 않음에도, BB클래스 객체를

사용하기 위해서 Main 클래스의 코드를 변경해야하는 상황이 발생했다.

만약 B클래스 대신에 BB 클래스의 객체를 사용해야 하는 코드들이 많다면 , 수정해주어야 하는 코드의

양도 증가하게 될것이다.(유지보수의 어려움)

즉 의존하는 객체를 직접 생성하는 방식은 개발 효율이 떨어지게된다.



### DI를 사용하는 방식의 코드:의존객체를 외부에서 조립함

의존 객체를 직접 생성하는 방식과 달리 DI(Dependency Injection)는 의존 객체를 외부로부터 전달받는 구현 방식이다.

```
public class A {
	private B b;
	
	//의존하는 타입의 객체를 생성자의 파라미터를 통해서 전달받음
	public A(B b) {
		this.b = b;
	}
}


class Main {
	//의존 객체를 직접 생성
	A a = new A(new B());
}
```

위에서 살펴본 예제를 다시 살펴보면 Main에서 의존객체를 직접 생성하여 주입하는 방식인데 여기서 Main클래스가 스스

로 객체를 생성하지 않고 외부의 누군가가 의존하는 객체를 A에 넣어준다는 의미로 이런 방식을 Dependency

Injection(의존 주입), 즉 DI라고 부른다. 그럼 누가 객체를 생성하고 서로 연결해 줄까? 그런 역할을 수행하는 것이

바로 조립기(컨테이너)이다. 다음 예를 보자


	public class Assembler {
		private A a;
		private B b;
		
	public Assembler() {
		b = new B();	
		a = new A(b);
		
	}
	
	public A a() {
		return a;
		}
	}
이제 A가 필요한 곳에서는 조립기를 이용해서 A를 구현하면된다.

		Assembler assembler = new Assembler();
		
		//Assembler에서 생성된 A타입 객체를 가져옴
		A a = assembler.getA();
	}
만약 A가 사용하는 B객체의 타입이 C로 변경되면 어떻게 될까? 이 경우 A코드를 변경하는 대신 조립기의 코드를

바꾸게 된다.

	public class Assembler {
	    private A a;
	    private B b;
	
	    public Assembler() {
	    	//C객체는 B를 상속함
	 	    b = new C();
	        a = new A(b);
	       
	    }
	
	    public A getA() {
	        return a;
	    }
	}
C객체를 사용하도록 변경해 주어야 하는 곳이 많더라도 변경되는 부분은 조립기로 제한된다. 예를들어 A객체 뿐만

아니라 다른 객체에서도 B대신 C를 사용해야한다고 해보자. 이경우, 변경되는 코드는 조립기 역할을 하는

Assembler 클래스 뿐이며 C객체를 필요로 하는 코드는 변경되지 않게 된다.



    public class Assembler {
        private A a;
        private B b;
        //A클래스 이외의 다른 클래스
        private OtherClass other;
        
    public Assembler() {
    	//C객체는 B를 상속함
     	    b = new C();
            a = new A(b);
            other = new OtherClass(b);
           }
    
    public A getA() {
        return a;
    }
    }