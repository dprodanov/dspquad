package ijaux.quad.poly;

import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.acos;
import static ijaux.quad.QuadDE.intde;
import static java.lang.Math.abs;
import static java.lang.Math.PI;

import ijaux.quad.QFunction;
 

public class Zernike  implements QFunction {

	int n=1;
	int m=2;
	private double value=0;
	
	public static void main(String[] args) {
		Zernike Z= new Zernike(2,2);
		System.out.println(Z+"="+Z.eval(0.5));
		Z= new Zernike(3,1);
		System.out.println(Z+"="+Z.eval(0.5));
	}
	
	/*
	 * integral kernel function
	 */
	private class Ker implements QFunction {
		public double val=0.5;
		
		public void setVal(double v) {
			val=v;
		}
		
		@Override
		public double eval(double x) {
			final double cc= acos(val*cos(x));
			final double ss= cos(m*x)*sin((n+1)*cc);
			return  ss/sin(cc);
		}

		@Override
		public String toString() {
			return "cos (m * phi) * sin ( (n+1) * acos (r * cos( phi)) / sin (acos (r * cos( phi)) ";
		}
	}
	
	private Ker qf=new Ker();
	private double tol=1.0e-15;
	
	public Zernike(int nn, int mm) {
		n=nn;
		m=mm;
	}
	
	@Override
	public String toString() {
		return "Z["+m+","+n+"](r)";
	}

	@Override
	public double eval(double x) {
		if (x==1.0) return 1.0;
		qf.setVal(x);
		final double[] ret=intde(qf, 0.0, 2*PI, tol);
		value=ret[0]/(2*PI);
		return value;
	}

}
