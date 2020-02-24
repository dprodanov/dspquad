package test.plot;

import static java.lang.Math.exp;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ijaux.quad.Utils;
import ijaux.quad.plot.UEn;
 
 
public class EnPlotDemo2 {

public static void main(String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
        @Override
		public void run() {
            JFrame frame = new JFrame("Charts");

            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
             XYSeriesCollection dataset = new XYSeriesCollection();
          
            XYSeries ds1 = datasetExp2(-10, 1, 50);
            dataset.addSeries(ds1);
              
          
            JFreeChart chart2 = ChartFactory.createXYLineChart("En Plot",
                    "x", "y", dataset, PlotOrientation.VERTICAL, true, true,
                    false);
            
            ChartPanel cp = new ChartPanel(chart2);
           
            frame.getContentPane().add(cp);
        }
    });

}
 
	private static XYSeries dataset(int n, double x0, double xn, int npoints) {
		
		UEn uen=new UEn(n);
	    
		uen.compute(x0, xn, npoints);
	
	    return uen.getSeries();
	}
	
	private static XYSeries datasetExp2( double x0, double xn, int npoints) {
		
		XYSeries series=new XYSeries("Exp  exp(x)-1-x-x^2/2");
 
		double[][] data = new double[2][]; //{ {0.1, 0.2, 0.3}, {1, 2, 3} };
 		 
	    double[] xx=Utils.linspace(x0, xn, npoints);
	    data[0]=xx;
	    double[] yy=new double[xx.length];
	    for (int i=0; i<xx.length; i++) {
	    	 
    		yy[i]= (exp(xx[i]) - 1 - xx[i]   )/Math.pow(Math.abs(xx[i]), 1.5);
	    	series.add(xx[i], yy[i]);
	    }
	    data[1]=yy;
		    return  series;
	}
	
 


}
