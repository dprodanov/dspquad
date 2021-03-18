package test.quad;

import static java.lang.Math.*;
import static ijaux.quad.QuadDE.*;
 
import ijaux.quad.QFunction;
 

class PowF implements QFunction {
	
	@Override
	public double eval(double x) {
		return 1/sqrt(x);
	}

	@Override
	public String toString() {
		return "1/sqrt(x)";
	}
}

/*
 * Test casses
 */
class GammaF implements QFunction {
	
	public double val=0.5;
	
	GammaF(double q) {
		val=q;
	}
	
	@Override
	public double eval(double x) {
		return exp(-x) * pow(x, val-1.0);
	}

	@Override
	public String toString() {
		return "exp(-x) *x^(z-1)";
	}
}

// Euler Gamma constant
class EGammaC implements QFunction {
	@Override
	public double eval(double x) {
		return -exp(-x) * log(x);
	}

	@Override
	public String toString() {
		return "-exp(-x) * log(x)";
	}
}


class SincF implements QFunction {
	
	@Override
	public double eval(double x) {
		return sin(x)/x;
	}

	@Override
	public String toString() {
		return "sin(x)/x";
	}
}

public class QddeTest1 {
	
	
public static void main(String[] args) {
		
		System.out.println("I_1=\\int_0^1 1/sqrt(x) dx\n");
		PowF f3=new PowF();
		double[] ret=intde(f3, 0.0, 1.0, 1.0e-15);
		   System.out.println (" I_4="+ ret[0]+"\t, err= "+ret[1]+ "\t, "+2.0);
		   
		System.out.println("I_2=\\int_0^\\infty exp(-x)/sqrt(x) dx\n");
		GammaF f= new GammaF(0.5);
		
		ret=intdei(f, 0.0, 1.0e-15);
		   System.out.println (" I_2="+ ret[0]+"\t, err= "+ret[1]+ "\t, "+sqrt(PI));
		   
	   f.val=1.5;
	   ret=intdei(f, 0.0, 1.0e-15);
	   System.out.println (" I_3="+ ret[0]+"\t, err= "+ret[1]+ "\t, "+sqrt(PI)/2.0);

	   SincF f2=new SincF();
	   System.out.println("\nI_3=\\int_0^\\infty sin(x)/x dx");
	   
	   ret=intdeo(f2, 0.0, 1.0, 1.0e-15);
	   System.out.println (" I_4="+ ret[0]+"\t, err= "+ret[1]+ "\t, "+PI/2.0);
	   
	   ret=intdei(f2, 0.0,   1.0e-15);
	   System.out.println (" I_4="+ ret[0]+"\t, err= "+ret[1]+ "\t, "+PI/2.0);
	   
	   // Euler Gamma constant
	   EGammaC fg=new EGammaC();
	   System.out.println("\nI_5=-\\int_0^\\infty epx(-x) log(x) dx\n");
	   ret=intdeo(fg, 0.0, 1.0, 1.0e-15);
	   System.out.println (" I_5="+ ret[0]+"\t, err= "+ret[1]+ "\t, ");
	   
	   
	}
}
