package ijaux.quad.cel;

import static ijaux.quad.QuadDE.intde;


import ijaux.quad.QFunction;

public class Eps implements QFunction {
	
	private Dn dn;
	
	private double m=0;
	
	private double tol=1e-8;
	 
	final private Ker qf=new Ker();
	
	/*
	 * integral kernel function
	 */
	private class Ker implements QFunction {
		 
		
		@Override
		public double eval(double x) {
			final double d=dn.eval(x);
			return d*d;
		}

		@Override
		public String toString() {
			return "dn(x|m)^2";
		}
	}
	//////////////////////
	
	public Eps(double mm) {
		m=mm;
		dn=new Dn(mm);
	}

	@Override
	public double eval(double x) {
		final double[] ret=intde(qf, 0, x, tol);
		return ret[0];
	}
	
	@Override
	public String toString() {
		return "E(x|m)";
	}

	public static void main(String[] args) {
		final Eps ef=new Eps(0.5);
		
		System.out.println( ef.eval(1.));
		System.out.println( ef.eval(2.));

	}

}
