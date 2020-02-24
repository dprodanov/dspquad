package ijaux.quad.cel;

import static java.lang.Math.sqrt;

public class ElE extends ElliptiC {

	public ElE() {
		super(1.0, 1.0, 1.0);
	}
	
	 
	@Override
	public double eval(double kc) {
		double k=1.0-kc;
		if (k<0) return 0;
		else {
			k=sqrt(k);
			this.setParams(1.0, 1.0, k*k);
			return super.eval(k);
		}
		//return cel(kc, 1.0, 1.0, kc*kc);

	}

}
