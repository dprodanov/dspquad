package ijaux.quad.sir;

import ijaux.quad.QFunction;
import ijaux.quad.lam.LambertO2;
import ijaux.quad.lam.LambertW;

import static java.lang.Math.sqrt;
import static java.lang.Math.abs;
import static java.lang.Math.cosh;
import static java.lang.Math.exp;
import static java.lang.Math.log;
import static java.lang.Math.sinh;
import static java.lang.Math.tanh;
import static java.lang.Math.E;

public class ISR implements QFunction {

	private final InvI3 iq_plus=new InvI3();
	private final InvI3 iq_minus=new InvI3();
	//private LambertW lw_minus=new LambertW();
	//private LambertW lw_plus=new LambertW();
	private final LambertO2 lw_minus=new LambertO2();
	private final LambertO2 lw_plus=new LambertO2();
	//private LambertW lw1=new LambertW(0);
	private int niter=16;
	private double tol=1e-15;
	private double a=1.0;
	private double g=1.0;
	
	public int aiter=0;
	
	public ISR() {}
	
	/*
	 * -w*(W(-%e^(w/g-bb/g-1))+1)*(integrate(1/((z+bb)*(W(-%e^(z/g-1))+1)),z,0,w-bb)+g*x)
	 * 
	 */
	
	public ISR(double gg, double aa) {
		a=aa;
		g=gg;
		lw_plus.setBranch(0);
		lw_minus.setBranch(-1);
		iq_plus.setVal(gg, aa);
		iq_plus.setBranch(0);
		iq_minus.setVal(gg, aa);
		iq_minus.setBranch(-1);
		//System.out.println(" a "+a+", g  "+g);
	}

	@Override
	public double eval(double x) {
		double bb=g*log(g)-g+a;
		if (x==0) return bb;
		if (x>0)
			return rbranch(x);
		else {
			return lbranch(x); 
		}	 
	}

	private final double c1=Math.cosh(1.0);
	private final double t1=Math.tanh(1.0);
	
	private final double aq=c1*c1/t1;

	private LambertW ww=new LambertW();
	
/*
	private double approx(double x, double g, double a) {
		final double bb=g*log(g)-g+a;
		double cc=1.0;
		if (g>=1.0)
			cc=sqrt(2.0*bb*g);
		else
			cc=sqrt(1/E*2.0*bb*g*2.0);
		
		if (x>0) cc=cc/sqrt(E);
	
		double w=bb*exp(1.0-exp(-x*cc)-x*cc);
		return w;
	}
*/
	
 
	private double approx(double x, double g, double a) {
		final double bb=g*log(g)-g+a;
		  double cc=ww.eval( a*log(a+1))*g/E;
			if (x<0) {cc=cc*E;}
//		double cc=1.0;
//		if (g>=1.0)
//			cc=sqrt(2.0*bb*g);
//		else
//			cc=sqrt(1/E*2.0*bb*g*2.0);
//		
//		if (x>0) cc=cc/sqrt(E);
		final double ch=cosh(exp(-cc*x));
		final double th=tanh(exp(-cc*x));
		final double w= aq*exp(-cc*x)*th/(ch*ch);
		return bb*w;
	}
 
	/*
	private double approx2(double x, double g, double a) {
		final double bb=g*log(g)-g+a;
		double cc=g*bb/2.0;
		double w=bb*exp(-cc*x*x);
		return w;
	}
	*/

	/**
	 * @param x
	 * @return
	 */
	private double rbranch(double x) {
		//final double bb=g*log(g)-g+a;
	 
		double w = approx(x, g, a);
		//System.out.println("w0= "+w);
		int i=0;
		double err=w+2;
		double arg=0,da=0,df=0;
		while (i<niter && abs(err-w)>tol ) {
			err=w;

			arg=(w-a)/g-log(g);
			da=-lw_plus.eval(arg);	
			df=iq_plus.eval(w);
			//System.out.println(w+" "+arg+" "+da+" "+df);
			if (Double.isNaN(df) || Double.isNaN(da))  {
				System.out.println("df "+df+ " da "+da+" w "+w);
				return Double.NaN;
			}
			 
			w+=w*(da+1.0)*(df-g*x);
			if (w<=0) return err;
	
			i++;
		} 
			aiter=i-1;
		//	System.out.println(" niter " +aiter +" err "+abs(err));
	
			return w;
	}
	
	private double lbranch(double x) {
	
		//final double bb=g*log(g)-g+a;
		//System.out.println("bb= "+bb);
	
		double w = approx(x, g, a);
		int i=0;
		double err=w+2;
		double arg=0,da=0,df=0;
		while (i<niter && abs(err-w)>tol ) {
			err=w;

			arg=(w-a)/g-log(g);
			da=-lw_minus.eval(arg);	
			df=iq_minus.eval(w);
			//System.out.println(w+" "+arg+" "+da+" "+df);
			if (Double.isNaN(df) || Double.isNaN(da))  {
				System.out.println("df "+df+ " da "+da+" w "+w);
				return Double.NaN;
			}
			w+=w*(da+1.0)*(df-g*x);
			if (w<=0) return err;
			i++;
		} 
		aiter=i-1;
	//	System.out.println(" niter " +aiter +" err "+abs(err));

		return w;
	}


	public static void main(String[] args) {
		ISR lwa=new ISR(1.0, 4.0 );
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
