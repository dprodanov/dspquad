package ijaux.quad.euler;

import static ijaux.quad.QuadDE.*;
import static java.lang.Math.*;

import ijaux.quad.QFunction;
 

public class Beta   {

	class AuxF implements QFunction {

		double a=0, b=0;
 
		
		public void setVal(double aa, double bb ) {
			a=aa;
			b=bb;
		}
		
		@Override
		public double eval(double u) {
			return pow(u,a-1.0)*pow(1-u, b-1.0);
		}

		@Override
		public String toString() {
			return "u^(a-1)*(1-u)^(b-1)";
		}
		
	}
	////////////////////////////
	
	private double value=0;
	
	private AuxF qf=new AuxF();
	private double tol=1.0e-15;
 	
	public Beta(double q) {
		tol=q;
	}
	
	public Beta() {
	}

	public double eval(double a, double b) {
		if (a<=0) throw new IllegalArgumentException("val: "+a);
		if (b<=0) throw new IllegalArgumentException("val: "+b);
		qf.setVal(a, b );
		final double[] ret=intde(qf, 0.0, 1.0, tol);
		value=ret[0];
		return value;
	}
	
	public static void main(String[] args) {
		 
		Beta bf= new Beta();
	 double ret=bf.eval (0.5, 2.5);
	 System.out.println (" I_4="+ ret+"\t, "+  (3.0 *PI)/8.0 );
	 
	 ret=bf.eval (2.5, 1.5);
	 System.out.println (" I_4="+ ret+"\t, "+ PI/16.0);
	 
	 ret=bf.eval (0.5, 1.5);
	 System.out.println (" I_4="+ ret+"\t, "+ PI/2.0);
	   
	}
	

}
