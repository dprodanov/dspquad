package test;

import static ijaux.quad.Wynn.*;

import ijaux.quad.Utils;

public class WynnTest {

	public WynnTest() {
 
	}
	
public static void main(String[] args) {
		
		System.out.println("Arc tan series for PI/4");
		 double[] a=new double[] 
				 {1,-0.3333333333333333,0.2,-0.1428571428571428,0.1111111111111111,-0.09090909090909091,0.07692307692307693};
		 double[] ac= Utils.cumsum(a);
		 System.out.println("-----------cum sum-----------------");
		 for (int i=0; i<ac.length; i++)
			 System.out.print(ac[i]+" ");
		 System.out.println();
		 System.out.println("-----------KK-----------------");
		 double[][] warr= wynn(ac);
		
		 for (int i=0; i<warr.length; i++) {
			 for (int j=0; j<warr[i].length; j++) {
				 System.out.print(warr[i][j]+" | ");
			 }
			 System.out.println("\r\n----------------------------");
		 }
		 int n=warr.length-1;
		 int m=warr[n].length-1;
		 System.out.println("4*W  -PI");
		 System.out.println(warr[n][m]*4.0- Math.PI );
		 System.out.println("W  - atan(1)");
		 System.out.println(warr[n][m]- Math.atan(1.0) );
	}


}
