package com.spring.exe;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.spring.main.Calculator;

public class SpringMain {

	public static void main(String[] args) {
		
		//web.xml 경로만 전달해주면 container를 만들어줌
		//applicationContext는 타입명.이안에는 우리가 만들었던 Map으로 이루어져있을것이다.
		//GenericXmlApplicationContext는 xml을 관리한다.
		ApplicationContext ctx = new GenericXmlApplicationContext("classpath:com/spring/context/root-context.xml");
		
		Calculator cal = ctx.getBean("cal",Calculator.class);
		
		int a=4,b=2;
		
		System.out.println(cal.sum(a,b));
		System.out.println(cal.min(a,b));
		System.out.println(cal.multi(a,b));
		System.out.println(cal.div(a,b));
	}

}
