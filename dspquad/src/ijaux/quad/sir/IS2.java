package ijaux.quad.sir;

import ijaux.quad.QFunction;
import ijaux.quad.lam.LambertW;

import static java.lang.Math.sqrt;
import static java.lang.Math.E;
import static java.lang.Math.abs;
import static java.lang.Math.exp;
import static java.lang.Math.log;

public class IS2 implements QFunction {

	private InvI2 iq_plus=new InvI2();
	private InvI2 iq_minus=new InvI2();
	private LambertW lw_minus=new LambertW();
	private LambertW lw_plus=new LambertW();
	private int niter=16;
	private double tol=1e-15;
	private double a=1.0;
	private double g=1.0;
	
	public int aiter=0;
	
	public IS2() {}
	
	/*
	 *  SIR model, Newton iteration
	 *  
	 * -g*i*(W(-%e^(i/g-L/g-1)/g)+1)*
	 * (integrate(1/(y*(g*log(y)-y+g+L)),y,-W(-%e^(-1)/g),-g*W(-%e^(i/g-L/g-1)/g))-x)
	 * 
	 */
	
	public IS2(double gg, double aa) {
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
	
	
	public void setVal(double gg, double aa) {
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
			return rbranch(x,bb);
		else {
			return lbranch(x,bb); 
		}
 
	 
	}

	/**
	 * @param x
	 * @return
	 */
	private double rbranch(double x, double bb) {
		
		double w = approx(x,a);
		//System.out.println("w0= "+w);
		int i=0;
		double err=w+2;
		double arg=0,da=0,df=0;
		
		while (i<niter && abs(err-w)>tol ) {
			err=w;
			arg=-exp((w-a)/g)/g;
			da=(lw_plus.eval(arg)+1.0);	
			df=iq_plus.eval(w);
			//System.out.println(w+" "+arg+" "+da+" "+df);
			if (Double.isNaN(df) || Double.isNaN(da))  {
				System.out.println("lbranch NaN at "+x +" i "+i);
				return Double.NaN;
			}
			//if (Double.isNaN(df) || Double.isNaN(da))  return w;
			w+=(w)*da*(df-g*(x));
			//w=abs(w);
			i++;
		} 
			aiter=i-1;
		//	System.out.println(" niter " +aiter +" err "+abs(err));
	
			return w;
	}
	
	private double lbranch(double x, double bb) {
	
		double w = approx(x,a);
		//System.out.println("w0= "+w);
		int i=0;
		double err=w+2;
		double arg=0,da=0,df=0;
		while (i<niter && abs(err-w)>tol ) {
			err=w;

			arg=-exp((w-a)/g)/g;
			da=(lw_minus.eval(arg)+1.0);	
			df=iq_minus.eval(w);
			//System.out.println(w+" "+arg+" "+da+" "+df);
			if (Double.isNaN(df) || Double.isNaN(da))  {
				System.out.println("lbranch NaN at "+x +" i "+i);
				//System.out.println(arg+" "+w+ " df "+ df +" da "+da);
				return Double.NaN;
			}
			//if (Double.isNaN(df) || Double.isNaN(da))  return w;
			//	w+=da*(df-g*(x));
			w+=(w)*da*(df-g* (x));
			//w=abs(w);
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
  	private double approx(double x, double bb) {
		double cc=lw_plus.eval( a*log(a+1))*g/2;		
 
		//double cc=lw_plus.eval( a*exp(-a/g))*g;
		//double cc=lw_plus.eval( (bb)*exp(bb/g+1)/g)/g;
		//double cc=(g+bb)/g;
		
		//double cc=ww.eval( (bb)*exp(bb/gg+1)/gg)/gg;
		//double cc=(gg+bb)/gg;
		if (x<=0) cc=2*cc;
		//if (cc<1.5) cc=1.5;
		//double w=bb*exp(1.0-exp(-x*cc)-x*cc);
		//double w=bb*exp(1.0-exp(-x*cc)-x*cc - cc*log(abs(x)+1) );
		double w=bb*exp(1.0-exp(-x*cc)-x*cc -cc*x*x*x/12 );
		return w;
	}
	*/
	
	private double approx(double x, double a) {
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
	
	public static void main(String[] args) {
		IS2 lwa=new IS2(4.0, 8.0 );
		
		
		double x=-0.30;
		System.out.println("x= "+x+ " W= " +lwa.eval(x));
		
		/*
		lwa.setVal(1.0, 4.0 );
		
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
		*/
	}

}
