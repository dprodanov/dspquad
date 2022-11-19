/**
 * 
 */
package ijaux.quad.misc;

import static ijaux.quad.QuadDE.intde;
import static ijaux.quad.QuadDE.intdei;
import static java.lang.Math.*;

import ijaux.quad.QFunction;
import ijaux.quad.euler.Gamma;




 

/**
 * @author prodanov
 *
 */
public class MiLe2 implements QFunction {
	
	public final static double TWOPI=2.*PI;
	
	double z=0;
	double a=1.0;
	double b=1.0;
	double value=0;
	

	private KerP qp= new KerP();
	private KerL ql= new KerL();
 
	private double tol=1.0e-15;
	private double gm=1.0;
	private double geps=1.0;

	/////////////////////////
	//  Integral Kernels
	////////////////////////
	

	
	
	/////////////////////////
	/*
	*  
	*  kercirc2(phi,z,eps):=(%e^(eps*cos(phi)*abs(z))*(cos(eps*sin(phi)*abs(z)+(-b+a+1)*phi)+eps^a*cos(eps*sin(phi)*abs(z)+(1-b)*phi)))/(2*sgn(z)*eps^a*cos(a*phi)+eps^(2*a)+1)
	*/
	private class KerP implements QFunction {

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
			double uab=eps*cos(u)*abs(z);
			return exp(uab)*(cos(eps*sin(u)*abs(z)+(1.-b+a)*u)+ea*cos(eps*sin(u)*abs(z)+(1-b)*u))/(2.*sgn(z)*ea*cos(a*u)+ea*ea+1.);		
		}
		
		private double sgn(double x) {
			if (x>0) return 1.0;
			else return -1.0;
		}

		@Override
		public String toString() {
			return "(exp(eps*cos(phi)*abs(z))*(cos(eps*sin(phi)*abs(z)+(-b+a+1)*phi)+eps^a*cos(eps*sin(phi)*abs(z)+(1-b)*phi)))/(2*sgn(z)*eps^a*cos(a*phi)+eps^(2*a)+1);"; 
		}

	}
	////////////////////////////
		
	/////////////////////////
	/*
	*  
	*  kerlin2(r,z):= (r^(a-b)*(sin(%pi*b)*r^a+ sgn(z)*sin(%pi*(b-a)))*%e^(-r*abs(z))) /(r^(2*a)+2*cos(%pi*a)*r^a+1);
	*
	*/
	private class KerL implements QFunction {

		double a=1., b=1.;
		double z=0;
		//private double eps=1.;

		public void setVal(double aa, double bb, double  zz) {
			a=aa;
			b=bb;
			//eps=ee;
			z=(zz);
		}

		@Override
		public double eval(double u) {
		 	double ra=pow(u,a);
			double rab=pow(u,a-b);
			return  (rab*(sin(PI*b)*ra+ sgn(z)*sin(PI*(b-a)))*exp(-u*abs(z))) /(ra*ra+2.*cos(PI*a)*ra+1.);	
		}
		
		private double sgn(double x) {
			if (x>0) return 1.0;
			else return -1.0;
		}

		@Override
		public String toString() {
			return "(r^(a-b)*(sin(%pi*b)*r^a+ sgn(z)*sin(%pi*(b-a)))*%e^(-r*abs(z))) /(r^(2*a)+2*cos(%pi*a)*r^a+1);";
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
		gm=1./gam.eval(b);
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
	
	private double compute (double x, double a, double b, double eps) {
		double ret=0;
		qp.setVal(a, b,  eps, x);
		final double[] pp1=intde(qp, -PI, PI, tol);
		ql.setVal(a, b,   x);
		final double[] pp2=intdei(ql, eps,  tol);
		
		ret= pow(eps, 1.-b+a)*pp1[0]/TWOPI + pp2[0]/PI;
		return ret;
	}

	/* (non-Javadoc)
	 * @see ijaux.quad.QFunction#eval(double)
	 */
	@Override
	public double eval(double x) {
		z=x;
		if (a==0) return 0;
		if (x==0) return gm;
		//if (a==1) return exp(-x); 
		double ret= pow(z, 1.-b)*compute(x,a,b, 1.+geps);
		return ret;
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
		double a=1.; double b=0.5;
		MiLe2 ml=new MiLe2(a, b);
		ml.setEps(0.05);
		System.out.println(ml.eval(0.000001) + " " +0.5641895835477563);
	}

}
