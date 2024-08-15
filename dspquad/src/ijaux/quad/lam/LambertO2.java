package ijaux.quad.lam;

import static java.lang.Math.*; 
import ijaux.quad.QFunction;

/* Computes the principal branch of the Lambert Omega function 
 *
 */
public class LambertO2 implements QFunction {
	
	private int niter=16;
	private double tol=5e-16;
	
	public int aiter=0;
	
	private int branch=0;
	private final double eps=1e-32;
	
	public LambertO2() {	}

	
	public void setIter(int n) {
		niter=n;
	}

	public void setBranch(int bb) {
		if (bb>=0) branch=0; 
		else branch=-1;
		//System.out.println("branch "+branch);
	}
	 
	@Override
	public double eval(double x) {
		if (x>-1.0) return (Double.NaN);
		if (x==-1) return(1.0);
		if (branch==0)
			return principalH(x);
		else 
			return nonprincipalH(x);
	}
	
	

	/**
	 * @param x
	 * @return
	 
	private double principalN(double x) {
		double w=exp(x);
		if (abs(w)<eps) return eps;
		//System.out.println("w0="+w);
		int i=0;
		double err=2;
		double lw=0;
		// Newton method
		while (i<niter && abs(err)>tol ) {
			lw=log(abs(w));
			if (!Double.isFinite(lw))
				lw=1/tol;
			w+=w*(x-lw+w)/(1.0-w);
			err=lw-w-x;
			i++;
		} 
		aiter=i-1;
		return  (w);
 
	}
	*/
	
	private double principalH(double x) {
		double w=exp(x);
		if (abs(w)<eps) return w;
		//System.out.println("w0="+w);
		int i=0;
		double err=2.0;
		double lw=0.0;
		// Halley-Newton method
		while (i<niter && abs(err)>tol ) {
			lw=log(abs(w));
			if (!Double.isFinite(lw))
				lw=1/tol;
			final double dw=(x+w-lw);
			final double q=-dw/(2.0*w) + (1.0-w)*(1.0-w)/w;
			if (q!=0)
				w+=dw*(1.0-w)/q;
			else
				w+=w*dw/(1.0-w);
			err=lw-w-x;
			i++;
		} 
		aiter=i;
		return w;
 
	}
	
	/**
	 * @param x
	 * @return
	*/
	private double nonprincipalH(double x) {
		//double w=(-x/(1.0 +exp(x))+1.0); 
		double w=(-x +log(-x)); 
		//System.out.println("w0="+w);
		int i=0;
		double err=abs(-x+1.0);
		double lw=0;
		// Halley-Newton method
		while (i<niter && abs(err)>tol ) {
			lw=log(abs(w));
			if (!Double.isFinite(lw))
				lw=1/tol;
			final double dw=(x+w-lw);
			final double q=-dw/(2.0*w)+(1.0-w)*(1.0-w)/w;
			if (q!=0)
				w+=dw*(1.0-w)/q;
			else
				w+=w*dw/(1.0-w);
			err=lw-w-x;
			i++;
		} 
		aiter=i;
		return w;

	}
	
	/*
	private double nonprincipalN(double x) {
		double w=(-x/(1 +exp(x))+1.0 ); 
		//double w=-2;
		//System.out.println("w0="+w);
		int i=0;
		double err=abs(x+1);
		double lw=0;
		// Newton method
	 
		while (i<niter && abs(err)>tol ) {
			lw=log(abs(w));
			if (!Double.isFinite(lw))
				lw=1/tol;
			w+=w*(x-lw+w)/(1.0-w);
			err=lw-w-x;
			i++;
		} 
		aiter=i-1;
		return  (w);

	}
*/

	/**
	 * @param x
	 * @return
	 */
 

	public static void main(String[] args) {
		LambertO2 lw=new LambertO2( );
		double x=0;
		System.out.println("principal branch");
		x=0;
		System.out.println("x= "+x+ " W= " +lw.eval(x)+ " n="+lw.aiter);
		x=-1.0;
		System.out.println("x= "+x+ " W= " +lw.eval(x)+ " n="+lw.aiter);
		x=-1.0005;
		System.out.println("x= "+x+ " W= " +lw.eval(x)+ " n="+lw.aiter);
		x=-2.0;
		System.out.println("x= "+x+ " W= " +lw.eval(x)+ " n="+lw.aiter);
		x=-10.0;
		System.out.println("x= "+x+ " W= " +lw.eval(x)+ " n="+lw.aiter);
		
		x=-750.0;
		System.out.println("x= "+x+ " W= " +lw.eval(x)+ " n="+lw.aiter);
		System.out.println("non principal branch");
		lw.setBranch(-1);
		
		x=-1.0;
		System.out.println("x= "+x+ " W= " +lw.eval(x)+ " n="+lw.aiter);
		x=-1.05;
		System.out.println("x= "+x+ " W= " +lw.eval(x)+ " n="+lw.aiter);
		x=-10.0;
		System.out.println("x= "+x+ " W= " +lw.eval(x)+ " n="+lw.aiter);
		
	}

}
