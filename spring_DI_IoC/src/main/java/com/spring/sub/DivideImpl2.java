package com.spring.sub;

public class DivideImpl2 extends DivideImpl{

	@Override
	public int div(int a, int b) {
		return super.div(a, b)/10;
	}

	@Override
	public int div(int a, int b, int c) {
		return super.div(a, b, c)/10;
	}

}
