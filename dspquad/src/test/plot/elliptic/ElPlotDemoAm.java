package test.plot.elliptic;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ijaux.quad.plot.*;
 
 
public class ElPlotDemoAm {

public static void main(String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
        @Override
		public void run() {
            JFrame frame = new JFrame("Charts");

            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
             XYSeriesCollection dataset = new XYSeriesCollection();
    
            XYSeries ds5 = am_dataset(.0, 20.0, 200);
            dataset.addSeries(ds5);
            
            XYSeries ds4 = am_dataset2(.0, 20.0, 200);
            dataset.addSeries(ds4);
            
            XYSeries ds3 = am_dataset3(.0, 20.0, 200);
            dataset.addSeries(ds3);
            
            XYSeries ds2 = am_dataset4(.0, 20.0, 200);
            dataset.addSeries(ds2);
            
            XYSeries ds7 = eps_dataset(0, 20.0, 200);
            dataset.addSeries(ds7);
            
            JFreeChart chart2 = ChartFactory.createXYLineChart("Am Plot",
                    "x", "y", dataset, PlotOrientation.VERTICAL, true, true,
                    false);
            
            ChartPanel cp = new ChartPanel(chart2);
           
            frame.getContentPane().add(cp);
        }
    });

}
 
	private static XYSeries am_dataset( double x0, double xn, int npoints) {
		
		UAm uen=new UAm(0.25);
		
		uen.compute(x0, xn, npoints);
	
	    return uen.getSeries();
	}
	
	 
	private static XYSeries am_dataset2( double x0, double xn, int npoints) {
		
		UAm uen=new UAm(0.75);
		
		uen.compute(x0, xn, npoints);
	
	    return uen.getSeries();
	}
	
	 
	private static XYSeries am_dataset3( double x0, double xn, int npoints) {
		
		UAm uen=new UAm(1.25);
		
		uen.compute(x0, xn, npoints);
	
	    return uen.getSeries();
	}
	 
	private static XYSeries am_dataset4( double x0, double xn, int npoints) {
		
		UAm uen=new UAm(4.);
		
		uen.compute(x0, xn, npoints);
	
	    return uen.getSeries();
	}
	
private static XYSeries eps_dataset( double x0, double xn, int npoints) {
		
		UEps uen=new UEps(0.75);
		
		uen.compute(x0, xn, npoints);
	
	    return uen.getSeries();
	}

	

}
