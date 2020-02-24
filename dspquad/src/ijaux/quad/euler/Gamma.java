package ijaux.quad.euler;

import static ijaux.quad.QuadDE.*;
import static java.lang.Math.*;

import ijaux.quad.QFunction;

public class Gamma implements QFunction {

	/*
	 * integral kernel function
	 */
	private class Ker implements QFunction {
		public double val=0.5;
		
		public void setVal(double v) {
			val=v;
		}
		
		@Override
		public double eval(double x) {
			if (val<=0) return (Double.NaN);
			return exp(-x) * pow(x, val-1.0);
		}

		@Override
		public String toString() {
			return "exp(-x)*x^(z-1)";
		}
	}
	
	////////////////////////////
	
	private double value=0;
	
	private Ker qf=new Ker();
	private double tol=1.0e-15;
	
	public Gamma() {
	}
	
	public Gamma(double q) {
		tol=q;
		qf=new Ker();
	}
	
	@Override
	public double eval(double x) {
		if (x>0) {
			qf.setVal(x);
			final double[] ret=intdei(qf, 0.0, tol);
			value=ret[0];
		} else {
			// reflection formula
			qf.setVal(-x);
			final double[] ret=intdei(qf, 0.0, tol);
			value=-PI/(ret[0]*sin(PI*x)*x);
		}
		return value;
	}

	@Override
	public String toString() {
		return "Gamma(z)";
	}
	

}
