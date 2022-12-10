package ijaux.quad;

//import static ijaux.quad.QuadDE.intde;
import static java.lang.Math.PI;

//import ijaux.quad.Wynn;
import ijaux.quad.gbessel.BesselJ0;
import ijaux.quad.gbessel.BesselKN;

/**
 *  Hankel Transformations
 * @author prodanov
 *
 */
public class QuadBess extends QuadDE implements QFunction {
	
	private QFunction kernel=null;
	
	private BesselJ0 besselj0 =new BesselJ0();
		
	private double[] nzeros=null;
	
	AuxF qf=new AuxF();
	
	public double tol=1e-15;

	//public static boolean debug=false;
	
	public QuadBess(QFunction ker, int n) {
		kernel=ker;
		nzeros=new double [n+1];
		for (int k=0; k<n+1; k++)
			nzeros[k]=bazero_j(0, k);
		if (debug) {
		   for (int k=0; k<nzeros.length; k++)
			System.out.println( nzeros[k]);
		}
	}
	
	class AuxF implements QFunction {
		
		double z=0;
		
		public void setValue(double xx) {
			z=xx;
		}

		@Override
		public double eval(double x) {
			double ret=besselj0.eval(x*z)*kernel.eval(x);
			return ret;
		}
		
	}
	
 
	/**
	 * asymptotic zero of the BesselJ function
	 * Lucas, Stephen & Stone, H.A.. (1995). 
	 * Evaluating infinite integrals involving Bessel functions of arbitrary order. 
	 * Journal of Computational and Applied Mathematics. 64. 217-231. DOI 10.1016/0377-0427(95)00142-5.
	 * @param n - order of the function
	 * @param k - order of the zero
	 * @return
	 */
	public static double bazero_j (int n, int k) {
		if (k<1) return 0.0;
		else return PI*(0+k-0.25);
	}
	
 
	
 public static boolean debug=false;

	@Override
	public double eval(double x) {
		int nz=nzeros.length;
		double[] seq=new double [nz-1];
		//System.out.println("nz=" +nz);
		qf.setValue(x);
		for (int i=0; i<seq.length; i++) {
		  double[] ret=intde(qf, nzeros[i]/x, nzeros[i+1]/x, tol);
		  if (debug)
			  System.out.println(ret[0]+ " - "+i+ " "+nzeros[i+1]);
		  seq[i]=ret[0];
		}
		final double val= Wynn.converge(seq);
		// System.out.println(val);
		return val;
	}

	public static void main(String[] args) {
	 
		int n=20;
		
		QFunction ker=new QFunction() {

			@Override
			public double eval(double x) {
				return x/(1.0+x*x);
			}
			
		};
		
		QuadBess qb=new	 QuadBess(  ker, n);
		
		
		BesselKN bn= new BesselKN(0);
		long time=System.currentTimeMillis();
		double bz=bn.eval(1.0);
		time=System.currentTimeMillis()-time;
		System.out.println("K0(1)="+ bz+"  " +0.421024438240708333+ " time "+time+" diff "+(bz-0.421024438240708333));
		time=System.currentTimeMillis();
		double bz2=qb.eval(1.0);
		time=System.currentTimeMillis()-time;
		System.out.println("K0(1)="+ bz2+"  " +0.421024438240708333+ " time "+time+" diff "+(bz2-0.421024438240708333));
		
	}

	
	
	
}
