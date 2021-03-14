package ijaux.quad.sir;

import ijaux.quad.QFunction;
import ijaux.quad.lam.LambertO2;
import ijaux.quad.lam.LambertW;

import static java.lang.Math.E;
//import static java.lang.Math.sqrt;
import static java.lang.Math.abs;
import static java.lang.Math.cosh;
import static java.lang.Math.exp;
import static java.lang.Math.log;
import static java.lang.Math.sinh;
import static java.lang.Math.sqrt;

public class IS6 implements QFunction {

	private InvI4 iq_plus=new InvI4();
	private InvI4 iq_minus=new InvI4();
	//private LambertW lw_minus=new LambertW();
	//private LambertW lw_plus=new LambertW();
	private LambertO2 lw_minus=new LambertO2();
	private LambertO2 lw_plus=new LambertO2();
	private int niter=16;
	private double tol=1e-15;
	private double a=1.0;
	private double g=1.0;
	
	public int aiter=0;
	
	public IS6() {}
	
	/*
	 * -w*(W(-%e^(w/g-bb/g-1))+1)*(integrate(1/((z+bb)*(W(-%e^(z/g-1))+1)),z,0,w-bb)+g*x)
	 * 
	 */
	
	public IS6(double gg, double aa) {
		a=aa;
		g=gg;
		//Lambert Omega2 principal branch
		lw_plus.setBranch(0);
		//Lambert Omega2 non principal branch
		lw_minus.setBranch(-1);
		iq_plus.setVal(gg, aa);
		iq_plus.setBranch(0);
		iq_minus.setVal(gg, aa);
		iq_minus.setBranch(-1);
		//System.out.println(" a "+a+", g  "+g);
	}

	@Override
	public double eval(double x) {
		//double bb=g*log(g)-g+a;
		if (x==0) return a;
		if (x>0)
			return rbranch(x, a);
		else {
			return lbranch(x, a); 
		}	 
	}

/*
	private double approx(double x, double g, double a) {
		double bb=g*log(g)-g+a;
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
	
	//private final double c1=Math.cosh(1);
	//private final double s1=Math.sinh(1);
	
	//private final double aq=c1*c1*c1/s1;

	//private LambertW ww=new LambertW();


	/*
	private double approx(double x, double g, double a) {
		double bb=g*log(g)-g+a;
		
		double cc=ww.eval( a*log(a+1))*g/2;
		double d=cosh(exp(-cc*x));
		double w= a*(exp(-cc*x)*sinh(exp(-cc*x)))/ (d*d*d);
		return bb*w;
	}
	*/
	private double approx(double x, double g, double bb) {
		//final double bb=g*log(g)-g+a;
		double cc= g/bb*(1.0- exp(- bb*x)) ;
		return Math.max(bb*exp(cc- g*x),gtol);
	}
	
	private final double gtol=1e-4;
	/**
	 * @param x
	 * @return
	 */
	private double rbranch(double x, double bb) {
		//final double bb=g*log(g)-g+a;
	 
		//final double bb=g*log(g)-g+a;
		double w = approx(x, g, bb ); //approx(x, g, a );
		//System.out.println("w0= "+w);
		int i=0;
		double err=w+2.0;
		double arg=0,da=0,df=0;
		while (i<niter && abs(err-w)>tol ) {
			err=w;
			//arg=-exp((w-a)/g)/g;
			arg=(w-bb)/g-1.0;
			da=-lw_plus.eval(arg);	
			df=iq_plus.eval(w);
			//System.out.println(w+" "+arg+" "+da+" "+df);
			if (Double.isNaN(df) || Double.isNaN(da))  {
				//System.out.println(w+" "+arg+" "+da+" "+df);
				return Double.NaN;
			}
			
			//double qq=(((da+1)*(w+g*(da+1.0))-w)*(g*x-df))/(2.0*(da+1.0)*g);
			/*
			double qq=(da*w+ g*(da+1.0)*(da+1.0) )*(g*x-df)/(2.0*(da+1.0)*g);
			if (qq!=-1.0)
				w+= w*(da+1.0)*(df-g*x)/(qq+1.0);
			else 
			   w+=w*(da+1.0)*(df-g*x);
			   */
			w+=w*(da+1.0)*(df-g*x);
			//if (w<=0) return err;
			i++;
		} 
			aiter=i-1;
		//	System.out.println(" niter " +aiter +" err "+abs(err));
	
			return w;
	}
	
	private double lbranch(double x, double bb) {
	
	//	final double bb=g*log(g)-g+a;
		//System.out.println("bb= "+bb);
		//final double bb=g*log(g)-g+a;
		double w = approx(x, g, bb ); //approx(x, g, a );
		int i=0;
		double err=w+2.0;
		double arg=0,da=0,df=0;
		while (i<niter && abs(err-w)>tol ) {
			err=w;
			//err=da*(df-g* (x));
			//arg=-exp((w-a)/g)/g;
			arg=(w-bb)/g-1.0;
			da=-lw_minus.eval(arg);	
			df=iq_minus.eval(w);
			//System.out.println(w+" "+arg+" "+da+" "+df);
			if (Double.isNaN(df) || Double.isNaN(da))  {
				//System.out.println(w+" "+arg+" "+da+" "+df);
				return Double.NaN;
			}
			/*
			double qq=(da*w+ g*(da+1.0)*(da+1.0) )*(g*x-df)/(2.0*(da+1.0)*g);
			if (qq!=-1.0)
				w+= w*(da+1.0)*(df-g*x)/(qq+1.0);
			else 
			   w+=w*(da+1.0)*(df-g*x);*/
			w+=w*(da+1.0)*(df-g*x);
			//if (w<=0) return err;
			i++;
		} 
		aiter=i-1;
	//	System.out.println(" niter " +aiter +" err "+abs(err));

		return w;
	}

	/**
	 * @param x
	 * @param bb
	 * @return
	 */
	/*
	private double approx(double x, double bb) {
		double w=bb*exp(1.0-exp(-x*log(bb+1))-x*log(bb+1));
		//double w=bb*exp(1.0-exp(-x*log(bb+1)*(bb+1))-x*log(bb+1)*(bb+1));
		if (x<0) w=bb*exp(1.0-exp(-x*bb/log(bb+1))-x*bb/log(bb+1) );
		return w;
	}
*/
	public static void main(String[] args) {
		IS6 lwa=new IS6(1.0, 6.0 );
		double x=0.5;

		x=0.7125359317880108;
		//x=0;
		System.out.println("x= "+x+ " W= " +lwa.eval(x));
		x=0.4264882878827939;
		System.out.println("x= "+x+ " W= " +lwa.eval(x));
		x=-0.7125359317880108;
		//x=0;
		System.out.println("x= "+x+ " W= " +lwa.eval(x));
		//x=-0.4264882878827939;
		x=-0.5;
		System.out.println("x= "+x+ " W= " +lwa.eval(x));
	}

}
