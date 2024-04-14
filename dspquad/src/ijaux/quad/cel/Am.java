package ijaux.quad.cel;

import static java.lang.Math.sqrt;
import static java.lang.Math.asin;

import ijaux.quad.QFunction;

/**
 * 
 * @author prodanov
 *
 */
public class Am implements QFunction {


	double m=0;
	double rm=0;
	double tol=1e-16;
	
	/**
	 * 
	 * @param param
	 */
	public Am (double param) {
		if (Math.abs(param)<0) throw new IllegalArgumentException(param +" - should be positive");
		m=param;
	}
	
	/**
	 * 
	 * @param p
	 */
	public void setParams(double p) {
		m=p;
	}
	
	@Override
	public double eval(double x) {
		if (m<=1.) {
			final double[] ret=EllipticFunctions.ellipj(x,  m,  tol);
			return ret[3];
		} else {
			// http://arkadiusz-jadczyk.eu/blog/2017/01/case-inverted-modulus-treading-tigers-tail/
			rm=sqrt(1./m);
			final double[] ret=EllipticFunctions.ellipj(x*rm,  1./m,  tol);
			return (ret[3]);
		}
	}
	
	@Override
	public String toString() {
		return "am(x | m)";
	}

}
