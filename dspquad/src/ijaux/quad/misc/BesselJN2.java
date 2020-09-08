/**
 * 
 */
package ijaux.quad.misc;
import static java.lang.Math.*;

import ijaux.quad.QFunction;
import ijaux.quad.gbessel.BesselJ0;
import ijaux.quad.gbessel.BesselJ1;
import ijaux.quad.gbessel.BesselJN;

/**
 * @author prodanov
 *   
 * based on GIPSY 
 * http://www.astro.rug.nl/~gipsy/
 *  not very precise
 */
public class BesselJN2 implements QFunction {

	private int n=0;
	
	private double ACC= 40.0;
	private double BIGNO =1.0e10;
	private double BIGNI=1.0e-10;
	
	BesselJ0 bessj0=new BesselJ0();
	BesselJ1 bessj1=new BesselJ1();
	
	public BesselJN2(int nn) {
		n=nn;
	}
	

	/* (non-Javadoc)
	 * @see ijaux.quad.QFunction#eval(double)
	 */
	@Override
	public double eval(double x) {
		int    j=1,  m=1;
		   double ax=0, bj=1, bjm=1, bjp=0, sum=0, tox=0, ans=0;

		   boolean jsum=true;
		   double fr=1;
		   if (n < 0)  {
		      if (n % 2==-1) fr=-1;
		      //System.out.println(fr+" " + (n % 2) +"  "+n);
		   }
		   ax=abs(x);
		   int an=abs(n);
		   if (an == 0)
		      return( bessj0.eval(ax) );
		   if (an == 1)
		      return( fr*bessj1.eval(ax) );
		   if (ax == 0.0)
		      return 0.0;	   
		   else if (ax > an) {
		      tox=2.0/ax;
		      bjm=bessj0.eval(ax);
		      bj=bessj1.eval(ax);
		      for (j=1;j<an;j++) {
		         bjp=j*tox*bj-bjm;
		         bjm=bj;
		         bj=bjp;
		      }
		      ans=bj;
		   } else {
		      tox=2.0/ax;
		      m=2*((an+(int) sqrt(ACC*an))/2);
		      jsum=true;
		      bjp=ans=sum=0.0;
		      bj=1.0;
		      for (j=m;j>0;j--) {
		         bjm=j*tox*bj-bjp;
		         bjp=bj;
		         bj=bjm;
		         if (abs(bj) > BIGNO) {
		            bj *= BIGNI;
		            bjp *= BIGNI;
		            ans *= BIGNI;
		            sum *= BIGNI;
		         }
		         if (jsum) sum += bj;
		         jsum=!jsum;
		         if (j == n) ans=bjp;
		      }
		      sum=2.0*sum-bj;
		      ans /= sum;
		   }
		   
		  // return  x < 0.0 && n%2 == 1 ? -ans : ans;
		   if (  x < 0.0 && n%2 == 1 ) ans= -ans;
		   ans=fr*ans;
		   return ans;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("BJ 1");
		BesselJN2 bj =new BesselJN2(1);
		
		System.out.println(bj.eval(1.5)+" 0.5579365079100996 ");
		
		BesselJN bji =new BesselJN(1);
		System.out.println(bji.eval(1.5)+" 0.5579365079100996 ");
		
		System.out.println("BJ 2");
		bj =new BesselJN2(2);
		System.out.println(bj.eval(1.5)+" 0.2320876721442147 ");
		
		bji =new BesselJN(2);
		System.out.println(bji.eval(1.5)+" 0.2320876721442147 ");
		
		System.out.println("BJ 3");
		bj =new BesselJN2(3);
		System.out.println(bj.eval(1.5)+" 0.06096395114113964 ");
		
		bji =new BesselJN(3);
		System.out.println(bji.eval(1.5)+" 0.06096395114113964 ");
		
		System.out.println("BJ -1");
		bj =new BesselJN2(-1);
		System.out.println(bj.eval(1.5)+" -0.5579365079100996 ");
		
		bji =new BesselJN(-1);
		System.out.println(bji.eval(1.5)+" -0.5579365079100996 ");
		
		System.out.println(bj.eval(5.5)+" 0.3414382154290433 ");
		System.out.println(bji.eval(5.5)+" 0.3414382154290433 ");
		
		System.out.println("BJ -3");
		bj =new BesselJN2(-3);
		System.out.println(bj.eval(1.5)+" -0.06096395114113964 ");
 
		bji =new BesselJN(-3);
		System.out.println(bji.eval(1.5)+" -0.06096395114113964 ");
		
		System.out.println(bj.eval(5.5)+" -0.2561178651401068 ");
		System.out.println(bji.eval(5.5)+" -0.2561178651401068 ");
		
	}

}
