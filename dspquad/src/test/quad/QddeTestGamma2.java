package test.quad;

import static java.lang.Math.PI;
import static java.lang.Math.sqrt;

import ijaux.quad.euler.Gamma;
import ijaux.quad.euler.Gamma2;



public class QddeTestGamma2 {
	
	
public static void main(String[] args) {
	
	
	Gamma2 g2=new Gamma2();	 
	Gamma g= new Gamma();
	
	double[] gvals=new double[]{sqrt(PI), sqrt(PI)/2.0, 3*sqrt(PI)/4.0, 15.0*sqrt(PI)/8.0, 
			135135.0*sqrt(PI)/128.0, -2.0*sqrt(PI), 4.0*sqrt(PI)/3.0};
		
	double ret=g.eval(0.5);
	System.out.println("I =\\int_0^\\infty exp(-x) x^{z-1} dx\n");
	System.out.println (" I="+ ret+"\t, "+ gvals[0]);
	ret=g2.eval(0.5);
	System.out.println (" G="+ ret+"\t, "+ gvals[0]);	   
    
	ret=g.eval(1.5); 
	System.out.println (" I="+ ret+"\t, "+ gvals[1]);
	ret=g2.eval(1.5); 
	System.out.println (" G="+ ret+"\t, "+ gvals[1]);
		 
	ret=g.eval(2.5);
	System.out.println (" I="+ ret+"\t, "+ gvals[2]);
	ret=g2.eval(2.5);
	System.out.println (" G="+ ret+"\t, "+ gvals[2]);
	
	ret=g.eval(3.5);
	System.out.println (" I="+ ret+"\t, "+ gvals[3]);
	ret=g2.eval(3.5);
	System.out.println (" G="+ ret+"\t, "+  gvals[3]);
	
	ret=g.eval(7.5);
	System.out.println (" I="+ ret+"\t, "+  gvals[4]);
	ret=g2.eval(7.5);
	System.out.println (" G="+ ret+"\t, "+  gvals[4]);
	
	ret=g.eval(-0.5);
	System.out.println (" I="+ ret+"\t, "+  gvals[5]);
	
	ret=g.eval(-1.5);
	System.out.println (" I="+ ret+"\t, "+  gvals[6]);
	}
}
