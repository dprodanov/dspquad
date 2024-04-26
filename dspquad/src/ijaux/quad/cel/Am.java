package ijaux.quad.cel;

import static java.lang.Math.sqrt;

import ijaux.quad.QFunction;

/**
 * 
 * @author prodanov
 *
 */
public class Am implements QFunction {


	double m=0;
	//double rm=0;
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
			// Greenhiil, The Applications of Elliptic Functions, 1892
			// https://archive.org/details/applicationsofel00greeuoft/page/24/mode/2up
			final double rm=sqrt(m);
			final double[] ret=EllipticFunctions.ellipj(x*rm,  1./m,  tol);
			return ret[3];
		}
	}
	
	@Override
	public String toString() {
		return "am(x | m)";
	}

	public static void main(String[] args) {
        double u = 1;
        double m = 1.5;
       // double tol = 1e-16;
     
        Am am=new  Am (m);
        
        System.out.println(am.eval(u));
    }
}
