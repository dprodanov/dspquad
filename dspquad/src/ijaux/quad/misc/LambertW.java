package ijaux.quad.misc;

import ijaux.quad.QFunction;

/* Computes the principal branch of the LAmber W function 
 * Corless, R. M.; Gonnet, G. H.; Hare, D. E. G.; Jeffrey, D. J.; Knuth, D. E. (1996). 
 * "On the Lambert W function". 
 *  Advances in Computational Mathematics. 5: 329–359. doi:10.1007/BF02124750
 */
public class LambertW implements QFunction {
	
	private int niter=18;

	public LambertW() {
	}
	
	public LambertW(int n) {
		niter=n;
	}

	@Override
	public double eval(double x) {
		if (x<-Math.exp(-1.0)) {return Double.NaN;};
		double w=0;
		for (int i=0; i<niter;i++) {
			final double ew=Math.exp(w);
			w+=(x-w*ew)/(ew*(w+1)- (w+2)*(w*ew-x)/(w+1)/2.0   );
		}
		return w;
	}

	public static void main(String[] args) {
		LambertW lw=new LambertW();
		System.out.println(lw.eval(2.0));
		System.out.println(lw.eval(-Math.exp(-1)));
	}

}
