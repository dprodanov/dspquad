package ijaux.quad.sir;

import ijaux.quad.QFunction;
import ijaux.quad.lam.LambertW;

//import static java.lang.Math.sqrt;
import static java.lang.Math.abs;
import static java.lang.Math.exp;
import static java.lang.Math.log;

public class IS1 implements QFunction {

	private InvI iq_plus=new InvI();
	private InvI iq_minus=new InvI();
	private LambertW lw_minus=new LambertW();
	private LambertW lw_plus=new LambertW();
	private int niter=16;
	private double tol=1e-15;
	private double a=1.0;
	private double g=1.0;
	
	public int aiter=0;
	
	public IS1() {}
	
	/*
	 * -w*(W(-%e^(w/g-bb/g-1))+1)*(integrate(1/((z+bb)*(W(-%e^(z/g-1))+1)),z,0,w-bb)+g*x)
	 * 
	 */
	
	public IS1(double gg, double aa) {
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
/*
	private double approx(double x, double bb) {
	//	double cc=lw_plus.eval( a*log(a+1))*g;		
		double cc=lw_plus.eval( bb*exp(bb/g)/g)*g;
		double w=bb*exp(1.0-exp(-x*cc)-x*cc);
		return w;
	}
	*/
	private double approx(double x, double bb) {
		//double cc=lw_plus.eval( a*log(a+1))*g;		
		//double cc=lw_plus.eval( a*exp(-a/g))*g;
		//double cc=lw_plus.eval( (bb+1)*exp(bb/g+1)/g)/g;
		double cc=(g+bb)/g;
		//if (cc<1.5) cc=1.5;
		//double w=bb*exp(1.0-exp(-x*cc)-x*cc);
		double w=bb*exp(1.0-exp(-x*cc)-x*cc - cc*x*x*x/48.0  );
		return w;
	}
	/**
	 * @param x
	 * @return
	 */
	private double rbranch(double x) {
		final double bb=g*log(g)-g+a;
	 
		double w = approx(x, bb);
		//System.out.println("w0= "+w);
		int i=0;
		double err=w+2;
		double arg=0,da=0,df=0;
		while (i<niter && abs(err-w)>tol ) {
			err=w;
			//err=w*da*(df-g* (x));
			arg=-exp((w-a)/g)/g;
			da=(lw_plus.eval(arg));	
			df=iq_plus.eval(w);
			//System.out.println(w+" "+arg+" "+da+" "+df);
			if (Double.isNaN(df) || Double.isNaN(da))  return Double.NaN;
			double qq=(((da+1)*(w+da*g+g)-w)*(g*x-df))/(2*(da+1)*g);
			w+=(da+1.0)*(df-g* (x))/(qq+1);
			 
			//w=abs(w);
			i++;
		} 
			aiter=i-1;
		//	System.out.println(" niter " +aiter +" err "+abs(err));
	
			return w;
	}
	
	/*
	((g*x\-df)*(\-1/(w*(g*x\-df))\-(w+da*g+g)/(2*g*w)+1/(2*(da+1)*g)))/(da+1)
	 */
	private double lbranch(double x) {
	
		final double bb=g*log(g)-g+a;
		//System.out.println("bb= "+bb);
	
		double w = approx(x, bb);
		int i=0;
		double err=w+2;
		double arg=0,da=0,df=0;
		while (i<niter && abs(err-w)>tol ) {
			err=w;
			//err=w*da*(df-g* (x));
			arg=-exp((w-a)/g)/g;
			da=(lw_minus.eval(arg));	
			df=iq_minus.eval(w);
			//System.out.println(w+" "+arg+" "+da+" "+df);
			if (Double.isNaN(df) || Double.isNaN(da))  return Double.NaN;
			
			double qq=(((da+1)*(w+da*g+g)-w)*(g*x-df))/(2*(da+1)*g);
			w+=(da+1.0)*(df-g* (x))/(qq+1);
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
		IS1 lwa=new IS1(1.0, 4.0 );
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
