package ijaux.quad.euler;

import static ijaux.quad.QuadDE.*;
import static java.lang.Math.*;

import ijaux.quad.En;
import ijaux.quad.QFunction;

public class RGamma implements QFunction {

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
			if (val<=0 || !Double.isFinite(x)) return (0.0);
			final double xq=pow(x, val);
			//System.out.println("x "+x+" xq "+xq+" "+Double.isFinite(xq));
			if (! Double.isFinite(xq))  
				return 0.0;
			return  npol.eval(-x) /xq ;
		}

		@Override
		public String toString() {
			return "E_n(-x)/x^z";
		}
		
		@Override
		public double prefactor() {
			return 1/PI;
		}
	}
	
	private En npol=null;
	////////////////////////////
	
	private double value=0;
	
	private Ker qf=new Ker();
 
	private double tol=1.0e-15;
	private Gamma gam=new Gamma();
	
	public RGamma() {
	}
	
	public RGamma(double q) {
		tol=q;
		gam=new Gamma(tol);
		qf=new Ker();
	}
	
	@Override
	public double eval(double x) {
		if (x==0) {
			value=0;
			return 0;
		}
		if (x>1) {
			value=1/(x-1)*eval(x-1);	 
		} else {
			if ( x==1) {
				//System.out.println("integer case ");
				value=1.0;
				return 1.0;
			}
			final double fr= sin(PI*x)/PI; 
			if (x>0) {
				int n= (int) abs(x);
				//System.out.println(n);
				npol=new En(n);
				qf.setVal(x);
				final double[] ret=intdei(qf, 0.0, tol);
				//System.out.println(" "+ret[0]);
				value=fr*ret[0];
			} else {
				// reflection formula
				value=fr*gam.eval(-x+1);
			}
			
		}
		return value;
	}
	
	public static void main(String[] args) {
		
		 RGamma rgam=new RGamma();
		 
		 double y1=rgam.eval(1.5);
		 final double g15=1.128379167094015;
		 System.out.println(y1 +" "+ g15);
		 
		 Gamma gam=new Gamma();
		 double y2=gam.eval(1.5);
		 System.out.println("ref "+1.0/y2 +"  " +y1*y2+ " " + y2*g15);
		 
		  y1=rgam.eval(0.90);
		  System.out.println("0.90 "+y1 +" ");
		  y1=rgam.eval(0.96);
		  System.out.println("0.96 "+y1 +" ");
		  
		  y1=rgam.eval(3.0);
		  System.out.println("3.0 "+y1 +" ");

	}

	@Override
	public String toString() {
		return "1/Gamma(z)";
	}

}
