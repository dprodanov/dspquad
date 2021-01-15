/**
 * 
 */
package ijaux.quad.gbessel;
import static ijaux.quad.QuadDE.intdei;
import static java.lang.Math.*;

import ijaux.quad.QFunction;

/**
 * @author prodanov
 *   
 * implements Bessel K_n(x)
 */
public class BesselKN implements QFunction {

	private int n=0;
	private KerF qf=new KerF();
	private double tol=1.0e-16;
	
	private class KerF implements QFunction {

		double a=0;
		double z=0;

		 	
		public void setVal(double aa, double  zz) {
			a=aa;
			z=(zz);
		}
		
		@Override
		public double eval(double u) {
			return cosh(a*u)*exp(-z*cosh(u));
		}

		@Override
		public String toString() {
			return "cosh(a*u)*exp(-z*cosh(u)) ";
		}
		
	}
	
	public BesselKN(int nn) {
		n=nn;
	}
	
	
	/* (non-Javadoc)
	 * @see ijaux.quad.QFunction#eval(double)
	 */
	@Override
	public double eval(double x) {
		qf.setVal(n,x);
		double ret=qf.prefactor();
		final double[] vv=intdei(qf, 0.0, tol);
		double value=vv[0]*ret;

		return value;
	}
	
	@Override
	public String toString() {
		return "K(a,z)";
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BesselKN bn= new BesselKN(0);
		System.out.println("K0(1)="+bn.eval(1.0) +"  " +0.421024438240708333);
		
	}
}
