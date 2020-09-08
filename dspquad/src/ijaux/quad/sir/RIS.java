package ijaux.quad.sir;

import ijaux.quad.QFunction;
import ijaux.quad.lam.LambertW;

import static java.lang.Math.sqrt;
import static java.lang.Math.abs;
import static java.lang.Math.exp;
import static java.lang.Math.log;

public class RIS implements QFunction {
 
	private LambertW lw_plus=new LambertW();
	private LambertW lw_minus=new LambertW();
	private ISR is5;
	//private int niter=16;
	//private double tol=1e-15;
	private double a=1.0;
	private double g=1.0;
	
	private double offset=0.0;
	
	public RIS() {}
	
	/*
	 *  SIR model, Newton iteration
	 *  
	 * r=g*W(-%e^(i/g-L/g)/g) - i
	 * 
	 */
	
	public RIS(double gg, double aa) {
		a=aa;
		g=gg;
		lw_plus.setBranch(0);
		lw_minus.setBranch(-1);
		is5=new ISR(gg,aa);
		offset=-g*lw_minus.eval( -exp((-a)/g)/g );
	}

	@Override
	public double eval(double x) {
		double val=is5.eval(x);
		double ret=offset;
		if (x<0) {
			ret+=g*lw_minus.eval( -exp((val -a)/g)/g ) -val;
			//ret=g*log(-g*lw_minus.eval( -exp((val -a)/g)/g ));
		} else {
			ret+=g*lw_plus.eval( -exp((val -a)/g)/g ) -val ;
			//ret=g*log(-g*lw_plus.eval( -exp((val -a)/g)/g ));
		}
		return ret;
	}

 

	public static void main(String[] args) {
		RIS lwa=new RIS(1.0, 4.0 );
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
