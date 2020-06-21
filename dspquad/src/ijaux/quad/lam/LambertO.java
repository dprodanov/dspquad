package ijaux.quad.lam;

import static java.lang.Math.*; 
import ijaux.quad.QFunction;

/* Computes the principal branch of the Lamber Omega function 
 *
 */
public class LambertO implements QFunction {
	
	private int niter=16;
	private double tol=5e-16;
	
	public int aiter=0;
	
	public LambertO() {	}
	
	public void setIter(int n) {
		niter=n;
	}

	 
	@Override
	public double eval(double x) {
		double w=1.0;
		if (x<=1) w=exp(x); else w=x-log(x);
		//if (w==0) w=tol;
		int i=0;
		double err=1e10;
		// Halley method
		while (i<niter && abs(err)>tol ) {
			final double lw=log((w));
			err=w*(x-lw-w)/(w+1.0);
			final double dd=((x-lw+1.0)/(2.0*(w+1))-(x-lw-2.0)/(2.0*w)+1);
			if (dd!=0)
				w+=(x-lw-w)/dd; 
			i++;
		} 
		aiter=i-1;
		return abs(w);
	}


	/**
	 * @param x
	 * @return
	 */
 

	public static void main(String[] args) {
		LambertO lw=new LambertO( );
		double x=0;
 
	 
		x=-20.0;
		System.out.println("x= "+x+ " W= " +lw.eval(x) + " n="+lw.aiter);
		x=-2;
		System.out.println("x= "+x+ " W= " +lw.eval(x)+ " n="+lw.aiter);
		x=0;
		System.out.println("x= "+x+ " W= " +lw.eval(x)+ " n="+lw.aiter);
		x=1.0;
		System.out.println("x= "+x+ " W= " +lw.eval(x)+ " n="+lw.aiter);
		x=10.0;
		System.out.println("x= "+x+ " W= " +lw.eval(x)+ " n="+lw.aiter);
		
		x=1000.0;
		System.out.println("x= "+x+ " W= " +lw.eval(x)+ " n="+lw.aiter);
	}

}
