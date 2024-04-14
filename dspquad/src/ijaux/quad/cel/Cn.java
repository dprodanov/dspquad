package ijaux.quad.cel;

import ijaux.quad.QFunction;
import static java.lang.Math.sqrt;

/**
 * 
 * @author prodanov
 *
 */
public class Cn implements QFunction {


	double m=0;
	double rm=0;
	double tol=1e-16;
	
	/**
	 * 
	 * @param param
	 */
	public Cn (double param) {
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
			return ret[1];
		} else {
			rm=sqrt(1./m); // cn -> dn
			final double[] ret=EllipticFunctions.ellipj(x*rm,  1./m,  tol);
			return ret[2];
		}
	}
	
	@Override
	public String toString() {
		return "cn(x | m)";
	}

}
