package com.spring.sub;

public class MinusImpl implements Minus{

	@Override
	public int min(int a, int b) {
		return a-b;
	}

	@Override
	public int min(int a, int b, int c) {
		return a-b-c;
	}

	
}
