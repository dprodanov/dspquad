package ijaux.quad.sir;

import static ijaux.quad.QuadDE.intde;
import static ijaux.quad.QuadDE.intdea;
//import static java.lang.Math.PI;
import static java.lang.Math.exp;
import static java.lang.Math.log;

import ijaux.quad.QFunction;
import ijaux.quad.lam.LambertO2;

/*
 * SIR model preparation
 */
public class InvI4 implements QFunction {

	private double tol=1e-15;
	private double bb=1.0;
	private double g=1.0;

	private KerF qf=new KerF();
	
	private LambertO2 lwo=new LambertO2();
	
	
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
			if (u<0) return Double.NaN;
			double d=(u*(gk*log(u/gk)-u+gk+ak));
			if (d==0) return 0;
			return 1/d;
		}

		public double prefactor() {
			return -g;
		};

		@Override
		public String toString() {
			return "g/(y*(g*log(y)-y+g+a));";
		}



	}
	////////////////////////////

	public InvI4() {}

	public InvI4(double gg, double aa) {
		bb=aa;
		g=gg;
	}
	
	public void setVal( double gg, double aa) {
		bb=aa;
		g=gg;
	}

	public void setBranch(int br) {
		lwo.setBranch(br);
	}
	
	@Override
	public double eval(double x) {
		//double bb=g*log(g)+a-g;
		//System.out.println("bb= "+bb);
		double ret=qf.prefactor();
		if (x==bb) return 0.0;
		
		if (x<0 || x> bb)  {return Double.NaN;};
 		
		qf.setVal(g, bb);
		 
		final double arg=(x-(bb+g))/g;
		final double dd=lwo.eval(arg)*g;
		
		final double[] vv=intde(qf, g, dd, tol);
		
		return ret*vv[0];
	}
	
	public static void main(String[] args) {
		InvI3 lwa=new InvI3(0.5, 6.0 );
		double x=0.5;
		lwa.setBranch(-1);
 
//		System.out.println("x= "+x+ " W= " +lwa.eval(x));
		x=2.0;
		System.out.println("x= "+x+ " W= " +lwa.eval(x)+" -0.2765399758456005");
		x=4.0;
		System.out.println("x= "+x+ " W= " +lwa.eval(x)+" -0.1654338982031921");
		
		lwa.setBranch(0);
		
		x=2.0;
		System.out.println("x= "+x+ " W= " +lwa.eval(x)+ " 1.049142956680817");
		x=4.0;
		System.out.println("x= "+x+ " W= " +lwa.eval(x) +" 0.3506353955758684");
		lwa.setVal(1.0, 3.0);
		x=1e-2;
		System.out.println("x= "+x+ " W= " +lwa.eval(x)+" 6.131914764575211");
		lwa.setBranch(-1);
		System.out.println("x= "+x+ " W= " +lwa.eval(x)+" -2.14625311521737");
	}

}
