package com.spring.sub;

public class MultiplexImpl implements Multiplex{

	@Override
	public int multi(int a, int b) {
		return a*b;
	}

	@Override
	public int multi(int a, int b, int c) {
		return a*b*c;
	}

	
}
