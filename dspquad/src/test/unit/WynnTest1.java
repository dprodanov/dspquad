package test.unit;

import static org.junit.jupiter.api.Assertions.*;
import static ijaux.quad.Wynn.converge;

import ijaux.quad.Utils;

import org.junit.jupiter.api.Test;

class WynnTest1 {

	final double tol=1e-5;
	
	@Test
	void testWaccelerate() {
		 double[] a=new double[] 
				 {1,-0.3333333333333333,0.2,-0.1428571428571428,0.1111111111111111,-0.09090909090909091,0.07692307692307693};
		 double ret=converge(a);
		 double at1= Math.atan(1.0);
		 System.out.println(ret- at1);
		 assert( Utils.apperoxeq(ret, at1, tol ));
		//fail("Not yet implemented");
	}

}
