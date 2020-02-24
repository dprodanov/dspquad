package ijaux.quad;

import static java.lang.Math.*;

import ijaux.quad.euler.Gamma;

import static ijaux.quad.Utils.*;

public class En implements QFunction {
	
	 private Gamma gam=new Gamma();
	 
	 private double[] coeff=null;
	 
	 private int n=0;

	 public En(int nn) {
		 if (n<0) throw new IllegalArgumentException(" n>0 "+nn);
		 n=nn-1;
		 coeff=new double[n+1];
		 if (n==0) coeff[0]=1;
		 for (int k=1; k<=n; k++) {
			 coeff[k]=round(frat2(k, n));
		 }
		 if (n>0)
			 coeff[0]=coeff[1];
		// Util.printDoubleArray(coeff);
	 }
	 
	 
	/*
	 * frat(k,n):=block(   if k<2 then gamma(n+1) else n/k*frat(k-1,n-1) );
	 *
	private  double frat(double k, double n) {
		if (k<2.0) {
			return gam.eval (n+1);
		} else {
			return n/k*frat(k-1,n-1);
		}
	}
	*/
	 
	private  double frat2(double k, double n) {
		double ret=1.0;
		do {
			ret= n/k*ret ;
			k=k-1; 
			n=n-1;
		} while (k>=2);
		int iret=(int) round(ret*gam.eval(n+1));
		return iret;
	}
	
	@Override
	public double eval(double x) {
		double value=0;
		if (n>=0)
		   value=polyval2(coeff, x)/coeff[0];
		return exp(x)-value;
	}
	
	public String printCoeff() {
		StringBuffer sb=new StringBuffer(100);
		
		for (int i=0; i<coeff.length; i++) {
			if (i>0)
				sb.append(" + "+ coeff[i]+"*x^"+i +"/"+coeff[0]);
			else 
				sb.append(coeff[0]+"/"+coeff[0]);
		}
		
		return sb.toString();
	}
	
	

	public static void main(String[] args) {
		
		int n=0;
		En polen=new En(n); 
		//Util.printDoubleArray(polen.coeff);
		System.out.println(polen.printCoeff());
		System.out.println(polen.eval(1.0));
		
		n=1;
		polen=new En(n);
		//Util.printDoubleArray(polen.coeff);
		System.out.println(polen.printCoeff());
		n=2;
		polen=new En(n); 
		//Util.printDoubleArray(polen.coeff);
		System.out.println(polen.printCoeff());
		n=3;
		polen=new En(n); 
		//Util.printDoubleArray(polen.coeff);
		System.out.println(polen.printCoeff());
		
		n=4;
		polen=new En(n); 
		//Util.printDoubleArray(polen.coeff);
		System.out.println(polen.printCoeff());
		//n=3;
		//polen=new En(n); 
		/*
		System.out.println(polen.frat(2,n));
		System.out.println(polen.frat2(2,n));
		
		System.out.println(polen.frat(3,n));
		System.out.println(polen.frat2(3,n));
		*/
		System.out.println(polen.eval(2.0)+" "+1.055722765597317);
		
		System.out.println(polen.eval(1.0)+" "+0.05161516179237857);

	}


	@Override
	public String toString() {
		return "exp(x)-T_"+n+"(x)";
	}

}
