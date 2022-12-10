package ijaux.quad.wright;

import static ijaux.quad.QuadDE.intdei;
import static ijaux.quad.QuadDE.intdeo;
import static java.lang.Math.*;

import ijaux.quad.QFunction;
import ijaux.quad.euler.Gamma;

/*
 * 1 parameter M-Wright function
 * 
 * 

M-Wirght kernels

\frac{1}{2 \pi i}\int_{Ha} exp(\xi -z/\xi^a)*\xi^(a-1) d \xi
 
Algorithms for evaluation of the Wright function for the real arguments' values. 
Fract. Calc. Appl. Anal. 11 : 57-75, 2008
*/
public class MWright implements QFunction {
	Gamma gam=new Gamma();
	
	private class KerF implements QFunction {

		double a=0;
		double z=0;
				
		public void setVal(double aa, double  zz) {
			a=aa;
			z=abs(zz);
		}
		
		@Override
		public double eval(double u) {
			return exp(-cos(PI*a)*u*z-pow(u, 1./a))*sin(sin(PI*a)*u*z-PI*a);
		}

		@Override
		public String toString() {
			return "exp(-cos(PI*a)*u*z-(u^1/a)*sin(sin(PI*a)*u*z-PI*a)";
		}
		
		@Override
		public double prefactor() {
			return -1./(a*PI);
		}
		
	}
	////////////////////////////
	double z=0;
	protected double a=0;
	double value=0;
	 
	private KerF qf=new KerF();
	private double tol=1.0e-15;
	
	
	public MWright(double aa) {
		a=aa;
	}
	
	public MWright(double aa, double ttol) {
		a=aa;
		tol=ttol;
	}

	@Override
	public double eval(double x) {
		if (x==0) {
			return 1./gam.eval(1.-a);
		}
		qf.setVal(a,x);
		//double ret=-1.0/(a*PI);
		double ret=qf.prefactor();
		z=x;
		if (a<=0.5) {
			final double[] vv=intdei(qf, 0.0, tol);
			value=vv[0]*ret;
		} else {
		final double[] vv=intdeo(qf, 0.0, sin(PI*a)*z, tol);
			value=vv[0]*ret;
		}
		return value;
	}

	public static void main(String[] args) {
		
		System.out.println("I =\\frac{1}{2 \\pi i}\\int_Ha^{-} exp(xi -z/xi^a)*xi^(a-1) dxi\n");
		MWright g= new MWright(0.5);
		
		double ret=g.eval(0);
		System.out.println (" M(1/2| 0)="+ ret+"\t, "+0.5641895835477563);
		 ret=g.eval(0.5);
		System.out.println (" M(1/2| 0.5)="+ ret+"\t, "+0.5300070646880571);
		
		 ret=g.eval(15);
			System.out.println (" M(1/2| 15)="+ ret+"\t, ");
			
		g= new MWright(0.33333333333333333);
		ret=g.eval(0.5);
		System.out.println (" M(1/3| 0.5)="+ ret+"\t, "+0.5563338386752553);
		
		g= new MWright(0.666666666666666667);
		ret=g.eval(0.5);
		System.out.println (" M(2/3| 0.5)="+ ret+"\t, "+0.4858328419352538);
		ret=g.eval(-2.0);
		System.out.println (" M(2/3| -2.0)= "+ ret+"\t, "+0.2483373615570918);
		
		g= new MWright(0.75);
		ret=g.eval(1.0);
		System.out.println (" M(3/4| 1.0)="+ ret+"\t, " +0.6065985435944561);
		
		ret=g.eval(2.5);
		System.out.println (" M(3/4| 2.5)="+ ret+"\t, "+0.02449154055019444);

	}

	@Override
	public String toString() {
		return "M(a,z)";
	}

}
