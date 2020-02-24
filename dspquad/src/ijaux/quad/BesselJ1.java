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
public class BesselJ1 implements QFunction {

	
	/* (non-Javadoc)
	 * @see ijaux.quad.QFunction#eval(double)
	 */
	@Override
	public double eval(double x) {
		double ax,z;
		   double xx,y,ans=0,ans1=1,ans2=1;

		   if ((ax=abs(x)) < 8.0) {
			      y=x*x;
			      ans1=x*(72362614232.0+y*(-7895059235.0+y*(242396853.1
			         +y*(-2972611.439+y*(15704.48260+y*(-30.16036606))))));
			      ans2=144725228442.0+y*(2300535178.0+y*(18583304.74
			         +y*(99447.43394+y*(376.9991397+y*1.0))));
			      ans=ans1/ans2;
			   } else {
			      z=8.0/ax;
			      y=z*z;
			      xx=ax-2.356194491;
			      ans1=1.0+y*(0.183105e-2+y*(-0.3516396496e-4
			         +y*(0.2457520174e-5+y*(-0.240337019e-6))));
			      ans2=0.04687499995+y*(-0.2002690873e-3
			         +y*(0.8449199096e-5+y*(-0.88228987e-6
			         +y*0.105787412e-6)));
			      ans=sqrt(0.636619772/ax)*(cos(xx)*ans1-z*sin(xx)*ans2);
			      if (x < 0.0) ans = -ans;
			   }
		   return ans;
	}
	
	
	public static final double[] zeros5=
	{3.83170597020751,	7.01558666981561,	10.1734681350627,	13.3236919363142,	16.4706300508776};

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BesselJ1 bj =new BesselJ1();
		
		System.out.println(bj.eval(1.5)+" 0.5579365079100996 ");
		System.out.println("zeroes test");
		for (double d:zeros5)
		System.out.println(d+" " +bj.eval(d));

	}

}
