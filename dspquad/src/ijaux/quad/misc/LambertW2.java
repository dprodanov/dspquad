package ijaux.quad.misc;

import static ijaux.quad.QuadDE.*;
import static java.lang.Math.*; 
import ijaux.quad.QFunction;


/* Computes the principal branch of the LAmber W function 
 * Corless, R. M.; Gonnet, G. H.; Hare, D. E. G.; Jeffrey, D. J.; Knuth, D. E. (1996). 
 * "On the Lambert W function". 
 *  Advances in Computational Mathematics. 5: 329–359. doi:10.1007/BF02124750
 */
public class LambertW2 implements QFunction {
	
	//private int niter=16;
	private double tol=1e-15;
	private KerF qf=new KerF();
	
	private class KerF implements QFunction {

		double z=0;
				
		public void setVal(double zz) {
			z=zz;
		}
	 
		/*
		@Override
		public double eval(double u) {
			final double aa=u+sin(u);
			final double ee=z*exp(-cos(u));
			final double rr=1.0-ee*cos(aa);
			final double ii=ee*sin(aa);
			return log(rr*rr+ii*ii);
		}
		 */
		
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
			return "log((1 -z*exp(-cos(u))*cos(u+sin(u)))^2+"
					+ "(z*exp(-cos(u))*sin(u+sin(u)))^2) ";
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
		//double w=vv[0]*ret;
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
