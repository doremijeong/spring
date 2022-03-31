package com.spring.sub;

public class SummationImpl2 extends SummationImpl {

	@Override
	public int sum(int a, int b) {
		return super.sum(a, b)+10;
	}

	@Override
	public int sum(int a, int b, int c) {
		return super.sum(a, b, c)+10;
	}

	
}
