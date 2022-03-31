package com.spring.advice;

import org.aspectj.lang.ProceedingJoinPoint;



public class AdviceBehavior {

	public void chikachika() {

		System.out.println("열심히 양치를 합니다.");
	}


	//loinpoint : around
	//around는 메서드를 따로 만들어야 사용할 수 있다.
	public void chikachikaAround(ProceedingJoinPoint joinPoint)throws Throwable {
		System.out.println("한번 닦았는데...");

		joinPoint.proceed();

		System.out.println("또 닦아요!!!!!");
	}
}
