package test;

import ijaux.quad.QFunction;

public class LambdaTest  {
	
	/*
	public LambdaTest() {
		
	}
*/
	public static void main(String[] args) {
		QFunction Lambda = (x) -> x*x;
		System.out.println(Lambda.eval(2));
	}

}
