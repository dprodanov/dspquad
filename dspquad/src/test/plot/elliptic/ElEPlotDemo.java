package test.plot.elliptic;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ijaux.quad.plot.UElE;
 
 
public class ElEPlotDemo {

public static void main(String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
        @Override
		public void run() {
            JFrame frame = new JFrame("Charts");

            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
             XYSeriesCollection dataset = new XYSeriesCollection();
    
            XYSeries ds5 = dataset(-2.0, 2.0, 100);
            dataset.addSeries(ds5);
            
            JFreeChart chart2 = ChartFactory.createXYLineChart("E Plot",
                    "x", "y", dataset, PlotOrientation.VERTICAL, true, true,
                    false);
            
            ChartPanel cp = new ChartPanel(chart2);
           
            frame.getContentPane().add(cp);
        }
    });

}
 
	private static XYSeries dataset( double x0, double xn, int npoints) {
		
		UElE uen=new UElE();
	    
		uen.compute(x0, xn, npoints);
	
	    return uen.getSeries();
	}
	
	 
	
	 
	
	 


}
