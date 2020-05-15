package ijaux.quad.misc;

import static java.lang.Math.*; 
import ijaux.quad.QFunction;

/* Computes the principal branch of the LAmber W function 
 * Corless, R. M.; Gonnet, G. H.; Hare, D. E. G.; Jeffrey, D. J.; Knuth, D. E. (1996). 
 * "On the Lambert W function". 
 *  Advances in Computational Mathematics. 5: 329–359. doi:10.1007/BF02124750
 *  
 *  Non-principal branch
 *  Rob Johnson archive:   http://www.whim.org/nebula/math/lambertw.html
 */
public class LambertW implements QFunction {
	
	private int niter=16;
	private double tol=1e-15;
	private int branch=0;
	
	public LambertW() {	}
	
	public void setIter(int n) {
		niter=n;
	}

	public LambertW(int bb) {
		if (bb>=0) branch=0; 
		else branch=-1;
		System.out.println("branch "+branch);
	}
	
	@Override
	public double eval(double x) {
		if (x<-exp(-1)) {return Double.NaN;};
		if (branch==0) {
			return principal(x);
		} else if (branch==-1) {
			return nonprincipal2(x);
		}
		return Double.NaN;
	}
	/**
	 * @param x
	 * @return
	 */
	/*
	 * private double nonprincipal(double x) {
	 * //System.out.println("branch eval "+branch +" x= "+x); if (x>=0) {return
	 * Double.NaN;}; double w=-2.0; double err=w+1; int i=0;
	 * //System.out.println("err "+err); while (i<niter && abs(err-w)>tol ) { err=w;
	 * double aa=(E*x+1.0); double bb=(w*exp(w)*E+1.0); if (bb!=0)
	 * w=-1.0+(1.0+w)*sqrt( abs(aa/ bb)); //System.out.println(aa+" "+bb+" " +w+" "
	 * +err); i++; } //System.out.println("niter " +i +" err "+abs(err-w)); return
	 * w;
	 * 
	 * }
	 */
	
	/**
	 * @param x
	 * @return
	 */
	private double nonprincipal2(double x) {
		//System.out.println("branch eval "+branch +" x= "+x);
		if (x>=0) {return Double.NaN;};
		double w=-2.0;
		double err=w+1; 	
		int i=0;
		if (x>=-exp(-2.0)) {
			while (i<niter && abs(err)>tol ) {
				final double ew=exp(w);
				err=(x/ew-w)/(1.0+w);
				w+=(x-w*ew)/(ew*(w+1)- (w+2)*(w*ew-x)/(w+1.0)/2.0   );
				i++;
			} 
			//System.out.println(-exp(-2.0)+" niter " +i +" err "+abs(err)+" "+dw);
			return w;
		} else {
			//System.out.println("err "+err);
			while (i<niter && abs(err-w)>tol ) {
				err=w;
				double aa=(E*x+1.0);
				double bb=(w*exp(w)*E+1.0);
				if (bb!=0)
					w=-1.0+(1.0+w)*sqrt( abs(aa/ bb));			 
				//System.out.println(aa+" "+bb+" " +w+" " +err);
				i++;
			}
			//System.out.println("niter " +i +" err "+abs(err-w));
			return w;
		}
	}
	/**
	 * @param x
	 * @return
	 */
	private double principal(double x) {
		double w=0;
		if (x>exp(2.0)) {
			w=log(x/log(x));
		}
		int i=0;
		double err=1e10;
			while (i<niter && abs(err)>tol ) {
				final double ew=exp(w);
				err=(x/ew-w)/(1.0+w);
				w+=(x-w*ew)/(ew*(w+1)- (w+2)*(w*ew-x)/(w+1.0)/2.0   );
				i++;
			} 
		return w;
	}

	public static void main(String[] args) {
		LambertW lw=new LambertW(-1);
		double x=0;
		x=2.0;
		//System.out.println("x= "+x+ " W= " +lw.eval(x));
		x=-exp(-1.0);
		System.out.println("x= "+x+ " W= " +lw.eval(x));
		x=-exp(-2.0);
		System.out.println("x= "+x+ " W= " +lw.eval(x));
		x=1.0;
		//System.out.println("x= "+x+ " W= " +lw.eval(x));
		lw=new LambertW(0);
		x=2.0;
		//System.out.println("x= "+x+ " W= " +lw.eval(x));
		x=-exp(-1.0);
		System.out.println("x= "+x+ " W= " +lw.eval(x));
		x=-exp(-2.0);
		System.out.println("x= "+x+ " W= " +lw.eval(x));
		x=1.0;
		//System.out.println("x= "+x+ " W= " +lw.eval(x));
	}

}
