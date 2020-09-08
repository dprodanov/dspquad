package ijaux.quad.sir;

import static ijaux.quad.QuadDE.intde;
import static ijaux.quad.QuadDE.intdea;
//import static java.lang.Math.PI;
import static java.lang.Math.exp;
import static java.lang.Math.log;

import ijaux.quad.QFunction;
import ijaux.quad.lam.LambertW;

/*
 * SIR model preparation
 */
public class InvI implements QFunction {

	//private int branch=0;
	private double tol=1e-15;
	private double a=1.0;
	private double g=1.0;

	private KerF qf=new KerF();
	
	private LambertW lw=new LambertW();
	
	//////////////////
	// Kernel
	/////////////////
	private class KerF implements QFunction {

		private double ak;
		private double gk;
		
		public void setVal( double gg, double aa) {
			ak=aa;
			gk=gg;
		}
		

		
		@Override
		public double eval(double u) {
			final double arg=-exp((u-gk)/gk);
			final double d=1.0/((u+ak)*(lw.eval(arg)+1.0));
			//System.out.println(u+" " +d);
			if (Double.isFinite(d)) return d;
			else {
				//System.out.println(u+" " +d);
				return 0.0;
			}
		}

		public double prefactor() {
			return -1.0;
		};

		@Override
		public String toString() {
			return "1/((x+b)*(W(-exp( (x-g)/g))+1));";
		}



	}
	////////////////////////////

	public InvI() {}

	public InvI(double gg, double aa) {
		a=aa;
		g=gg;
		//System.out.println(" a "+a+", g  "+g);
	}
	
	public void setVal( double gg, double aa) {
		a=aa;
		g=gg;
	}

	public void setBranch(int br) {
		lw.setBranch(br);
	}
	
	@Override
	public double eval(double x) {
		double bb=g*log(g)-g+a;
		//System.out.println("bb= "+bb);
		double ret=qf.prefactor();
		if (x==bb) return 0.0;
		
		if (x<0 || x> bb)  {return Double.NaN;};

		//System.out.println("t= "+(x-bb));
		
		qf.setVal(g, bb);
		final double[] vv=intde(qf, 0.0, x-bb, tol);
		
		return ret*vv[0];
	}
	
	public static void main(String[] args) {
		InvI lwa=new InvI(1.0, 4.0 );
		double x=0.5;
		lwa.setBranch(-1);
 
//		System.out.println("x= "+x+ " W= " +lwa.eval(x));
		x=2.0;
		System.out.println("x= "+x+ " W= " +lwa.eval(x));
		x=2.5;
		System.out.println("x= "+x+ " W= " +lwa.eval(x));
		
		lwa.setBranch(0);
		
		x=-2.0;
		System.out.println("x= "+x+ " W= " +lwa.eval(x));
		x=-2.5;
		System.out.println("x= "+x+ " W= " +lwa.eval(x));
		lwa.setVal(1.0, 3.0);
		x=1e-2;
		System.out.println("x= "+x+ " W= " +lwa.eval(x)+" 6.131914764567867");
	}

}
