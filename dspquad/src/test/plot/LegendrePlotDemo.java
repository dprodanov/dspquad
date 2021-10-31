package test.plot;

import static java.lang.Math.exp;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ijaux.quad.Utils;
import ijaux.quad.plot.ULegendre;
 
 
public class LegendrePlotDemo {

public static void main(String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
        @Override
		public void run() {
            JFrame frame = new JFrame("Charts");

            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            
            final XYSeriesCollection dataset = new XYSeriesCollection();
            
            XYSeries ds1 = LegDataset(-2.0, 2.0, 150);
            dataset.addSeries(ds1);
           
            XYSeries ds2 = datasetL2(-2.0, 2.0, 150);
            dataset.addSeries(ds2);
            JFreeChart chart2 = ChartFactory.createXYLineChart("Legendre Plot",
                    "x", "y", dataset, PlotOrientation.VERTICAL, true, true,
                    false);
            
            ChartPanel cp = new ChartPanel(chart2);
           
            frame.getContentPane().add(cp);
        }
    });

}
 
	private static XYSeries LegDataset(double x0, double xn, int npoints) {
		
		ULegendre fn=new ULegendre(2);
	    
		fn.compute(x0, xn, npoints);
	
	    return fn.getSeries();
	}
	

	private static XYSeries datasetL2( double x0, double xn, int npoints) {
		
		XYSeries series=new XYSeries(" (3*x^2-1)/2 ");
 
		double[][] data = new double[2][]; //{ {0.1, 0.2, 0.3}, {1, 2, 3} };
 		 
	    double[] xx=Utils.linspace(x0, xn, npoints);
	    data[0]=xx;
	    double[] yy=new double[xx.length];
	    for (int i=0; i<xx.length; i++) {
	    	 
    		yy[i]= 0.5* (3.0*xx[i]*xx[i] - 1     );
	    	series.add(xx[i], yy[i]);
	    }
	    data[1]=yy;
		    return  series;
	}


}
