package ijaux.quad.wright;

import static ijaux.quad.QuadDE.*;
import static java.lang.Math.*;

import ijaux.quad.QFunction;

/*
 * 1 parameter M-Wright function
 * 
 * 

M-Wirght kernels

\frac{1}{2 \pi i}\int_{Ha} exp(\xi -z/\xi^a)*\xi^(a-1) d \xi
 
Algorithms for evaluation of the Wright function for the real arguments' values. 
Fract. Calc. Appl. Anal. 11 : 57-75, 2008
*/
public class IMWright implements QFunction {

	private class KerF implements QFunction {

		double a=0;
		double z=0;

		
		public void setVal(double aa, double  zz) {
			a=aa;
			z=abs(zz);
		}
		
		@Override
		public double eval(double u) {
			if (u<=1e-8) return (0);
			return (exp(-cos(PI*a)*u*z-pow(u,-1.0/a))*sin(sin(PI*a)*u*z))/u;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "(exp(-cos(PI*a)*u*z-1/(u^1/a))*sin(sin(PI*a)*u*z))/u";
		}
		
		@Override
		public double prefactor() {
			return -1.0/(a*PI);
		}
		
	}
	////////////////////////////
	double z=0;
	double a=0;
	double value=0;
	 
	private KerF af=new KerF();
	private double tol=1.0e-15;
	
	
	public IMWright(double aa) {
		a=-aa;
	}
	
	public IMWright(double aa, double ttol) {
		a=-aa;
		tol=ttol;
	}

	@Override
	public double eval(double x) {
		af.setVal(a,x);
		//double ret=-1.0/(a*PI);
		double ret=-1.0/(a*PI);
		if (x>0 ) ret=-ret;
		z=x;
		if (a<=0.5) {
			final double[] vv=intdei(af, 0.0, tol);
			value=vv[0]*ret;
		} else {
			final double[] vv=intdeo(af, 0.0, sin(PI*a)*z, tol);
			value=vv[0]*ret;
		}
		value=0.5+0.5*value;
		return value;
	}

	public static void main(String[] args) {
			
		IMWright g= new IMWright(0.5);
		
		double ret=g.eval(0);
		System.out.println (" I_4="+ ret+"\t, "+0.5);
		 ret=g.eval(0.5);
		System.out.println (" I_4="+ ret+"\t, "+0.6381631950841183);
		
		g= new IMWright(0.33333333333333333);
		ret=g.eval(0.5);
		System.out.println (" I_4="+ ret+"\t, "+0.6615802478133084);
		
		g= new IMWright(0.666666666666666667);
		ret=g.eval(0.5);
		System.out.println (" I_4="+ ret+"\t, "+0.6080696066867238);
		
		g= new IMWright(0.75);
		ret=g.eval(2.5);
		System.out.println (" I_4="+ ret+"\t, ");
	}

	@Override
	public String toString() {
		return "W("+a+",1, x)";
	}

}
