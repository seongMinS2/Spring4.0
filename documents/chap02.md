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
또한 DI를 사용하게 되면 클래스의 구현이 완성되어 있지 않더라도 테스트가 가능하다.



### 생성자 방식과  프로퍼티 설정 방식

```
class Assembler {
    private A = a;

    public Assembler {
        //생성자 주입방식
        A(B b){}
    }

    //set메서드를 통한 주입(프로퍼티방식)
    public void setA(A a){
        this.a = a;	
    }
}
```

생성자 설정 방식은 앞서 본것들이고 프로퍼티 설정 방식은 의존 객체를 전달받기 위해 메서드를 이용한다.

자바빈(JavaBeans)의 영향으로 프로퍼티 설정 방식은 위예제 처럼 setA()형식의 메서드를 주로 사용한다.

메서드의 이름이 setA()인 경우 프로퍼티 이름은 "set" 접두어를 빼고 첫 글자를 소문자로 바꾼 "a"가 된다.



### 생성자 방식과 프로퍼티 설정 방식의 장단점

생성자 방식의 장점은 객체를 생성하는 시점에 의존하는 객체를 모두 전달받을 수 있다.

전달받은 파라미터가 정상인지 확인하는 코드를 생성자에 추가할 경우, 객체 생성 이후에는 그

객체가 사용 가능 상태임을 **보장**할 수 있다.

```
//객체 생성 시점에 의존 객체가 정상인지 확인
public class Assembler{
	private A a;

	
	public Assembler(A a){
		if(a == null) throw new IllegalArgumentException();
		this.a = a;
	}
	...
	
	//Assembler가 정상적으로 생성되면, Assembler객체는 사용 가능한 상태가 됨
	Assembler assem = new Assembler(a);
	assem.add(3, 5);
	
}
```



프로퍼티 설정 방식의 장점은 어떤 의존 객체를 설정하는지 메서드 이름으로 알 수 있다는 점이다.

예를들어, 아래 코드에서 a 파라미터의 타입을 확인하지 않더라도 setA()라는 프로퍼티 설정 메서드의

이름을 통해 a가 A객체임을 유츄할 수 있다.

```
Assembler assem = new Assembler();
// a 파라미터의 타입을 확인하지 않아도 a가 A임을 알 수 있다.
assem.setA(a);
```



두 방식의 장점들이 다른 방식의 단점이 되기도 한다. 즉, 생성자 방식에서는 생성자에 전달되는 파라미터의

이름만으로는 실제 타입을 알아내기 힘들고, 생성자에 전달되는 파라미터의 개수가 증가할수록 코드

가독성이 떨어진다.

반대로 프로퍼티 설정 방식에서는 객체를 생성한 뒤에 의존 객체가 모두 설정되었다고 보장할 수 없어서

사용 가능하지 않은 상태일 수도 있다.

### 스프링은 객체를 생성하고 연결해주는 DI 컨테이너

지금까지 DI가 무엇인지, 조립기가 무엇을 하는지, 그리고 DI를 위한 두 가지방식인 생성자 방식과 프로퍼티

방식을 살펴봤다. 그러면, 스프링은 DI와 무슨 관계가 있을까? **스프링을 떠받치고 있는 핵심 기능중 하나가**

**바로 DI다.**  스프링은 객체를 생성하고 각 객체를 연결해주는 조립기 역할을 하게 된다.

### 스프링 DI 설정

스프링을 잘 사용하려면 가장 기본적인 DI설정을 이해해야 한다. 스프링 DI 설정은 XML,자바코드,그루비 코드

의 세가지 방법으로 할 수 있는데, 이 책에서는 XML과 자바 코드를 이용한 DI 설정방법에 대해 살펴볼것이다.



### XML을 이용한 DI 설정

설정 정보로 사용할 XML 파일을 작성하는 것은 매우 간단하다.<bean> 태그를 이용해서 컨테어너가

생성할 빈 객체를 지정해주고, <property>나<constructor-arg>태그를 이용해서 객체가

필요로 하는 값을 설정해주면 된다.

#### <bean>태그 : 생성할 객체 지정

<bean> 태그는 스프링 컨테이너가 생성할 객체에 대한 정보를 지정할때 사용한다.

<bean> 태그의 주요 속성은 id와 class이다.

id = 빈 객체의 고유한 이름으로 사용한다. id속성으로 지정한 빈의 이름은 다른<bean>태그에서 참조할때

사용된다. 컨테이너에서 직접 빈 객체를 구할때에도 사용된다.(ctx.getBean("빈id", 빈클래스명.class))

id 속성의 값을 지정하지 않으면 빈객체의 클래스 이름을 사용해서 임의의 이름을 생성한다.

#### <constructor-arg> 태그: 생성자 방식 설정

생성자 방식의 의존성 주입시 생성자에 전달할 값을 설정해 주어야하는데, 이때 사용되는 태그가

<constructor-arg> 태그다. 다음은 XML 설정에서 <constructor-arg>태그를 사용하는 방법을

보여주고 있다

```
//User 객체의 생성자 파라미터가 (String id, String pw) 일 시

<bean id="user" class="net.madvirus.spring4.chap02.User">
	<constructor-arg value="park" /> //아이디
	<constructor-arg value="1234" /> //비밀번호
</bean>
```

파라미터의 갯수가 위와같이 다수인 경우 갯수만큼 차례대로 지정해 주면된다.

이와같이 User 객체를 생성하게 되면 다음의 코드를 이용해서 객체를 생성하는것과 **비슷**하다.

(실제로는 아래의 코드를 이용해서 객체를 생성하는 것은 아니고 자바의 **리플렉션**이라는 기능을 이용해서

생성한다.)

```
new User("park", "1234");
```

<constructor-arg>에 int와 같은 **기본데이터 타입**, Integer와 같은 **래퍼타입**, **String**등의 값을 설정할 떄에는

<value>나 value속성을 사용한다

```
<bean id="user" class="net.madvirus.spring4.chap02.User">
	<constructor-arg><value> park <value></constructor-arg>
	<constructor-arg value="1234" />
</bean>
```

스프링은 설정파일에 지정한 값을 파라미터 타입에 맞게 변환해서 처리한다. 예를들어, 위 코드에서 첫번째

파라미터값으로 지정한 "park" 라는 값을 실제 생성자에서 필요로 하는 String 타입으로 만들어서 제공한다.

파라미터가 int타입이나 double타입이라면 해당 타입으로 맞게 변환 처리를 수행한다.



파라미터로 다른 빈 객체를 사용해야 하는 경우에는 value속성 대신에 <ref>태그와 ref속성을 사용한다.

```reStructuredText
<bean id="userRepository" class = "net.madvirus.spring4.chap02.UserRepository">
</bean>

<bean id="pwChangeSvc" class="net.madvirus.spring4.chap02.PasswordChangeServic">
	<constructor-arg ref="userRepository"
</bean>
```

ref속성의 값으로는 다른 빈 객체의 이름을 사용하면 된다.

<ref>태그를 사용할때에는 아래와 같다

```
<bean id="userRepository" class = "net.madvirus.spring4.chap02.UserRepository">
</bean>

<bean id="pwChangeSvc" class="net.madvirus.spring4.chap02.PasswordChangeServic">
	<constructor-arg><ref bean="userRepository"/></constructor-arg>
</bean>
```



#### <property>태그: 프로퍼티 방식 설정

프로퍼티 설정 방식을 사용하는 경우에는 <property> 태그를 사용한다. name속성을 이용해서 프로퍼티 이름을지정하고

프로퍼티 값은 <value>태그 또는 value 속성 그리고 <ref>태그 또는 ref속성을 이용해서 값을 설정한다.

```
<bean id="authFailLogger" class="net.madvirus.spring4.chap02.AuthFailLogger">
	<property name="threshold" value="5" />	-> setThreshold(5)
</bean>

<bean id="authService" class="net.madvirus.spring4.chap02.AuthenticationService">
	<property name="failLogger" ref="authFailLogger" />      -> setFailLogger(authFailLogger)
	<property name="userRepository">
		<ref bean="userReposityory" />         -> setUserRepository(userRepository)
	</property>
</bean>
```



<property> 태그를 사용하면, set프로퍼티이름() 형식의 메서드를 이용해서 값을 설정한다.

위 연결 관계를 보면 프로퍼티 이름과 일치하는 set 메서드를 사용해서 값을 설정하는 것을 알 수 있다. 이때 프로퍼티의 이름의 첫 글자를 소문자로 변환한

이름을 사용해서 set 메서드를 선택한다. 



### GenericXmlApplicationContet로 XML파일  읽어오기

XML을 이용해서 생성할 객체 목록과 DI 설정을 알아봤다. 이제 설정파일을 이용해서 스프링 컨테이너를 생성할 차례이다. **XML파일을 이용해서**

**설정파일을 작성했으므로 GenericXmlApplicationContext클래스를 사용해서 스프링 컨테이너를 생성하면 된다.**

```
GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("classpath:config.xml")

얻어올타입  변수명 = ctx.getBean("빈에 등록된 객체이름", 클래스이름.class)
```

위 코드에서 GenericXmlApplicationContext 생성자를 호출할때 "clashspath:config.xml"을 파라미터로 전달했는데, 이는 클래스패스에 위치한 config.xml

파일을 스프링 설정으로 사용한다는 것을 의미한다. 설정 파일을 이용해서 스프링 컨테이너를 생성했다면 그 다음으로 getBean()메서드를 이용해서

빈객체를 구할 수 있다. getBean()메서드에서 "객체이름"을 지정하지 않으면 기술된 타입에 해당하는 빈을 구해서 리턴한다.(클래스이름.class)



### GenericXmlApplicationContext설정 파일 지정

한 개의 XML파일에 수십 개의 빈 설정을 하면 어떻게 될까? 아마도 XML의 빈 설정을 관리하는 것이 다소 성가실 것이다.

자바로 웹 어플리케이션을 개발할 때 업무 영역이나 계층별로 패키지를 나누는 것처럼, 스프링을 설정할 때에도 관련된 것들을

별도의 설정파일로 나눠 보관한다. 예를 들어, 회원관련된 빈 설정은 spring-member.xml파일에 넣고 게시판 관련된 빈 설정은

spring-board.xml 파일에 넣고,DB연동 관련 빈 설정은 spring-datasource.xml 파일에 넣는 식으로 구분한다.

이렇게 스프링 설정을 여러 파일에 분리해서 넣었다면, 다음과 같이 설정 파일 목록을 GenericXmlApplicationContext 생성자에

전달해주면 된다. 생성자 파라미터는 **가변인자**라서 한 개 이상의 값을 파라미터로 전달할 수 있다.



```
GenericXmlApplicationContext ctx = new GenericXmlApplicationContext(
	"classpath:/spring-member.xml", "classpath:/spring-board.xml", "spring:/datasource.xml"
);
```



스프링 설정이 클래스패스 루트가 아닌 다른곳에 위치한다면, 루트를 기준으로 경로 형식을 입력하면 된다.



```
GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("classpath:/conf/spring/conf.xml");
```



클래스패스가 아닌 파일 시스템에서 설정 파일을 읽어오고 싶다면 file: 접두어를 사용하면 된다.



```
GenericXmlApplicationContext ctx = new GenericXmlApplicationContext(
	"file:src/main/resources/conf.xml", // 상대 경로인 경우 현재 디렉토리 기준
	"file:/conf/local/conf2.xml"
);
```

file: 접두어 뒤에 상대경로와 절대경로를 사용할 수 있는데, 상대 경로를 사용하면 java를 실행하는 현재 디렉토리를 기준으로 상대 경로를 계산한다.



특정 경로에 있는 모든 XML 파일을 설정 파일로 사용하고 싶은 경우가 있는데, 이럴 떈 애스터리크(*)를 사용해서 지정할 수 있다.

```
GenericXmlApplicationContext ctx = new GenericXmlApplicationContext(
	"classpath:/conf/spring-*.xml");
```

위 코드는 /conf/ 클래스패스에 위치하고 파일 이름이"spring-"으로 시작하는 모든 xml파일을 설정 파일로 사용한다.

 