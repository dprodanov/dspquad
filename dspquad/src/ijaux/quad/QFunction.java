package ijaux.quad;


/*
 *  Interface for integrable Kernel functions
 */
public interface QFunction {

	double eval(double x);
	
	@Override
	String toString();
	
	default double prefactor() {
		return 1.0;
	};
	
	
}
