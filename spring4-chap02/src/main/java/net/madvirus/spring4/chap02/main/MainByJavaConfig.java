package net.madvirus.spring4.chap02.main;

import javax.servlet.jsp.jstl.core.Config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.madvirus.spring4.chap02.AuthenticationService;
import net.madvirus.spring4.chap02.PasswordChangeService;

public class MainByJavaConfig {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);

		AuthenticationService authSvc = ctx.getBean("authenticationService", AuthenticationService.class);
		
		authSvc.authenticate("bkchoi", "1234");
		
		PasswordChangeService pwChgSvc = ctx.getBean("passwordChangeServic", PasswordChangeService.class);
		
		pwChgSvc.changePassword("bkchoi", "1234", "5678");
		
	}

}
