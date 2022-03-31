package com.spring.sub;

public class MinusImpl2 extends MinusImpl{

	@Override
	public int min(int a, int b) {
		return super.min(a, b)-10;
	}

	@Override
	public int min(int a, int b, int c) {
		return super.min(a, b, c)-10;
	}

}
