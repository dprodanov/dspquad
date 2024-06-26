package ijaux.quad.cel;

import static java.lang.Math.sqrt;

import ijaux.quad.QFunction;

/**
 * 
 * @author prodanov
 *
 */
public class Dn implements QFunction {


	double m=0;
	double tol=1e-16;
	
	/**
	 * 
	 * @param param
	 */
	public Dn (double param) {
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
		if (m<=1.0) {
			final double[] ret=EllipticFunctions.ellipj(x,  m,  tol);
			return ret[2];
		} else {
			final double rm=sqrt(m); //dn -> cn
			final double[] ret=EllipticFunctions.ellipj(x*rm,  1./m,  tol);
			return ret[1];
		}
	}
	
	@Override
	public String toString() {
		return "dn(x | m)";
	}

	public static void main(String[] args) {
        double u = 1.;
        double m = 1.5;
     
        Dn am=new  Dn (m);
        
        System.out.println(am.eval(u));
    }
}
