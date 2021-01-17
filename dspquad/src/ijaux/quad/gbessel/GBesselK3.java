package ijaux.quad.gbessel;

//import static ijaux.quad.QuadDE.intdei;
import static ijaux.quad.QuadDE.intdeo;
import static java.lang.Math.*;

import ijaux.quad.QFunction;
import ijaux.quad.QuadBess;
import ijaux.quad.euler.Gamma;

/*
 *  Bessel K function
 * 
 * 

\frac{1}{2 \pi  }\int_{0}{1} u/(1+ u^(2*a)) J_0(u*z) du
 
 
*/
public class GBesselK3 implements QFunction {
	
	//Gamma gam=new Gamma();
	//	double ga=0;
	
	QuadBess qb=null;
	
	private class KerF implements QFunction {

		double a=0;
	
		public void setVal(double aa) {
			a=aa;
		}
		
		@Override
		public double eval(double u) {
			final double up=pow(u, 2*a);
		 
			return  u/(1.0+ up) ;
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
	private double tol=1.0e-15;
	
	
	public GBesselK3(double aa) {
		a=aa;
		qf.setVal(aa);
		qb=new QuadBess(qf, 20);
	}
	
	public GBesselK3(double aa, double ttol) {
		a=aa;
		qf.setVal(aa);
		tol=ttol;
		qb=new QuadBess(qf, 20);
	}

	@Override
	public double eval(double x) {
	
		value=qb.eval(x);
		return value;
	}


	@Override
	public String toString() {
		return "K(a,z)";
	}

	
	public static void main(String[] args) {
		
		BesselKN bn= new BesselKN(0);
		System.out.println("K0(1)="+bn.eval(1.0) +"  " +0.421024438240708333);
		
		GBesselK3 bn2=new GBesselK3(1.0);
		System.out.println("K0(1)="+bn2.eval(1.0) +"  " +0.421024438240708333);
		
	}

}
