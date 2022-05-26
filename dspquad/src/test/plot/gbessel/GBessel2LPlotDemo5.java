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
import ijaux.quad.plot.UGBessel2L2;
 
 
public class GBessel2LPlotDemo5 {

public static void main(String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
        @Override
		public void run() {
            JFrame frame = new JFrame("Charts");

            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
             XYSeriesCollection dataset = new XYSeriesCollection();
               XYSeries ds1 = dataset2(1.0,-5, 5, 301);
            dataset.addSeries(ds1);
            
       
            XYSeries ds2 = dataset2(0.5,-5, 5, 301);
           dataset.addSeries(ds2);
        
           XYSeries ds3 = dataset2(0.66667,-5, 5, 301);
           dataset.addSeries(ds3);
 
           
            JFreeChart chart2 = ChartFactory.createXYLineChart("Riesz Laplacian Plot",
                    "x", "y", dataset, PlotOrientation.VERTICAL, true, true,
                    false);
            
            ChartPanel cp = new ChartPanel(chart2);
           
            frame.getContentPane().add(cp);
        }
    });

}
 
	
private static XYSeries dataset2(double a, double x0, double xn, int npoints) {
		
		UGBessel2L2 uen=new UGBessel2L2(a);
	    
		uen.compute(x0, xn, npoints);
	
	    return uen.getSeries();
	}
	
	
 
	
	


}
