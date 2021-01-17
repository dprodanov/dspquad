package ijaux.quad.gbessel;

//import static ijaux.quad.QuadDE.intdei;
import static ijaux.quad.QuadDE.intdeo;
import static java.lang.Math.*;

import ijaux.quad.QFunction;
import ijaux.quad.euler.Gamma;

/*
 *  Bessel K function
 * 
 * 

\frac{1}{2 \pi  }\int_{0}{1} u/(1+ u^(2*a)) J_0(u*z) du
 
 
*/
public class GBesselK2 implements QFunction {
	
	Gamma gam=new Gamma();
		double ga=0;
	
	
	private class KerF implements QFunction {

		double a=0;
		double z=0;
		
		// Bessel rational approximants
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
			return  u/(1.0+ up)*(bk0) ;
		}

		@Override
		public String toString() {
			return "u/(1+ u^(2*a)) J0(u*z) ";
		}
		
		@Override
		public double prefactor() {
			return 1.0;
		}
		
	}
	////////////////////////////
	double z=0;
	protected double a=0;
	double value=0;
	 
	private KerF qf=new KerF();
	private double tol=1.0e-20;
	
	
	public GBesselK2(double aa) {
		a=aa;
	}
	
	public GBesselK2(double aa, double ttol) {
		a=aa;
		tol=ttol;
	}

	@Override
	public double eval(double x) {
 
		qf.setVal(a,x);
		double ret=qf.prefactor();
		z=x;
		//osicllatory integral
		final double[] vv=intdeo(qf, 0.0, 1.0, tol);
		value=vv[0]*ret;

		return value;
	}


	@Override
	public String toString() {
		return "K(a,z)";
	}

	
	public static void main(String[] args) {
		
		BesselKN bn= new BesselKN(0);
		System.out.println("K0(1)="+bn.eval(1.0) +"  " +0.421024438240708333);
		
		GBesselK2 bn2=new GBesselK2(1.0);
		System.out.println("K0(1)="+bn2.eval(1.0) +"  " +0.421024438240708333);
		
	}

}
