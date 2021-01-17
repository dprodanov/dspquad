package test.unit;

import static ijaux.quad.QuadDE.intde;
import static ijaux.quad.QuadDE.intdeo;
import static ijaux.quad.QuadDE.intdei;
import static java.lang.Math.PI;
import static java.lang.Math.exp;
import static java.lang.Math.log;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import ijaux.quad.QFunction;
import ijaux.quad.Utils;
 

import org.junit.jupiter.api.Test;

class QuadDETest1 {

	final double tol=1e-15;
	
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
	
	@Test
	void testGamma() {
	 
		 EGammaC fg=new EGammaC();
		 System.out.println("\nI_5=-\\int_0^\\infty epx(-x) log(x) dx\n");
		 double []  reti=intdei(fg, 0.0,  1.0e-15);
		 System.out.println (" I_5="+ reti[0]+"\t, err= "+reti[1]+ "\t, "+ Utils.GA);
		   
		 assert( Utils.apperoxeq(reti[0], Utils.GA , tol ));
		//fail("Not yet implemented");
	}
	
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
	
	@Test
	void testSqrt() {
	 
		System.out.println("I_1=\\int_0^1 1/sqrt(x) dx\n");
		PowF f3=new PowF();
		double[] ret=intde(f3, 0.0, 1.0, 1.0e-15);
		   System.out.println (" I_4="+ ret[0]+"\t, err= "+ret[1]+ "\t, "+2.0);
		   
		 assert( Utils.apperoxeq(ret[0], 2.0 , tol ));
		//fail("Not yet implemented");
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
	
	@Test
	void testPi2() {
	 
		  SincF f2=new SincF();
		   System.out.println("\nI_3=\\int_0^\\infty sin(x)/x dx");
		   
		double[]   ret=intdeo(f2, 0.0, 1.0, 1.0e-15);
		   System.out.println (" I_4="+ ret[0]+"\t, err= "+ret[1]+ "\t, "+PI/2.0);
		   
		 assert( Utils.apperoxeq(ret[0], PI/2.0 , tol ));
		//fail("Not yet implemented");
	}

}
