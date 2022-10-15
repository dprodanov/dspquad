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
public class MiLe1 implements QFunction {
	
	public final static double TWOPI=2.*PI;
	
	double z=0;
	double a=0;
	double value=0;

	private KerF1 qf1=new KerF1();
	private KerP1 pf1= new KerP1();
	
	private Gamma gam=new Gamma();
	private double tol=1.0e-15;
	private double gm=1.0;
	private double geps=1.0e-2;

	/////////////////////////
	//  Integral Kernels
	////////////////////////
	

	/*
	 * Ker=(r^(1/a)*%e^(-r^(1/a)))/(z^2-2*cos(%pi*a)*r*z+r^2)
	 */
	private class KerF1 implements QFunction {

		double a=0;
		double z=0;	

		public void setVal(double aa,  double  zz) {
			a=aa;
			z=zz;
		}
	
		@Override
		public double eval(double r) {
			if (r==0) return(0);
			final double ra=pow(r,1/a);
			double denom=(r*r-2*cos(PI*a)*r*z+z*z);
			return ra*exp(-ra)/denom;
		}
		
		@Override
		public double prefactor() {
			return -sin(PI*a)/a;
		}

		@Override
		public String toString() {
			return "(r^(1/a)*%e^(-r^(1/a)))/(z^2-2*cos(%pi*a)*r*z+r^2)";		
		}

	}
	
	
	 
	////////////////////////////
	
	/*
	 * Ker=(%e^(eps*cos(u))*(cos(eps*sin(u)+u)*z-eps^a*cos(eps*sin(u)+(1-a)*u)))/(z^2-2*eps^a*cos(a*u)*z+eps^(2*a))
	 */
	
	private class KerP1 implements QFunction {

		double a=0;
		double z=0;
		private double eps=1;

		public void setVal(double aa,  double ee, double  zz) {
			a=aa;
			eps=ee;
			z=zz;
		}
		

		@Override
		public double eval(double u) {
			final double ea=pow(eps,a);
			final double unum=ea*cos(eps*sin(u)+(1-a)*u)-cos(eps*sin(u)+u)*z;
			final double denom=(z*z-2*ea*cos(a*u)*z+ea*ea);
			final double ret=exp(eps*cos(u))*unum/denom;
			return ret;	
		}
		
		@Override
		public double prefactor() {
			return eps/TWOPI;
		}
			
		@Override
		public String toString() {
			return "-eps*(exp(eps*cos(u))*(cos(eps*sin(u)+u)*z-eps^a*cos(eps*sin(u)+(1-a)*u)))/(z^2-2*eps^a*cos(a*u)*z+eps^(2*a))"; 
		}

	}
	////////////////////////////

	/**
	 * 
	 */
	public MiLe1(double aa) {
		a=aa;
		fr=sin(PI*aa)*fr;
		gm=1/gam.eval(aa);
		
	}
	
	private double fr=1/PI;
	
	/**
	 * 
	 */
	public MiLe1(double aa, double ttol) {
		a=aa;
		tol=ttol;
		fr=sin(PI*aa)*fr;
		gm=1/gam.eval(aa);

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
  	public double phaseintML1 (double x, double a, double eps) {
		pf1.setVal(a, eps, x);
		double ret=pf1.prefactor();
		final double[] pp1=intde(pf1, -PI, PI, tol);
		//System.out.println("pp1 "+pp1[0]+" eps "+eps );
		ret=ret*pp1[0]; 
		return ret;
	}
	
	
	/* (non-Javadoc)
	 * @see ijaux.quad.QFunction#eval(double)
	 */
	@Override
	public double eval(double x) {
		double delta=256.0;
		z=x;
		if (a==0) return 0;
		double eps=16;
	
			if (abs(z)>=1/2) {
				
				eps=abs(delta*z/(delta-1));
	
		
				eps=pow(eps, 1/a);
				eps=min(eps, 16);
				eps=max(eps, 12);
				
				System.out.println(eps); 
			}
			
			if (fr!=0) {
				value=lineML1(z, a, eps);
			}
		
			double phase=phaseintML1(z, a, eps);
			value=value+phase;
 
		return value;
	}

	/**
	 * @param x
	 * @param a
	 * @param eps
	 * @return 
	 */
	private double lineML1(double x, double a, double eps) {
		qf1.setVal(a, x);
		double fr=qf1.prefactor();
		final double[] vv=intdei(qf1, eps, tol);
		return vv[0]*fr;
	}
	
	 

	/* (non-Javadoc)
	 * @see ijaux.quad.QFunction#getFormula()
	 */
	@Override
	public String toString() {
		return "E(a,a,x)";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("case 1/4");
		MiLe1 ml= new MiLe1(0.25);
		System.out.println (" E(1/4| 0)= " +ml.eval(0.0) +" "+ ml.gm);
		System.out.println (" E(1/4| 3/2)= " +ml.eval(1.5) +" 2132.96961241502 " );
		ml= new MiLe1(0.3333333333);
			System.out.println (" E(1/3| 0)= " +ml.eval(0.0) +" "+ ml.gm);
			System.out.println (" E(1/3| 3/2)= " +ml.eval(1.5) +" 197.398225135384 " );
		ml= new MiLe1(0.5);
	
		System.out.println (" E(1/2| 0)= " +ml.eval(0.0) +" "+ ml.gm);
		System.out.println (" E(1/2| 1/2)= " +ml.eval(0.5) +" 1.540369828139035");
		System.out.println (" E(1/2| 2)= " +ml.eval(2.0) +" 218.4459983635037");
		System.out.println (" E(1/2| 3)= " +ml.eval(3) +" 48618.53075158231");
		System.out.println("case 3/2");
		ml= new MiLe1(1.5);
		System.out.println (" E(3/2| 0)= " +ml.eval(0.0) +" "+ml.gm);
		System.out.println (" E(3/2| 1/2)= " +ml.eval(0.5) +" 1.400947959370093");
		System.out.println("case 5/2");
		ml= new MiLe1(2.5);
		System.out.println (" E(5/2| 0)= " +ml.eval(0.0) +" "+ml.gm);
		System.out.println("case 3");
		ml= new MiLe1(3.0);
		System.out.println (" E(3| 0)= " +ml.eval(0.0) +" "+ml.gm);
		System.out.println (" E(3| 3/2)= " +ml.eval(1.5) +" 0.5125558881803863 ");
		
	}

}
