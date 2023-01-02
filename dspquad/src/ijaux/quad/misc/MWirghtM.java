package ijaux.quad.misc;

import static ijaux.quad.QuadDE.intdei;

import ijaux.quad.QFunction;
//import ijaux.quad.euler.Gamma;
import ijaux.quad.wright.GMWright;

import static java.lang.Math.pow;
import static java.lang.Math.exp;

public class MWirghtM implements QFunction {
	
	GMWright gmw;
	private double a;
	private double b;
 

	
	////////////////////
	private class KerF implements QFunction {
	 
		double z=0;
 
		
		public void setVal(  double  zz) {
			z=(zz);
	 
		}
		
		@Override
		public double eval(double u) {
			double d1= gmw.eval(u)*pow(u, z);
			double d= 0.5641895835477563*exp(-0.25*u*u)*pow(u, z);
			if (Double.isNaN(d)) d=0;
			//if (Double.isNaN(d1)) d1=0;
			System.out.println("d="+ d+ " d1="+d1);
			return  d;
		}
		
		public double prefactor() {
			return fr;
		}
		
		@Override
		public String toString() {
			return "u^z*W(a, b, u)"; 
		}
	}
	//////////////////
	
	private KerF qf=new KerF();
	private double tol=1.0e-8;
	
	
	double fr=1.;///Math.sqrt(Math.PI);
	/**
	 * 
	 */
	public MWirghtM(double aa, double bb) {
		a=aa;
		b=bb;
		gmw= new GMWright(a,b);

	}

	@Override
	public double eval(double x) {
		double ret=qf.prefactor();
		qf.setVal(x);
		final double[] vv=intdei(qf, 0.0, tol);
		System.out.println (vv[0]);
 		return vv[0]*ret;
	}

	public static void main(String[] args) {
		MWirghtM g= new MWirghtM(-0.5, 0.5);
		
		double ret=g.eval(1.);
		System.out.println ("E(1, 1 |0) "+ ret);
		
		 
		
		
	}

}
