package test.plot.gbessel;

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
import ijaux.quad.plot.UBesselKN;
import ijaux.quad.plot.UGBessel2L;
import ijaux.quad.plot.UGBesselK2;
import ijaux.quad.plot.UGBesselK3;
 
 
public class GBesselK3PlotDemo {

public static void main(String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
        @Override
		public void run() {
            JFrame frame = new JFrame("Charts");

            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            
            XYSeriesCollection dataset = new XYSeriesCollection();
            XYSeries ds1 = dataset2(1.0, 0.25, 5, 150);
            dataset.addSeries(ds1);
            
          
            XYSeries ds2 = dataset2(0.75, 0.25, 5, 150);
            dataset.addSeries(ds2);
            
            XYSeries ds3 = dataset2(0.50, 0.25, 5, 150);
            dataset.addSeries(ds3);
        
            JFreeChart chart2 = ChartFactory.createXYLineChart("Fractional Bessel K Plot",
                    "x", "y", dataset, PlotOrientation.VERTICAL, true, true,
                    false);
            
            ChartPanel cp = new ChartPanel(chart2);
           
            frame.getContentPane().add(cp);
        }
    });

}
 
	
private static XYSeries dataset2(double a, double x0, double xn, int npoints) {
		
		UGBesselK3 uen=new UGBesselK3(a);
	    
		uen.compute(x0, xn, npoints);
	
	    return uen.getSeries();
	}
	
/*
private static XYSeries datasetBesselK0( double x0, double xn, int npoints) {
	
//	XYSeries series=new XYSeries("bessel_k(0,x)");

	UBesselKN uen=new UBesselKN(0);
    
	uen.compute(x0, xn, npoints);

    return uen.getSeries();
}
	
*/
	


}
