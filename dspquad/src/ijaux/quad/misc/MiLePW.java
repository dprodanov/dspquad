package ijaux.quad.misc;

import static ijaux.quad.QuadDE.intdei;

import ijaux.quad.QFunction;
import ijaux.quad.euler.Gamma;
import ijaux.quad.wright.GMWright;

import static java.lang.Math.pow;
import static java.lang.Math.exp;

public class MiLePW implements QFunction {
	
	GMWright gmw;
	private double a;
	private double b;
	private double g;
	
	private Gamma gam=new Gamma();
	
	////////////////////
	private class KerF implements QFunction {
	 
		double z=0;
		double g=0;
		
		public void setVal(double gg, double  zz) {
			z=(zz);
			g=gg;
		}
		
		@Override
		public double eval(double u) {
			final double bk=gmw.eval(u*z)*pow(u, g-1.);
			return exp(-u)*bk;
		}
		
		public double prefactor() {
			return fr;
		}
		
		@Override
		public String toString() {
			return "exp(-u)* u^(g-1)* W(a,b, zu)"; 
		}
	}
	//////////////////
	
	private KerF qf=new KerF();
	private double tol=1.0e-15;
	
	
	double fr=1.;
	/**
	 * 
	 */
	public MiLePW(double aa, double bb, double gg) {
		a=aa;
		b=bb;
		g=gg;
		gmw= new GMWright(a,b);
		fr=1/gam.eval(g);

	}

	@Override
	public double eval(double x) {
		double ret=qf.prefactor();
		qf.setVal(g, x);
		final double[] vv=intdei(qf, 0.0, tol);
 		return vv[0]*ret;
	}

	public static void main(String[] args) {
		MiLePW g= new MiLePW(1.0, 1.0, 1.0);
		
		double ret=g.eval(0);
		System.out.println ("E(1, 1 |0) "+ ret);
		
		double x=5.;
		
		ret=g.eval(x);
		System.out.println ("E(1, 1 |5) "+ ret+ " "+ exp(x));
		
		// (exp(x)-1)/x
		g=new MiLePW(1.0, 2.0, 1.);
		 x=.5;
		ret=g.eval(x);
		System.out.println ("E(1, 2 |5) "+ ret+ " "+ (exp(x)-1.)/x);
		
		
		
		
	}

}
