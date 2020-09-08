package ijaux.quad.sir;

import ijaux.quad.QFunction;
import ijaux.quad.lam.LambertW;

import static java.lang.Math.sqrt;
import static java.lang.Math.abs;
import static java.lang.Math.exp;
import static java.lang.Math.log;

public class SIR implements QFunction {

	private ISR is2;
	 
		private LambertW lw_plus=new LambertW();
		private LambertW lw_minus=new LambertW();
	//private int niter=16;
	//private double tol=1e-15;
	private double a=1.0;
	private double g=1.0;
	
	public int aiter=0;
	
	public SIR() {}
	
	/*
	 *  SIR model, Newton iteration
	 *  
	 * s=-g*W(-%e^(i/g-%c/g)/g)
	 * 
	 */
	
	public SIR(double gg, double aa) {
		a=aa;
		g=gg;
		lw_plus.setBranch(0);
		lw_minus.setBranch(-1);
		is2=new ISR(gg,aa);
	}

	@Override
	public double eval(double x) {
		double val=is2.eval(x);
		double ret=0;
		if (x<0) {
			ret=g*lw_minus.eval( -exp((val -a)/g)/g );
		} else {
			ret=g*lw_plus.eval( -exp((val -a)/g)/g ) ;
		}
		return -ret;
	}

	/*
	@Override
	public double prefactor() {
		return -1.0;
	}
	 */
	public static void main(String[] args) {
		SIR lwa=new SIR(1.0, 4.0 );
		double x=0.5;

		x=0.7125359317880108;
		//x=0;
		System.out.println("x= "+x+ " W= " +lwa.eval(x));
		x=0.4264882878827939;
		System.out.println("x= "+x+ " W= " +lwa.eval(x));
		x=-0.7125359317880108;
		//x=0;
		System.out.println("x= "+x+ " W= " +lwa.eval(x));
		x=-0.4264882878827939;
		System.out.println("x= "+x+ " W= " +lwa.eval(x));
	}

}
