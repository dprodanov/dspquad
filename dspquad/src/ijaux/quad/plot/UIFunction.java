package ijaux.quad.plot;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;

/*
 *  support of JFreeChart functionality
 */
public interface UIFunction extends IFChart {

	void compute(double x0, double xn, int npoints);
	
	void compute(double[] dpoints, double[] points);
	
	XYDataset getData();
	
	XYSeries getSeries();
	
	void setTitle(String title);
	
	String getTile();
	
	
	
	
}
