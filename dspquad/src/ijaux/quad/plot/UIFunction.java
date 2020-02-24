package ijaux.quad.plot;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;

/*
 *  support of JFreeChart functionality
 */
public interface UIFunction {

	void compute(double x0, double xn, int npoints);
	
	void compute(double[] dpoints, double[] points);
	
	XYDataset getData();
	
	XYSeries getSeries();
	
	void setTitle(String title);
	
	String getTile();
	
}
