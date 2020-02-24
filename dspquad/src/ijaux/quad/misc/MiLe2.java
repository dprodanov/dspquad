/**
 * 
 */
package ijaux.quad.misc;

import static ijaux.quad.QuadDE.intde;
import static java.lang.Math.*;

import ijaux.quad.QFunction;
import ijaux.quad.euler.Gamma;




 

/**
 * @author prodanov
 *
 */
public class MiLe2 implements QFunction {
	
	public final static double TWOPI=2*PI;
	
	double z=0;
	double a=1.0;
	double b=1.0;
	double value=0;
	
	//private KerF qf=new KerF();
	private KerPP1 qp1= new KerPP1();
	private KerPP2 qp2= new KerPP2();
 
	private double tol=1.0e-15;
	private double gm=1.0;
	private double geps=1.0;

	/////////////////////////
	//  Integral Kernels
	////////////////////////
	

	
	
	/////////////////////////
	/*
	*  
	*  PPm1(z,a,b, eps):= (exp(eps*cos(phi))*cos(eps*sin(phi)+(1-b)*phi))/(z^2-2*eps^a*cos(a*phi)*z+eps^(2*a));
	*/
	private class KerPP1 implements QFunction {

		double a=0, b=1;
		double z=0;
		private double eps=1;

		public void setVal(double aa, double bb, double ee, double  zz) {
			a=aa;
			b=bb;
			eps=ee;
			z=(zz);
		}

		@Override
		public double eval(double u) {
			double ea=pow(eps,a);
			double uab=eps*cos(u);
			return exp(uab)*cos(eps*sin(u)+(1-b)*u)/(z*z-2*ea*cos(a*u)*z+ea*ea);		
		}

		@Override
		public String toString() {
			return "exp(eps*cos(phi))*cos(eps*sin(phi)+(1-b)*phi)/(z^2-2*eps^a*cos(a*phi)*z+eps^(2*a));"; 
		}

	}
	////////////////////////////
		
	/////////////////////////
	/*
	*  
	*  PPm2(z,a,b, eps):=(exp(eps*cos(phi))*cos(eps*sin(phi)+(-b+a+1)*phi)*z)/(z^2-2*eps^a*cos(a*phi)*z+eps^(2*a));
	*
	*/
	private class KerPP2 implements QFunction {

		double a=0, b=1;
		double z=0;
		private double eps=1;

		public void setVal(double aa, double bb, double ee, double  zz) {
			a=aa;
			b=bb;
			eps=ee;
			z=(zz);
		}

		@Override
		public double eval(double u) {
		 	double ea=pow(eps,a);
			double uab=eps*cos(u);
			return exp(uab)*cos(eps*sin(u)+(1-b+a)*u)*z/(z*z-2*ea*cos(a*u)*z+ea*ea);		
		}

		@Override
		public String toString() {
			return "(exp(eps*cos(phi))*cos(eps*sin(phi)+(-b+a+1)*phi)*z)/(z^2-2*eps^a*cos(a*phi)*z+eps^(2*a))";
		}

	}
	////////////////////////////

	/**
	 * 
	 */
	public MiLe2(double aa, double bb) {
		a=aa;
		b=bb;
		Gamma gam=new Gamma();
		//gm=gam.eval(b);
	}
	
	/**
	 * 
	 */
	public MiLe2(double aa, double bb, double ttol) {
		a=aa;
		b=bb;
		tol=ttol;

	}
	
	/*
	 * sets the global epsilon
	 */
	public void setEps(double ge) {
		geps=ge;
	}
	
	/*
	 * 
	 */
	
	public double phaseintML (double x, int a, int b, double eps) {
		double ret=0;
		qp1.setVal(a, b,  eps, x);
		final double[] pp1=intde(qp1, -PI, PI, tol);
		qp2.setVal(a, b,  eps, x);
		final double[] pp2=intde(qp2, -PI, PI, tol);
		//float(eps^(1-b+2*a)*pp1/2/%pi - eps^(1-b+a)* pp2/2/%pi)
		ret= pow(eps, 1.0-b +2.0*a)*pp1[0]/TWOPI-pow(eps, 1.0-b +a)*pp2[0]/TWOPI;
		return ret;
	}

	/* (non-Javadoc)
	 * @see ijaux.quad.QFunction#eval(double)
	 */
	@Override
	public double eval(double x) {
		z=x;
		if (a==0) return 0;
		if (x==0) return 1.0;
		if (a==1) return exp(x); 
		
		return 0;
	}

	/* (non-Javadoc)
	 * @see ijaux.quad.QFunction#getFormula()
	 */
	@Override
	public String toString() {
		return "E(a,b,x)";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
