package test.quad;

import static java.lang.Math.*;
import static ijaux.quad.QuadDE.*;
 
import ijaux.quad.QFunction;
 
 


//Euler Gamma constant
class CatlanAsin implements QFunction {
	@Override
	public double eval(double x) {
		double y=sin(x);
		if (y==0.0) return 0.0;
		return asin(y)/y;
	}

	@Override
	public String toString() {
		return "asin(sin(x))/ sin(x)";
	}
}

class CatlanAtan implements QFunction {
	@Override
	public double eval(double x) {
		double y=sin(x);
		if (y==0.0) return 0.0;
		return atan(y)/y;
	}

	@Override
	public String toString() {
		return "atan(x)/x";
	}
}

 

public class QddeTest3 {
	
	
public static void main(String[] args) {
	/*
	 * Catalan constant
	 */
	System.out.println("\nI_5= \\int_0^\\pi/2 asin(sin(x))/ sin(x) dx\n");
 	 
		CatlanAsin fg=new CatlanAsin();
		   
		   double[] ret=intdeaf(fg, 0.0, PI/2.0, 1.0e-15);
		   System.out.println (" I_5="+ ret[0]*0.5+"\t, err= "+ret[1]+ "\t, "+0.9159655941772190150546035);
		   
		   System.out.println("\nI_5= \\int_0^\\1 atan(x)/x dx\n");
		   CatlanAtan fg1=new CatlanAtan();
		   
		    ret=intdeaf(fg1, 0.0, 1.0, 1.0e-15);
		   System.out.println (" I_5="+ ret[0]+"\t, err= "+ret[1]+ "\t, "+ 0.9159655941772190150546035);
	}
}
