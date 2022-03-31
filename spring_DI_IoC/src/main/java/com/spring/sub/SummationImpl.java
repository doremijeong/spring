package com.spring.sub;

public class SummationImpl implements Summation{

	@Override
	public int sum(int a, int b) {
		return a+b;
	}

	@Override
	public int sum(int a, int b, int c) {
		return a+b+c;
	}

}
