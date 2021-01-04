package ijaux.quad.gbessel;

import static ijaux.quad.QuadDE.intdei;
import static java.lang.Math.*;

import ijaux.quad.QFunction;
import ijaux.quad.euler.Gamma;

/*
 *  fractional Laplacian kernel
 * 
 * 

M-Wirght kernels

\frac{1}{2 \pi  }\int_{0}{\infty} exp( - k^a)*J_0 ( k r) *  k^3 * d k
 
 
*/
public class GBessel2L implements QFunction {
	
	Gamma gam=new Gamma();
		double ga=0;
	
	
	private class KerF implements QFunction {

		double a=0;
		double z=0;

		private BesselJ0 besselj0= new BesselJ0();
		//private BesselJN besselj0= new BesselJN(0);
					
		public void setVal(double aa, double  zz) {
			a=aa;
			z=(zz);
		}
		
		@Override
		public double eval(double u) {
			final double up=pow(u, 2*a);
			final double bk0=besselj0.eval(u*z);
			return  u*u*u*exp(-up)*(bk0) ;
		}

		@Override
		public String toString() {
			//return " exp(-k^a)*(J_0 ( k r) -J_2 ( k r) )  k^3 ";
			return " exp(-k^a)* J_0 ( k r) * k^3 ";
		}
		
		@Override
		public double prefactor() {
			return -2/ga*a;
		}
		
	}
	////////////////////////////
	double z=0;
	protected double a=0;
	double value=0;
	 
	private KerF qf=new KerF();
	private double tol=1.0e-15;
	
	
	public GBessel2L(double aa) {
		a=aa;
		ga=gam.eval(1.0/a);
	}
	
	public GBessel2L(double aa, double ttol) {
		a=aa;
		tol=ttol;
		ga=gam.eval(1.0/a);
	}

	@Override
	public double eval(double x) {
 
		qf.setVal(a,x);
		double ret=qf.prefactor();
		z=x;
		final double[] vv=intdei(qf, 0.0, tol);
		value=vv[0]*ret;

		return value;
	}

	public static void main(String[] args) {
		
		//System.out.println("I =\\frac{1}{2 \\pi i}\\int_Ha^{-} exp(xi -z/xi^a)*xi^(a-1) dxi\n");
		GBessel2L g= new GBessel2L(1.0);
		
		double ret= g.eval(0.5);
		System.out.println (" M(1 | 0.5)="+ ret+"\t, "+-0.8806997463876336);
		
		ret=g.eval(2);
		System.out.println (" M(1 | 2)="+ ret+"\t, "+0);
		
	 
		g= new GBessel2L(0.5);
		ret=g.eval(0.8164965809277261);
		System.out.println (" M(1/2| sqrt(2/3)="+ ret+"\t, "+0);
		ret=g.eval(1.0);
		System.out.println (" M(1/2| 1.0)="+ ret+"\t, "+0.2651650429449552);
		
		/*
		g= new GBessel(0.666666666666666667);
		ret=g.eval(0.5);
		System.out.println (" M(2/3| 0.5)="+ ret+"\t, "+0.4858328419352538);
		ret=g.eval(-2.0);
		System.out.println (" M(2/3| -2.0)= "+ ret+"\t, "+0.2483373615570918);
		 */
 
	}

	@Override
	public String toString() {
		return "M(a,z)";
	}

}
