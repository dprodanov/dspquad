package ijaux.quad.lam;

import static java.lang.Math.*; 
import ijaux.quad.QFunction;

/* Computes the principal branch of the Lamber Omega function 
 *
 */
public class LambertO implements QFunction {
	
	private int niter=16;
	private double tol=1e-15;
	 
	
	public LambertO() {	}
	
	public void setIter(int n) {
		niter=n;
	}

	 
	@Override
	public double eval(double x) {
		double w=1.0;
		if (x>(2.0)) {
			w= (x -log(x));
		}
		int i=0;
		double err=1e10;
		// Halley method
			while (i<niter && abs(err)>tol ) {
				final double lw=log(w);
				err=(w*x-w*lw-w*w)/(w+1.0);
				w+=(x-lw-w)/((x-lw+1.0)/(2.0*(w+1))-(x-lw-2.0)/(2.0*w)+1);
				i++;
			} 
		return w;
	
		
	}


	/**
	 * @param x
	 * @return
	 */
 

	public static void main(String[] args) {
		LambertO lw=new LambertO( );
		double x=0;
 
	 
		x=2.0;
		System.out.println("x= "+x+ " W= " +lw.eval(x));
		x=-exp(-1.0);
		System.out.println("x= "+x+ " W= " +lw.eval(x));
		x=0;
		System.out.println("x= "+x+ " W= " +lw.eval(x));
		x=1.0;
		System.out.println("x= "+x+ " W= " +lw.eval(x));
		x=10.0;
		System.out.println("x= "+x+ " W= " +lw.eval(x));
	}

}
