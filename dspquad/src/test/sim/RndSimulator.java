package test.sim;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


import ijaux.quad.Erf;
import ijaux.quad.QFunction;
import ijaux.quad.Utils;

public class RndSimulator {
	
	public QFunction cdf=null;
	
	ThreadLocalRandom rnd=  ThreadLocalRandom.current();
	
	public double[] sample(int n) {
		double[] ret =new double[n];
		int i=0;
		while (i<n) {
			final double ys=rnd.nextDouble();
			double arg=-rnd.nextDouble();
			arg=Math.exp(arg);
			//if (arg!=0.0)
			//	arg=1.0/arg;
			//System.out.println(ys +" "+ arg);
			final double fv=cdf.eval(arg);
			//System.out.println(arg+" ys="+ys+" fv="+fv);
			if (ys >= fv) {
				int sgn=rnd.nextInt(0,2);
				//System.out.println(sgn);
				if (sgn<1) arg=-arg;
				ret[i]=arg;
				i++;
			}
		}
		
		return ret;
	}

	public RndSimulator(QFunction cf) {
		cdf=cf;
	}
	
	public static void printvector(double[] data) {
		for (int i=0; i<data.length; i++) {		
				System.out.println(data[i]+",");		
		}
	}

	public static void main(String[] args) {
		Erf erf =new Erf();
		QFunction Lambda = (x) -> 0.5*erf.eval(x)+0.5;
		RndSimulator sim=new RndSimulator(Lambda);
		
		double[] sample=sim.sample(100);
		
		printvector(sample);

	}

}
