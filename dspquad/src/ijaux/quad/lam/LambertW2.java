package ijaux.quad.lam;

import static ijaux.quad.QuadDE.*;
import static java.lang.Math.*; 
import ijaux.quad.QFunction;


/* 
 * Computes the principal branch of the Lambert W function 
 * German A. Kalugin, David J. Jeffrey, and Robert M. Corless. 
 * "Stieltjes, Poisson and other integral representations for functions of Lambert W". 
 *  https://arxiv.org/abs/1103.5640
 */
public class LambertW2 implements QFunction {
	
	private double tol=1e-15;
	private final KerF qf=new KerF();
	
	//////////////////
	// Kernel
	/////////////////
	private class KerF implements QFunction {

		double z=0;
				
		public void setVal(double zz) {
			z=zz;
		}
		
		@Override
		public double eval(double u) {
			final double aa=u/tan(u);
			double bb=(1.0- aa);
			bb=bb*bb;
			
			return (bb+u*u)/(z+u/sin(u)*exp(-aa) );
		}
		
		public double prefactor() {
			return 1.0/PI;
		};

		@Override
		public String toString() {
			return  "((1- cot(u))^2 +u^2)/(z+ u*cot(u)*exp(- u*cot(u)))";
		}
		
	 
		
	}
	////////////////////////////
	
	
	public LambertW2() {
	}
	

	@Override
	public double eval(double x) {
		if (x<-Math.exp(-1.0)) {return Double.NaN;};
	 
		qf.setVal(x);
		double ret=qf.prefactor();
		final double[] vv=intde(qf, 0.0, PI, tol);
		double w=vv[0]*ret*x;
		return w;
	}

	public static void main(String[] args) {
		LambertW2 lw=new LambertW2();
		System.out.println(lw.eval(2.0));
		System.out.println(lw.eval(-Math.exp(-1)));
		System.out.println(lw.eval(1.0));
	}

}
