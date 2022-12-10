package ijaux.quad.misc;

import static ijaux.quad.QuadDE.intdei;

import ijaux.quad.QFunction;
import ijaux.quad.wright.GMWright;
import static java.lang.Math.exp;

public class MiLeW implements QFunction {
	
	GMWright gmw;
	private double a;
	private double b;
	
	
	////////////////////
	private class KerF implements QFunction {
	 
		double z=0;
		
		public void setVal( double  zz) {
			z=zz;
		}
		
		@Override
		public double eval(double u) {
			final double bk=gmw.eval(u*z);
			return exp(-u)*bk;
		}
		
		public double prefactor() {
			return fr;
		}
		
		@Override
		public String toString() {
			return "exp(-u)* W(a,b, z u)"; 
		}
	}
	//////////////////
	
	private KerF qf=new KerF();
	private double tol=1.0e-15;
	
	double fr=1.;
	/**
	 * 
	 */
	public MiLeW(double aa, double bb) {
		a=aa;
		b=bb;
		gmw= new GMWright(a,b);
	}

	@Override
	public double eval(double x) {
		double ret=qf.prefactor();
		qf.setVal(x);
		final double[] vv=intdei(qf, 0.0, tol);
 		return vv[0]*ret;
	}

	public static void main(String[] args) {
		MiLeW g= new MiLeW(1.0, 1.0);
		
		double ret=g.eval(0);
		System.out.println ("E(1, 1 |0) "+ ret);
		
		double x=5.;
		
		ret=g.eval(x);
		System.out.println ("E(1, 1 |5) "+ ret+ " "+ Math.exp(x));
		
		// (exp(x)-1)/x
		g=new MiLeW(1.0, 2.0);

		ret=g.eval(x);
		System.out.println ("E(1, 2 |5) "+ ret+ " "+ (Math.exp(x)-1.)/x);
		
		g=new MiLeW(2.0, 3.0);

		ret=g.eval(x);
		System.out.println ("E(1, 2 |5) "+ ret+ " "+ (Math.exp(Math.sqrt(x))/(2.*x)+Math.exp(-Math.sqrt(x))/(2.*x)-1./x ));
		
		
		
	}

}
