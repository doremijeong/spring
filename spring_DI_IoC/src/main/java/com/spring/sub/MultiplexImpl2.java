package com.spring.sub;

public class MultiplexImpl2 extends MultiplexImpl{

	@Override
	public int multi(int a, int b) {
		return super.multi(a, b)*10;
	}

	@Override
	public int multi(int a, int b, int c) {
		return super.multi(a, b, c)*10;
	}

}
