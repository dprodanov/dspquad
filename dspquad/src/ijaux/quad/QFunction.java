package ijaux.quad;


/**
 *  Interface for integrable Kernel functions
 *  @author Dimiter Prodanov
 */
public interface QFunction {

	/**
	 *  Main method to be overwritten
	 * @param x
	 * @return
	 */
	double eval(double x);
	
	@Override
	String toString();
	
	/**
	 * 
	 * @return - multiplication factor
	 */
	default double prefactor() {
		return 1.0;
	};
	
	
}
