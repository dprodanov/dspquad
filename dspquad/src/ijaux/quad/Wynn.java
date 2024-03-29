package ijaux.quad;


public class Wynn  {


	/**
	 * The Wynn algorithm
	 *  Wynn, P. On a Device for Computing the e m (S n ) Transformation, 
	 *  Mathematical Tables and Other Aids to Computation 10, no. 54 (April 1956) 91. DOI:10.2307/2002183.
	 * @param arr
	 * @return
	 */
	public static double[][] wynn(double[] arr) {
		final int k=(arr.length-1)/2;
		//System.out.println("k="+k);
		final int n = 2*k+1;
		double[][] ret=new double[n+1][n+1];
		for (int i=0; i<n; i++)
			ret[i+1][1]=arr[i];
		for (int i=2; i<=n; i++)
			for (int j=2; j<=i; j++)
				ret[i][j]=ret[i-1][j-2]+1.0/(ret[i-1][j-1]-ret[i][j-1]); 
		return ret;
	}
	
	/**
	 * 
	 * @param arr
	 * @return
	 */
	public static double[][] wynnshort(double[] arr) {
		final int k=(arr.length-1)/2;
		//System.out.println("k="+k);
		final int n = 2*k+1;
		double[][] ret=new double[n+1][];
		ret[0]=new double[1];
		for (int i=0; i<n; i++) {
			ret[i+1]=new double[i+2];
			ret[i+1][1]=arr[i];
		}
		for (int i=2; i<=n; i++)
			for (int j=2; j<=i; j++) {
				if (j>2)
					ret[i][j]=ret[i-1][j-2]+1.0/(ret[i-1][j-1]-ret[i][j-1]); 
				else 
					ret[i][j]=1.0/(ret[i-1][j-1]-ret[i][j-1]); 
			}
		return ret;
	}
	
	/**
	 * 
	 * @param seq
	 * @return
	 */
	public static double converge(double [] seq) {
		 double[] ac= Utils.cumsum(seq);
		 double[][] warr= wynnshort(ac);
		 
		 if (debug) {
			 System.out.println("--------Wynn print--------------------");
		 	 for (int i=0; i<warr.length; i++) {
				 for (int j=0; j<warr[i].length; j++) {
					 System.out.print(warr[i][j]+" | ");
				 }
				 System.out.println("\r\n----------------------------");
			 }
		 }
		 int n=warr.length-1;
		 int m=warr[n].length-1;
		 return warr[n][m];
	}
	
	public static boolean debug=false;
	
	public static void main(String[] args) {
		
		System.out.println("Arc tan series for PI/4");
		 double[] a=new double[] 
				 {1,-0.3333333333333333,0.2,-0.1428571428571428,0.1111111111111111,-0.09090909090909091,0.07692307692307693};
		 double[] ac= Utils.cumsum(a);
		 
		 for (int i=0; i<ac.length; i++)
			 System.out.print(ac[i]+" ");
		 System.out.println();
		 
		  double[][] warr= wynnshort(ac);
		 System.out.println("--------Wynn print--------------------");
	 	 for (int i=0; i<warr.length; i++) {
			 for (int j=0; j<warr[i].length; j++) {
				 System.out.print(warr[i][j]+" | ");
			 }
			 System.out.println("\r\n----------------------------");
		 }
		 int n=warr.length-1;
		 int m=warr[n].length-1;
		 System.out.println("PI "+ Math.PI );
		 System.out.println("4*W  -PI");
		 System.out.println(warr[n][m]*4.0- Math.PI );
		 System.out.println("W  - atan(1)");
		
	}

  

}
