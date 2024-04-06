package ijaux.quad.cel;

import ijaux.quad.QFunction;

public class Cn implements QFunction {


	double m=0;
	double tol=1e-16;
	
	public Cn (double param) {
		if (Math.abs(param)>1.) throw new IllegalArgumentException(param +" - not implemented");
		m=param;
	}
	
	public void setParams(double p) {
		m=p;
	}
	
	@Override
	public double eval(double x) {
		double[] ret=EllipticFunctions.ellipj(x,  m,  tol);
		return ret[1];
	}
	
	@Override
	public String toString() {
		return "cn(x | m)";
	}

}
