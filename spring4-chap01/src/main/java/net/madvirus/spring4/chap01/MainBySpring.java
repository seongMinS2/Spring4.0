package net.madvirus.spring4.chap01;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainBySpring {
	
	public static void main(String[] args) {
		
//		String configLoaction = "classpath:applicationContext.xml";
//		AbstractApplicationContext ctx = new GenericXmlApplicationContext(configLoaction);
//		Project project = ctx.getBean("sampleProject", Project.class);
//				project.build();
//				ctx.close();

		//시간값이 문자열이라치면..
		String a = "21:30:44";
		
		//시간빼오고
		String hourS = a.substring(0, 2);
		
		//분빼와서
		String minM = a.substring(3, 5);

		//인트값바꾸고
		int hourI = Integer.parseInt(hourS);
		int minI = Integer.parseInt(minM);
		 
		//더해서 나누기n
		System.out.println((hourI + hourI + hourI)/ 3);
		System.out.println((minI + minI + minI)/ 3);
	}
}
