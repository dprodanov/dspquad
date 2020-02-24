package test.plot.gbessel;

import static java.lang.Math.pow;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ijaux.quad.Utils;
import ijaux.quad.plot.UGBessel;
 
 
public class GBesselPlotDemo {

public static void main(String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
        @Override
		public void run() {
            JFrame frame = new JFrame("Charts");

            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
             XYSeriesCollection dataset = new XYSeriesCollection();
               XYSeries ds1 = dataset2(0.5,-5, 5, 301);
            dataset.addSeries(ds1);
            
          
            XYSeries ds2 = datasetPoiss(-5, 5, 301);
           dataset.addSeries(ds2);
            
            
            JFreeChart chart2 = ChartFactory.createXYLineChart("Fractional Laplacian Plot",
                    "x", "y", dataset, PlotOrientation.VERTICAL, true, true,
                    false);
            
            ChartPanel cp = new ChartPanel(chart2);
           
            frame.getContentPane().add(cp);
        }
    });

}
 
	
private static XYSeries dataset2(double a, double x0, double xn, int npoints) {
		
		UGBessel uen=new UGBessel(a);
	    
		uen.compute(x0, xn, npoints);
	
	    return uen.getSeries();
	}
	
	
private static XYSeries datasetPoiss( double x0, double xn, int npoints) {
	
	XYSeries series=new XYSeries("1/(1+x^2)^(3/2)");

	double[][] data = new double[2][]; //{ {0.1, 0.2, 0.3}, {1, 2, 3} };
		 
    double[] xx=Utils.linspace(x0, xn, npoints);
    data[0]=xx;
    double[] yy=new double[xx.length];
    for (int i=0; i<xx.length; i++) {
    	final double x2=xx[i]*xx[i];
		yy[i]= pow(1+x2, -1.5);
    	series.add(xx[i], yy[i]);
    }
    data[1]=yy;
	    return  series;
}
	
	
	


}
