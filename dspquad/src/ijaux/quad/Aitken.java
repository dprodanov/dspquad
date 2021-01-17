package ijaux.quad;

public class Aitken {

	public static boolean debug=false;
	
	public static double[] aitken(double[] seq) {
		final int n=seq.length;
		if (n<3) return seq;
		
		final double[] ret =new double[n-2];
		
		for (int k=0; k<ret.length; k++) {
			final double p0=seq[k];
			final double p1=seq[k+1];
			final double p2=seq[k+2];
			//final double d2=(p2+p0-2.0*p1);
			final double d2=(p2-p1)+(p0-p1);
			ret[k]=(p0*p2 - p1*p1)/d2;
			//ret[k]=p2 -(p2-p1)* (p2-p1)/d2;
		}
		return ret;
	}
	
	public static double converge(double[] seq) {
		 double[] ac= Utils.cumsum(seq);
		 
		 double[] ret=aitken( ac);
		 int k=0;
		 if (debug) {
			 System.out.println("--------Aitken print--------------------");
			 for (int i=0; i<ret.length; i++) 
				 System.out.print("("+i+")->" +ret[i]+" | ");
			 System.out.println("\r\n---------0------------------");
		 }
		 int niter=seq.length/2;
		
		 while (ret.length>2 && k<niter)   {
			 ret=aitken(ret);
			 k++;
			 if (debug) {
				 for (int i=0; i<ret.length; i++) 
					 System.out.print("("+i+")->"+ret[i]+" | ");
				 System.out.println("\r\n---------" + k +" ----------------------");
			 }	 
		 }
		 
		 return ret[0];
	}
	
	
	
	public static void main(String[] args) {
	 
		System.out.println("Arc tan series for PI/4");
		 double[] a=new double[] 
				 {1,-0.3333333333333333,0.2,-0.1428571428571428,0.1111111111111111,-0.09090909090909091,0.07692307692307693};
		 double[] ac= Utils.cumsum(a);
		 
		 for (int i=0; i<ac.length; i++)
			 System.out.print(ac[i]+" ");
		 System.out.println();
		 
		  double[] warr= aitken(ac);
		 System.out.println("--------Aitken print--------------------");
	 	 for (int i=0; i<warr.length; i++) {
				 System.out.print(warr[i]+" | ");
	 
			 System.out.println("\r\n----------------------------");
		 }
		 int n=warr.length-1;
		 
		 System.out.println("PI "+ Math.PI );
		 System.out.println("4*W  -PI");
		 System.out.println(warr[n]*4.0- Math.PI );
		 System.out.println("W  - atan(1)");
		 double ret=converge(a);
		 System.out.println(ret- Math.atan(1.0) );
	 
	}
 
}
