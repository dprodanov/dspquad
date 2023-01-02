package ijaux.quad.wright;

import static ijaux.quad.QuadDE.*;
import static java.lang.Math.*;

import ijaux.quad.QFunction;
import ijaux.quad.euler.Gamma;

/*
 * 2 parameter (General) Wright function
 * 
 * 

Wright kernel

$
\frac{1}{2 \pi i}\int_{Ha} exp(\xi +z/\xi^a) /\xi^b d \xi
$
cases b<=1 
a<0
 

@Article{Luchko2008,
  author  = {Y. Luchko},
  title   = {Algorithms for evaluation of the {Wright} function for the real arguments' values},
  journal = {Fract. Calc. Appl. Anal.},
  year    = {2008},
  volume  = {11},
  number  = {1},
  pages   = {57 --75},
}


Method of stationary phase


@Article{Uluisik2009,
  author    = {C. Uluisik and L. Sevgi},
  title     = {A Tutorial on Bessel Functions and Numerical Evaluation of Bessel Integrals},
  journal   = {{IEEE} Antennas and Propagation Magazine},
  year      = {2009},
  volume    = {51},
  number    = {6},
  pages     = {222--233},
  month     = {dec},
  doi       = {10.1109/map.2009.5433159},
  publisher = {Institute of Electrical and Electronics Engineers ({IEEE})},
}

*/
public class GMWright implements QFunction {

	public final static double TWOPI=2*PI;
	/////////////////////////
	//  Integral Kernels
	////////////////////////
	
	/**
	 * Kwg(z, a, b):=u^(1/b-2)*%e^(-(cos(%pi*a)*z)/u^(a/b)-u^(1/b))*sin((sin(%pi*a)*z)/u^(a/b)-%pi*b);
	 *      
	 * 
	 * @author Dimiter Prodanov
	 */
	private class KerF implements QFunction {

		double a=0, b=1;
		double z=0;

		/**
		 * 
		 * @param aa
		 * @param bb
		 * @param zz
		 */
		public void setVal(double aa, double bb, double  zz) {
			a=aa;
			b=bb;
			z=zz;
		}
	
		@Override
		public double eval(double u) {
			if (b==0) return 0;
			final double uab=pow(u,a/b);
			final double ub=pow(u,1./b-2.);
			return ub*exp(-(cos(PI*a)*z)/uab-pow(u,1./b))*sin(-(sin(PI*a)*z)/uab+PI*b);
		}

		@Override
		public String toString() {
			return "u^(1/b-2)*e^(-(cos(PI*a)*z)/u^(a/b)-u^(1/b))*sin(-(sin(PI*a)*z)/u^(a/b)+PI*b)";
		}

	}
	
	////////////////////////////
	//  case b=0
	///////////////////
	private class KerF0 implements QFunction {

		double a=0;
		double z=0;
			
		public void setVal(double aa, double zz) {
			a=aa;
			z=zz;
		}
		
		@Override
		public double eval(double r) {
			final double ra=pow(r,a);
			return exp(-(cos(PI*a)*z)/ra-r)*sin(-(sin(PI*a)*z)/ra);
		}

		@Override
		public String toString() {
			return "exp(-(cos(PI*a)*z)/r^a-r)*sin(-(sin(PI*a)*z)/r^a)";
		}
		
	}
	/////////////////
	
	////////////////////////////
	//  case b=0
	///////////////////
	/**
	 * Kwgr(z, a, b):=%e^(-(cos(%pi*a)*z)/r^a-r)*sin(-(sin(%pi*a)*z )/r^a + %pi *b)/r^b;
	 *
	 */
	private class KerFr implements QFunction {
	
	double a=0;
	double z=0;
	
	/**
	 * 
	 * @param aa
	 * @param bb
	 * @param zz
	 */
	public void setVal(double aa, double bb, double  zz) {
		a=aa;
		b=bb;
		z=zz;
	}
	
	@Override
	public double eval(double r) {
		final double ra=pow(r,a);
		final double rb=pow(r,b);
		return exp(-(cos(PI*a)*z)/ra-r)*sin(-(sin(PI*a)*z)/ra +PI*b)/rb;
	}
	
	@Override
	public String toString() {
		return "exp(-(cos(PI*a)*z)/r^a-r)*sin(-(sin(PI*a)*z)/r^a + PI*b)/r^b";
	}
	
	}
	/////////////////
	
	/**
	 * Kwg1(z, a, b):=u^((1-b)/a-1)*%e^(-(cos(%pi*a)*z)/u-u^(1/a))* sin((sin(%pi*a)*z)/u-%pi*b);
	 * 
	 * @author Dimiter Prodanov
	 *
	 */
	private class KerF1 implements QFunction {

		double a=0;
		double b=0;
		double z=0;
	
		public void setVal(double aa, double bb, double zz) {
			a=aa;
			b=bb;
			z=zz;
		}
		
		@Override
		public double eval(double u) {
			if (u==0) return 0.0;
			final double ee=exp(-(cos(PI*a)*z)/u-pow(u,1./a));
			return pow(u,(1.-b)/a-1.)*ee*sin(sin(PI*a)*z/u-PI*b);
		}

		@Override
		public String toString() {
			return "u^((1-b)/a-1)*exp(-(cos(PI*a)*z)/u-u^(1/a))*sin((sin(PI*a)*z)/u-PI*b)";
		}
		
	}
	////////////////////////////
	
	/////////////////////////
	/*
	 * PP(z,a,b,eps):= exp(eps*cos(phi)-(cos(a*phi)*z)/eps^a) *cos((sin(a*phi)*z)/eps^a+eps*sin(phi)+ (1-b)*phi);
	 * 
	 * circular integral     
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
	 		final double ea=pow(eps,a);
			double uab=sin(a*u)*z/ea+eps*sin(u)+(1.-b)*u;
			uab=cos(uab);
			return exp(cos(u)*eps-cos(a*u)*z/ea)*uab;			
		}

		@Override
		public String toString() {
			return "exp(eps*cos(phi)-(cos(a*phi)*z)/eps^a) *cos((sin(a*phi)*z)/eps^a+eps*sin(phi)+ (1-b)*phi)"; 
		}

	}
	////////////////////////////
	
	double z=0;
	double a=0, b=1;
	double value=0;
	 
	//ray
	private KerF qf=new KerF();
	private KerF0 qf0=new KerF0();
	private KerFr qfr=new KerFr();
	private KerF1 qf1=new KerF1();
	
	// arch
	private KerP qp= new KerP();
 
	private double tol=1.0e-15;
	// unused
//	private double geps=-log(tol)/log(10);
	
	private double gm=1.0;
	
	/**
	 * 
	 * @param aa
	 * @param bb
	 */
	public GMWright(double aa,  double bb) {
		a=aa;
		b=bb;
		Gamma gam=new Gamma();
		gm=gam.eval(b);
		
	}
	
	/**
	 * 
	 * @param aa
	 * @param bb
	 * @param ttol
	 */
	public GMWright(double aa, double bb, double ttol) {
		a=aa;
		b=bb;
		tol=ttol;
		Gamma gam=new Gamma(ttol);
		gm=gam.eval(b);
	}

 
	/**
	 *  @param ge - sets the global epsilon
	 *
	public void setEps(double ge) {
		geps=ge;
	}
	*/


	/**
	 * calculates the circular integral contribution
	 * @param x
	 * @param a
	 * @param b
	 * @param eps - radius
	 * @return
	 */
	private double phaseint(double x, double a, double b, double eps) {
		double ret=0;
		qp.setVal(a, b,  eps, x);
		final double[] vvp=intde(qp, -PI, PI, tol);
		ret= pow(eps, 1.-b)*vvp[0]/TWOPI;
		return ret;
	}
	
	
	@Override
	public double eval(double x) {
		z=x;
		if (a==0 && b==0) return 0;
		if (x==0 && b==0) return 0;
		if (x==0) return 1.0/gm;
		// special case
		if (a==0) return exp(z)/gm;
		
		double fr=1.0/PI;	
		//System.out.println("a "+a+" b "+b);
		if (a<0) {
			// special case
			if (b==0) {
				//System.out.println(" case a<0, b=0 ");
				qf0.setVal(a,-x);
				final double[] vv=intdei(qf0, 0.0, tol);
				value=vv[0]*fr;
				return value;	
			} 	// end case
			if (b==1) {
				// special case
				//System.out.println(" case  a<0,  b=1");
				if (x==0) 
					value=0; 
				else {
				/*	qf1.setVal(a, 1.0, -x);
					final double[] vv=intdei(qf1, 0.0, tol);
					value=vv[0]*fr/a; */
					qfr.setVal(a, 1., -x);
					final double[] vv=intdei(qfr, 0.0, tol);
					value=vv[0]*fr;
				}
				value=1.0+value;
				return value;
			} // end case
			if (b>0) {
	 	 		if (b<1) {
	 	 			//fr=1./PI;
	 	 			//System.out.println(" case a<0,   b<1:  "+b);
	 	 			qf1.setVal(a, b, -x);
 	 				final double[] vv=intdei(qf1, 0.0,  tol);
 	 				value=vv[0]*fr/a;
	 	 		} else { // b>1
	 	 			//System.out.println("case a<0,  b>1:  "+b);
	 	 			double phase=phaseint(-x, a, b, 1.0);
	 	 			//qf.setVal(a,b,-x);
	 	 			qfr.setVal(a, b, -x);
	 	 			final double[] vv=intdei(qfr, 1.0,  tol);
	 	 			//value=vv[0]*fr/b +phase;
					value=vv[0]*fr +phase;
	 	 		}
			} else  { // b<0
				//System.out.println("case a<0,  b<0:  "+b);
				qf1.setVal(a, b, -x);
				final double[] vv=intdei(qf1, 0.0,  tol);
				value=vv[0]*fr/a;
			}
		} else { // a>0
			//System.out.println("case a>0,  b :  "+b);
			//double eps=min(geps,32.0);
			
			// stationary phase method
			double eps= pow(abs(a*x), 1./(a+1.));
			//  System.out.println("eps "+eps);
			 		
			qf1.setVal(a, b, -x);
			final double[] vv=intdei(qf1, pow(eps, a), tol);
			value=-fr/a*vv[0]; 
			
			double phase=phaseint(-x, a, b, eps);
			//value=phase;
			//System.out.println("phase "+phase+ " line "+value);
			value=value+phase;
			
		}
		return value;
	}
	
	@Override
	public String toString() {
		return "W("+a+","+b+",z)";
	}
	/////////////////////////////
	
	////////////////////////////
	//  Testing
	///////////////////////////
	public static void main(String[] args) {
		
		//mSystem.out.println("I =\\frac{1}{2 \\pi i}\\int_Ha^{-} exp(xi -z/xi^a)/xi^(b) dxi\n");
		
		
		System.out.println("erf");
		GMWright g= new GMWright(-0.5, 1.0);
		
		double ret=g.eval(0);
		System.out.println (" W(0 | -1/2, 1)= "+ ret+"\t, "+2.*0.5);
		 long time=System.nanoTime();
		ret=g.eval(2);
		time-=System.nanoTime();
		
		 System.out.println (" W(2 | -1/2, 1)= "+ ret+"\t, "+2.*0.9213503964748574 +"\t, "+(-time));
		
		
		//////////////////////////////////////
		//  Gaussian
		////////////////////////////////
		System.out.println("Gaussian");
		
		g= new GMWright(-0.5, 0.5);
		time=System.nanoTime();
		ret=g.eval(0.5);
		time-=System.nanoTime();
		System.out.println (" W(1/2 | -1/2, 1/2)= "+ ret+"\t, "+0.5300070646880571+"\t, "+(-time) );
		ret=g.eval(1.5);
		System.out.println (" W(3/2 | -1/2, 1)= "+ ret+"\t, "+0.3214655345976037);
			
		//////////////////////////////////
		//  Gaussian derivative 1
		////////////////////////////////
		System.out.println("GD 1");
		g= new GMWright(-0.5, 0);
		
		ret=g.eval(0.5);
		System.out.println (" W(1/2 | -1/2, 0)= "+ ret+"\t, "+-0.1325017661720143);
		
		ret=g.eval(2.5);
		System.out.println (" W(5/2 | -1/2, 0)= "+ ret+"\t, "+-0.1478257015295567);
		
		//////////////////////////////////
		//  Gaussian derivative 2
		////////////////////////////////
		System.out.println("GD 2");
		g= new GMWright(-0.5, -0.5);	
		
		time=System.nanoTime();
		ret=g.eval(1.5);
		time-=System.nanoTime();
		System.out.println (" W(3/2 | -1/2, -1/2)= "+ ret+"\t, "+2.*0.01004579795617512+"\t, "+(-time) );
		
		ret=g.eval(2.5);
		System.out.println (" W(5/2 | -1/2, -1/2)= "+ ret+"\t, "+2.*0.06282592315006161);
		
		//////////////////////////////////////
		//  Gaussian derivative 3
		////////////////////////////////
		System.out.println("GD 3");
		g= new GMWright(-0.5, -1.0);	
		
		ret=g.eval(1.5);
		System.out.println (" W(3/2 | -1/2, -1)= "+ ret+"\t, "+2.*0.11301522700697);
		
		ret=g.eval(2.5);
		System.out.println (" W(5/2 | -1/2, -1)= "+ ret+"\t, "+-2*0.004619553172798648);
		
		//////////////////////////////////////
		//  Gaussian derivative 4
		////////////////////////////////
		System.out.println("GD 4");
		g= new GMWright(-0.5, -1.5);	
		
		ret=g.eval(1.5);
		System.out.println (" W(3/2 | -1/2, -3/2)= "+ ret+"\t, "+-2.*0.0998301171894902);
		
		ret=g.eval(2.5);
		System.out.println (" W(5/2 | -1/2, -3/2)= "+ ret+"\t, "+-2.*0.08846444325909411);
		
		//////////////////////////////////////
		//  Gaussian derivative 5
		////////////////////////////////
		System.out.println("GD 5");
		g= new GMWright(-0.5, -2.0);	
		
		ret=g.eval(1.5);
		System.out.println (" W(3/2 | -1/2, -2)= "+ ret+"\t, "+-2.*0.1511578661218224);
		
		ret=g.eval(2.5);
		System.out.println (" W(5/2 | -1/2, -2)= "+ ret+"\t, "+2.*0.1198196604194649);

		////////////////////////
		// Airy
		//////////////////////
		g= new GMWright(-0.333333333 , 0.66666667);
		System.out.println("Airy");
		time=System.nanoTime();
		ret=g.eval(-0.5);
		time-=System.nanoTime();
		System.out.println (" W(1/2 | -1/3, 2/3)= "+ ret+"\t, "+0.5563338386752553 +"\t, "+(-time));
	 
		ret=g.eval(-2.0);
		System.out.println (" W(-2 | -1/3, 2/3)= "+ ret+"\t, "+0.1736639759810553);

						
		////////////////////////
		// Bessel
		//////////////////////
		System.out.println("Bessel J");
		g= new GMWright(1.0, 1.5);
		time=System.nanoTime();
		ret=g.eval(0);
		time-=System.nanoTime();
		System.out.println("Bessel J1/2");
		System.out.println (" W(0 | 1 , 3/2)= "+ ret+"\t, "+1.128379167095513 +" "+ abs(ret-1.128379167095513) +"\t, "+(-time));
		time=System.nanoTime();
		ret=g.eval(-9.869604401089358);
		time-=System.nanoTime();
		System.out.println (" W(-PI^2 | 1 , 3/2)= "+ ret+  "\t, "+(-time));
		
		System.out.println("Bessel J0");
		g= new GMWright(1.0, 1.0);
		time=System.nanoTime();
		ret=g.eval(-1.445796490736696);
		time-=System.nanoTime();
		System.out.println (" W(-w0^2/4 | 1 , 1)= "+ ret+  "\t, "+(-time));
	}



}
