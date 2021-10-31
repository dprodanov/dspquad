package ijaux.quad.poly;


import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.abs;
import static java.lang.Math.atan2;
import static ijaux.quad.QuadDE.intde;
import static java.lang.Math.PI;

import ijaux.quad.QFunction;
 
/**
 * Integral representation of the Legendre polynomials for x>1 using 2nd Laplace integral
 * @author prodanov
 *
 */
public class Legendre  implements QFunction {

	int n=1;
 
	private double value=0;
	
	public static void main(String[] args) {
		Legendre Z= new Legendre(2);
		System.out.println(Z+"="+Z.eval(0.5));
		Z= new Legendre(3);
		System.out.println(Z+"="+Z.eval(0.5));
	}
	
	/*
	 * integral kernel function
	 */
	private class Ker implements QFunction {
		public double val=1.;
		
		public void setVal(double v) {
			val=v;
		}
		
		@Override
		public double eval(double x) {
			double p=1.;
			if (abs(val)>1.0)  {
				final double cc= val + sqrt(val*val-1.) *cos(x);
				for (int i=0; i<n;i++)  {p*=cc;};
			} 
			return  p;
		}

		@Override
		public String toString() {
			return "(x + sqrt(x^2-1) * cos (phi))^n";
		}
	}
	
	/*
	 * integral kernel function
	 */
	private class Ker2 implements QFunction {
		public double val=0.5;
		
		public void setVal(double v) {
			val=v;
		}
		
		@Override
		public double eval(double x) {
			double p=1.;
			if (abs(val)<1.0) {
				final double v2=val*val;
				final double c2=cos(2.*x);
				final double cc= sqrt( (1.+ v2 + (1.-v2) * c2)/2.) ;
				p=cos(n*atan2(cos(x)*sqrt(1.-v2),val));
				for (int i=0; i<n;i++)  {p*=cc;};
				return p;
			}
			return p;
		}

		@Override
		public String toString() {
			return "((-cos(2*phi)*x^2+x^2+cos(2*phi)+1)^(n/2)*cos(n*atan2(cos(phi)*sqrt(1-x^2),x)))/2^(n/2)";
		}
	}
	
	private Ker qf=new Ker();
	private Ker2 qf2=new Ker2();
	private double tol=1.0e-15;
	
	public Legendre(int nn ) {
		n=nn;
	}
	
	@Override
	public String toString() {
		return "L["+n+"](x)";
	}

	@Override
	public double eval(double x) {
		if (n==0) return 1.0; 
		if ((x)==1.) return x;
		if (abs(x)>1.0) {
			qf.setVal(x);
			final double[] ret=intde(qf, 0, PI, tol);
			value=ret[0]/(PI);
		} else {
			qf2.setVal(x);
			final double[] ret=intde(qf2, 0, PI, tol);
			value=ret[0]/(PI);
		}
		return value;
	}

}
