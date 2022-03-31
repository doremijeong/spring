package com.spring.sub;

public class DivideImpl implements Divide{

	@Override
	public int div(int a, int b) {
		return a/b;
	}

	@Override
	public int div(int a, int b, int c) {
		return a/b/c;
	}

	
}
