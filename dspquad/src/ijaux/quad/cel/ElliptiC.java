package ijaux.quad.cel;

import static ijaux.quad.QuadDE.intdei;
import static java.lang.Math.*;

import ijaux.quad.QFunction;
 


public class ElliptiC implements QFunction {


	private Ker qf=new Ker();
	double kcc=1.0;
	double pp=1.0;
	double aa=1.0;
	double bb=1.0;
	
	double tol=1e-15;
	
	final static double PI2=PI/2.0;
	
	
	/////////////////////////
	//  Integral Kernel
	////////////////////////
	private class Ker implements QFunction {
		
		@Override
		public double eval(double x) {
			final double x2=x*x;
			final double numer=(aa+bb*x2);
			final double denom=(1.0+pp*x2)*sqrt(1+x2)*sqrt(1+kcc*kcc*x2);
			if (denom==0) return (Double.NaN);
			return numer/denom;
		}

		@Override
		public String toString() {
			return " (a+b*t^2)/(1+p*t^2)/sqrt(1+t^2)*(1+k^2*t^2)";
		}
	}
	
	////////////////////////////

	  /**
     * Elliptic integrals: 
     *	This algorithm for the calculation of the complete elliptic
     *	integral (CEI) is presented in papers by Ronald Bulirsch,
     *  Ronald Bulirsch: Numerical Calculation of Elliptic Integrals and Elliptic Functions III,
     *	Numerische Mathematik 13,305-315 (1969). 
     *  https://dlmf.nist.gov/19.2 
     *     cel(k_c, p, a, b)= 
     *     \int_{0}^{\pi/2} \frac{a \cos^2 {\theta}+ b \sin^2{\theta}}{\cos^2{\theta}+ p \sin^2{\theta}}
     *      \frac{d \theta}{\sqrt{\cos^2{\theta}  +  k^2_c \sin^2{\theta}}}
     *
     */
	   
	public ElliptiC(double p, double a, double b) {
		aa=a;
		bb=b;
		pp=p;
	}
	
	public ElliptiC(double p, double a, double b, double ttol) {
		aa=a;
		bb=b;
		pp=p;
		tol=ttol;
	}
	
	public void setParams(double p, double a, double b ) {
		aa=a;
		bb=b;
		pp=p;
	}

	/* the argument corresponds to kc
	 * (non-Javadoc)
	 * @see ijaux.quad.QFunction#eval(double)
	 */
	@Override
	public double eval(double kc) {
		kcc=kc;
		final double[] ret=intdei(qf, 0.0, tol);
		return  ret[0];
	}

	public static void main(String[] args) {
		ElliptiC ec=new ElliptiC(1.0, 1.0, 1.0);
		
		double ret= ec.eval(1.0);
		
		System.out.println (" C(1,1,1,1)="+ ret+"\t, "+PI2);
	}

}
