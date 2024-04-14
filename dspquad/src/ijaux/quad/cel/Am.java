package ijaux.quad.cel;

import static java.lang.Math.sqrt;
import static java.lang.Math.asin;

import ijaux.quad.QFunction;

public class Am implements QFunction {


	double m=0;
	double rm=0;
	double tol=1e-16;
	
	public Am (double param) {
		if (Math.abs(param)<0) throw new IllegalArgumentException(param +" - should be positive");
		m=param;
	}
	
	public void setParams(double p) {
		m=p;
	}
	
	@Override
	public double eval(double x) {
		if (m<=1.) {
			double[] ret=EllipticFunctions.ellipj(x,  m,  tol);
			return ret[3];
		} else {
			rm=sqrt(1./m);
			double[] ret=EllipticFunctions.ellipj(x*rm,  1./m,  tol);
			return asin(ret[0]*rm);
		}
	}
	
	@Override
	public String toString() {
		return "am(x | m)";
	}

}
