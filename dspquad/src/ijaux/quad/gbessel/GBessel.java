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

\frac{1}{2 \pi  }\int_{0}{\infty} exp( - k^a)*J_0 ( k r)  k d k
 
 
*/
public class GBessel implements QFunction {
	Gamma gam=new Gamma();
	
	private class KerF implements QFunction {

		double a=0;
		double z=0;
		
		private BesselJ0 besselj0= new BesselJ0();
		
		double ga=0;
				
		public void setVal(double aa, double  zz) {
			a=aa;
			z=(zz);
			ga=gam.eval(1.0/a);
		}
		
		@Override
		public double eval(double u) {
			final double up=pow(u, 2*a);
			final double bk=besselj0.eval(u*z);
			return  u*exp(-up)*bk ;
		}

		@Override
		public String toString() {
			return " exp(-k^a)*J_0 ( k r)  k ";
		}
		
		@Override
		public double prefactor() {
			return 2/ga*a;
		}
		
	}
	////////////////////////////
	double z=0;
	protected double a=0;
	double value=0;
	 
	private KerF qf=new KerF();
	private double tol=1.0e-15;
	
	
	public GBessel(double aa) {
		a=aa;
	}
	
	public GBessel(double aa, double ttol) {
		a=aa;
		tol=ttol;
	}

	@Override
	public double eval(double x) {
		if (x==0) {
			return 1.0;
		}
		qf.setVal(a,x);
		double ret=qf.prefactor();
		z=x;
		final double[] vv=intdei(qf, 0.0, tol);
		value=vv[0]*ret;
		
		/*
		if (a<=0.5) {
			final double[] vv=intdei(qf, 0.0, tol);
			value=vv[0]*ret;
		} else {
		final double[] vv=intdeo(qf, 0.0, sin(PI*a)*z, tol);
			value=vv[0]*ret;
		}
		*/
		return value;
	}

	public static void main(String[] args) {
		
		//System.out.println("I =\\frac{1}{2 \\pi i}\\int_Ha^{-} exp(xi -z/xi^a)*xi^(a-1) dxi\n");
		GBessel g= new GBessel(1.0);
		
		double ret= g.eval(0.5);
		System.out.println (" M(1 | 0.5)="+ ret+"\t, "+0.9394130628134758);
		
		ret=g.eval(5);
		System.out.println (" M(1 | 5)="+ ret+"\t, "+0.001930454136227731);
		
	 
		g= new GBessel(0.5);
		ret=g.eval(0.5);
		System.out.println (" M(1/2| 0.5)="+ ret+"\t, "+0.7155417527999326);
		ret=g.eval(5.0);
		System.out.println (" M(1/2| 0.5)="+ ret+"\t, "+0.007542928274545542);
		
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
