/**
 * 
 */
package ijaux.quad.gbessel;
import static java.lang.Math.*;

import ijaux.quad.QFunction;
import ijaux.quad.wright.GMWright;

/**
 * @author prodanov
 *   
 *
 */
public class BesselJN implements QFunction {

	private int n=0;
	GMWright gm=null;
	
	public BesselJN(int nn) {
		n=nn;
		gm= new GMWright(1, n+1);
		
	}
	
	/*
	private double sgn (double z) {
		if (z>=0) return 1.0;
		else return -1.0;
	}
	*/
	
	/* (non-Javadoc)
	 * @see ijaux.quad.QFunction#eval(double)
	 */
	@Override
	public double eval(double x) {
		 double fr=1.0;
		 final double dd=gm.eval(-0.25*x*x);
		 
		 if (n==0)
			 return dd;
		 if (n==1)
			fr= (x)/2.0;
		 else {
			 fr=pow(x/2,n);
		 }
		 return fr*dd;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		double[][] zeros510=new double[][] {
		 {2.40482555769577,	5.52007811028631,	8.65372791291101,	11.7915344390142,	14.9309177084877},
		 {3.83170597020751,	7.01558666981561,	10.1734681350627,	13.3236919363142,	16.4706300508776},
		 {5.13562230184068,	8.41724414039986,	11.6198411721490,	14.7959517823512,	17.9598194949878},
		 {6.38016189592398,	9.76102312998166,	13.0152007216984,	16.2234661603187,	19.4094152264350},
		 {7.58834243450380,	11.0647094885011,	14.3725366716175,	17.6159660498048,	20.8269329569623},
		 {8.77148381595995,	12.3386041974669,	15.7001740797116,	18.9801338751799,	22.2177998965612},
		 {9.93610952421768,	13.5892901705412,	17.0038196678160,	20.3207892135665,	23.5860844355813},
		 {11.0863700192450,	14.8212687270131,	18.2875828324817,	21.6415410198484,	24.9349278876730},
		 {12.2250922640046,	16.0377741908877,	19.5545364309970,	22.9451731318746,	26.2668146411766},
		 {13.3543004774353,	17.2412203824891,	20.8070477892641,	24.2338852577505,	27.5837489635730},
		 {14.4755006865545,	18.4334636669665,	22.0469853646978,	25.5094505541828,	28.8873750635304}};

		int order=0;
		
		BesselJN bj =new BesselJN(0);
		
		System.out.println(bj.eval(1.5)+" 0.5579365079100996 ");
	 
		double[] zeros1=zeros510[order];
		System.out.println("zeroes test order "+order);
		for (double d:zeros1)
			System.out.println(d+" " +bj.eval(d));
			
		//System.out.println(bj.eval(508.152857707228));
		order++;
		
		bj =new BesselJN(order);
		System.out.println(bj.eval(1.5)+" 0.5579365079100996 ");
		
		zeros1=zeros510[order];
		System.out.println("zeroes test order "+order);
		for (double d:zeros1)
			System.out.println(d+" " +bj.eval(d));
		
		order=10;
		bj =new BesselJN(order);
			
		zeros1=zeros510[order];
		System.out.println("zeroes test order "+order);
		for (double d:zeros1)
			System.out.println(d+" " +bj.eval(d));
		
	}
}
