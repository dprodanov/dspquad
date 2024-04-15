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
 
 
public class ElPlotDemoSn {

public static void main(String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
        @Override
		public void run() {
            JFrame frame = new JFrame("Charts");

            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
             XYSeriesCollection dataset = new XYSeriesCollection();
    
            XYSeries ds4 = sn_dataset(-5.0, 5.0, 200);
            dataset.addSeries(ds4);
            
            XYSeries ds5 = sn_dataset2(-5.0, 5.0, 200);
            dataset.addSeries(ds5);
            
            XYSeries ds1 = cn_dataset(-5.0, 5.0, 200);
            dataset.addSeries(ds1);
            
            XYSeries ds2 = cn_dataset2(-5.0, 5.0, 200);
            dataset.addSeries(ds2);
            
            
            XYSeries ds3 = dn_dataset(-5.0, 5.0, 200);
            dataset.addSeries(ds3);
            
            XYSeries ds6 = dn_dataset2(-5.0, 5.0, 200);
            dataset.addSeries(ds6);
           
            
            JFreeChart chart2 = ChartFactory.createXYLineChart("sn/cn/dn Plot",
                    "x", "y", dataset, PlotOrientation.VERTICAL, true, true,
                    false);
            
            ChartPanel cp = new ChartPanel(chart2);
           
            frame.getContentPane().add(cp);
        }
    });

}

	private static XYSeries sn_dataset( double x0, double xn, int npoints) {
		
		USn uen=new USn(Math.sqrt(.5));
		
		uen.compute(x0, xn, npoints);
	
	    return uen.getSeries();
	}
	
	 
	private static XYSeries sn_dataset2( double x0, double xn, int npoints) {
		
		USn uen=new USn(Math.sqrt(2.));
		
		uen.compute(x0, xn, npoints);
	
	    return uen.getSeries();
	}
 
	private static XYSeries cn_dataset( double x0, double xn, int npoints) {
		
		UCn uen=new UCn(Math.sqrt(.5));
		
		uen.compute(x0, xn, npoints);
	
	    return uen.getSeries();
	}
	
	 
	private static XYSeries cn_dataset2( double x0, double xn, int npoints) {
		
		UCn uen=new UCn(Math.sqrt(2.));
		
		uen.compute(x0, xn, npoints);
	
	    return uen.getSeries();
	}
	
	
	private static XYSeries dn_dataset( double x0, double xn, int npoints) {
		
		UDn uen=new UDn(Math.sqrt(.5));
		
		uen.compute(x0, xn, npoints);
	
	    return uen.getSeries();
	}
	 
	private static XYSeries dn_dataset2( double x0, double xn, int npoints) {
		
		UDn uen=new UDn(Math.sqrt(2.));
		
		uen.compute(x0, xn, npoints);
	
	    return uen.getSeries();
	}
	 
 

}
