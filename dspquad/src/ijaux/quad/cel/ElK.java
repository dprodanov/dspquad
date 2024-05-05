package ijaux.quad.cel;

import static java.lang.Math.*; 

/**
 * complete elliptic integral of the 1st kind 
 * */
public class ElK extends ElliptiC {

	public ElK() {
		super(1.0, 1.0, 1.0);
	}
	
	@Override
	public double eval(double kc) {
		double k=1-kc;
		if (k<0) return 0;
		else {
			k=sqrt(k);
			return super.eval(k);
		}
	}

}
