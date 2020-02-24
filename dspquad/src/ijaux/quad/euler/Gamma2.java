package ijaux.quad.euler;

import ijaux.quad.QFunction;

public class Gamma2 implements QFunction {
 
	
	@Override
	public double eval(double x) {
		 int k, n;
		    double w, y;

		    n = x < 1.5 ? -((int) (2.5 - x)) : (int) (x - 1.5);
		    w = x - (n + 2);
		    y = ((((((((((((-1.99542863674e-7 * w + 1.337767384067e-6) * w - 
		        2.591225267689e-6) * w - 1.7545539395205e-5) * w + 
		        1.45596568617526e-4) * w - 3.60837876648255e-4) * w - 
		        8.04329819255744e-4) * w + 0.008023273027855346) * w - 
		        0.017645244547851414) * w - 0.024552490005641278) * w + 
		        0.19109110138763841) * w - 0.233093736421782878) * w - 
		        0.422784335098466784) * w + 0.99999999999999999;
		    if (n > 0) {
		        w = x - 1;
		        for (k = 2; k <= n; k++) {
		            w *= x - k;
		        }
		    } else {
		        w = 1;
		        for (k = 0; k > n; k--) {
		            y *= x - k;
		        }
		    }
		    return w / y;
	}

	@Override
	public String toString() {
		return "Gamma(z)";
	}
	

}
