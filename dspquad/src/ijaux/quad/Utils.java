package ijaux.quad;

import static java.lang.Math.abs;

public class Utils {
	
	/*
	 * Euler's Gamma constant
	 */
	public static final double  GA=0.5772156649015332;
	
	public static double[] linspace(double a, double b, int n) {
		final double[] ret = new double[n];
		final double step =  (b-a)/(n-1);
		for(int i=0;i<n;i++){
			ret[i] = i*step +a;
		}
		return ret;
	}
	
	public static double sgn(double x) {
		if (x<0) return -1;
		else return 1;
	}
	
	public static int sgn(int x) {
		if (x<0) return -1;
		else return 1;
	}
	
	public static int sgn(boolean x) {
		if (!x) return -1;
		else return 1;
	}

	/** calculates the value of a polynomial assuming a_0 + \sum a_i x^i
	 * @param coef
	 * @param x
	 * @return
	 */
	public static double polyval2(double[] coef, double x) {			
		final int n=coef.length-1;
		double ret=coef[n];
		for (int i=1; i<=n; i++) {			
			double z=ret*x + coef[n-i];
			ret= z;			
		}
		return ret;
	}
	
	/**
	 * Approximate equality
	 * @param a
	 * @param b
	 * @param eps - allowed error bond
	 * @return
	 */
	public static boolean apperoxeq(double a, double b, double eps) {
		if (abs(a-b)<=eps) return true;
		return false;
	}
	
	
	/**
	 * cumulative sum
	 * @param arr
	 * @return
	 */
	public static double[] cumsum(final double[] arr) {
		final double[] ret=new double[arr.length];
		double ss=0;
		for (int i=0; i< arr.length; i++) {
			ss+=arr[i];
			ret[i]=ss;
		}
		return ret;
	}
}
