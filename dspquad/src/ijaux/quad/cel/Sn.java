package ijaux.quad.cel;

import ijaux.quad.QFunction;
import static java.lang.Math.sqrt;

/**
 * 
 * @author prodanov
 *
 */
public class Sn implements QFunction {


	double m=0;
	double rm=0;
	double tol=1e-16;
	
	/**
	 * 
	 * @param param
	 */
	public Sn (double param) {
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
			return ret[0];
		} else {
			rm=sqrt(1./m); // sn -> sn
			final double[] ret=EllipticFunctions.ellipj(x*rm,  1./m,  tol);
			return ret[0]*rm;
		}
	}

	@Override
	public String toString() {
		return "sn(x | m)";
	}
}
