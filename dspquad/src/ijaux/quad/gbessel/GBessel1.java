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
public class GBessel1 implements QFunction {
	Gamma gam=new Gamma();
	
	private class KerF implements QFunction {

		double a=0;
		double z=0;
		
		//private BesselJ1 besselj1= new BesselJ1();
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
			//final double bk1=besselj1.eval(u*z);
			final double bk0=besselj0.eval(u*z);
			final double qu=(a*pow(u,2*a)-1);
			//return  u*u*exp(-up)*bk1 ;
			return  u*qu*exp(-up)*bk0 ;
		}

		@Override
		public String toString() {
			return " exp(-k^a)*J_1 ( k r)  k^2 ";
		}
		
		@Override
		public double prefactor() {
			//return -2/ga*a;
			return 4/ga*a/z;
		}
		
	}
	////////////////////////////
	double z=0;
	protected double a=0;
	double value=0;
	 
	private KerF qf=new KerF();
	private double tol=1.0e-15;
	
	
	public GBessel1(double aa) {
		a=aa;
	}
	
	public GBessel1(double aa, double ttol) {
		a=aa;
		tol=ttol;
	}

	@Override
	public double eval(double x) {
		if (x==0) {
			return 0.0;
		}
		qf.setVal(a,x);
		double ret=qf.prefactor();
		z=x;
		final double[] vv=intdei(qf, 0.0, tol);
		value=vv[0]*ret;
		return value;
	}

	public static void main(String[] args) {
		
		//System.out.println("I =\\frac{1}{2 \\pi i}\\int_Ha^{-} exp(xi -z/xi^a)*xi^(a-1) dxi\n");
		GBessel1 g= new GBessel1(1.0);
		
		double ret= g.eval(0.5);
		System.out.println (" M(1 | 0.5)="+ ret+"\t, "+-0.234853265703369);
		
		ret=g.eval(5);
		System.out.println (" M(1 | 5)="+ ret+"\t, "+-0.004826135340569273);
		
	 
		g= new GBessel1(0.5);
		ret=g.eval(0.5);
		System.out.println (" M(1/2| 0.5)="+ ret+"\t, "+-0.858650103359919);
		ret=g.eval(5.0);
		System.out.println (" M(1/2| 0.5)="+ ret+"\t, "+-0.00435168938916089);
		
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
