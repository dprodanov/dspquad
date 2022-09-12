package test.quad;

import static java.lang.Math.*;
import static ijaux.quad.QuadDE.*;
 
import ijaux.quad.QFunction;
import ijaux.quad.euler.Beta;
 

/*
 * beta(1/2,1/2*(k+1))
 */
 class SinF implements QFunction {
	
	public double val=0.5;
	
	SinF(double q) {
		val=q;
	}
	
	@Override
	public double eval(double x) {
		final double d=sin(x);
		return   pow(d, val);
	}

	@Override
	public String toString() {
		return "sin(x)^z";
	}
}
  

public class QddeTestBeta {
	
	
	
	
public static void main(String[] args) {
	 Beta bf= new Beta();
	 double ret=bf.eval (0.5, 2.5);
	 System.out.println(" beta(1/2,1/2*(k+1)), k=4  3 \\pi/8");
	 System.out.println (" I_4="+ ret+"\t, "+  (3.0 *PI)/8.0 );
	 
	 SinF f= new SinF(4.0);
		
	double[]	ret2=intde(f, 0.0, PI, 1.0e-15);
		   System.out.println (" I_2="+ ret2[0]+"\t, err= "+ret2[1]+ "\t, "+ret);
		   

}
	 
}
