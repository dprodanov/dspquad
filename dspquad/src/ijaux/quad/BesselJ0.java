/**
 * 
 */
package ijaux.quad;
import static java.lang.Math.*;

/**
 * @author prodanov
 *  code based on http://www.astro.rug.nl/~gipsy/sub/bessel.c
 *  based on  M.G.R. Vogelaar
 *
 */
public class BesselJ0 implements QFunction {

	
	/* (non-Javadoc)
	 * @see ijaux.quad.QFunction#eval(double)
	 */
	@Override
	public double eval(double x) {
		double ax,z;
		   double xx,y,ans=0,ans1=1,ans2=1;

		   if ((ax=abs(x)) < 8.0) {
		      y=x*x;
		      ans1=57568490574.0+y*(-13362590354.0+y*(651619640.7
		         +y*(-11214424.18+y*(77392.33017+y*(-184.9052456)))));
		      ans2=57568490411.0+y*(1029532985.0+y*(9494680.718
		         +y*(59272.64853+y*(267.8532712+y*1.0))));
		      ans=ans1/ans2;
		   } else {
		      z=8.0/ax;
		      y=z*z;
		      xx=ax-0.785398164;
		      ans1=1.0+y*(-0.1098628627e-2+y*(0.2734510407e-4
		         +y*(-0.2073370639e-5+y*0.2093887211e-6)));
		      ans2 = -0.1562499995e-1+y*(0.1430488765e-3
		         +y*(-0.6911147651e-5+y*(0.7621095161e-6
		         -y*0.934935152e-7)));
		      ans=sqrt(0.636619772/ax)*(cos(xx)*ans1-z*sin(xx)*ans2);
		   }
		   return ans;
	}

	public static final double[] zeros5=
		{2.40482555769577, 5.52007811028631, 8.65372791291101, 11.7915344390142, 14.9309177084877};
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BesselJ0 bj =new BesselJ0();
		
		System.out.println(bj.eval(1.5)+" 0.511827671735918 ");
		// zeros
		System.out.println("zeroes test");
		for (double d:zeros5)
		System.out.println(d+" " +bj.eval(d));
		
		System.out.println(bj.eval(508.152857707228));
	}

}
