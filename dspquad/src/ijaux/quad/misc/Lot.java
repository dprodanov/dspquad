/**
 * 
 */
package ijaux.quad.misc;
import static ijaux.quad.QuadDE.*;
import static java.lang.Math.*;

import ijaux.quad.QFunction;
 

/**
 * @author prodanov
 *   
 
 */
public class Lot implements QFunction {

	private Ker qf=new Ker();
	private double tol=1.0e-15;
	
	public Lot( ) {
	}
	
	/////////////////////////
	//  Integral Kernels
	////////////////////////

 
	/*
	 * Ker= (y*%e^(2*sqrt(y)*z))/(y+1)^2
	 */
	private class Ker implements QFunction {

		double z=0;	

		public void setVal(double zz) {
			z=zz;
		}

		@Override
		public double eval(double y) {
			double y2=(y+1);
			y2*=y2;
			return (y*exp(-2*sqrt(y)*z))/y2;
		}

		@Override
		public double prefactor() {
			return 0.5;
		}

		@Override
		public String toString() {
			return "(y*%e^(-2*sqrt(y)*z))/(y+1)^2";		
		}

	}



////////////////////////////
	

	/* (non-Javadoc)
	 * @see ijaux.quad.QFunction#eval(double)
	 */
	@Override
	public double eval(double x) {
		qf.setVal(x);
		final double fr=qf.prefactor();
		final double[] vv=intde(qf, 0.0, 1.0, tol);
		return fr*vv[0];
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("case 1/4");
		Lot ml= new Lot( );
		System.out.println (" L(  0)= " +ml.eval(0.0) +" " + (2*log(2)-1)/4 );
		
	}

}
