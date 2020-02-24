package test.plot.bessel;



import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ijaux.quad.Utils;
import ijaux.quad.plot.UBesselJ0;
import ijaux.quad.plot.UGMWright;
 
 
public class BesselJ0PlotDemo1 {

public static void main(String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
        @Override
		public void run() {
            JFrame frame = new JFrame("Charts");

            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
             XYSeriesCollection dataset = new XYSeriesCollection();
          
            XYSeries ds1 = dataset(-25, 25, 300);
            dataset.addSeries(ds1);
            
            XYSeries ds2 = datasetJ0(-25, 25, 300);
            dataset.addSeries(ds2);
            
            
            JFreeChart chart2 = ChartFactory.createXYLineChart("Bessel J0 Plot",
                    "x", "y", dataset, PlotOrientation.VERTICAL, true, true,
                    false);
            
            ChartPanel cp = new ChartPanel(chart2);
           
            frame.getContentPane().add(cp);
        }
    });

}
 
	private static XYSeries dataset( double x0, double xn, int npoints) {
		
		UBesselJ0 uen=new UBesselJ0();
	    
		uen.compute(x0, xn, npoints);
	
	    return uen.getSeries();
	}
	
	
private static XYSeries datasetJ0( double x0, double xn, int npoints) {
		
		UGMWright fn=new UGMWright(1, 1);
		 double[] x=Utils.linspace(x0, xn, npoints);
		 double[] x2=new double[npoints];
		 for (int i=0; i<npoints; i++) {
			 x2[i]=-x[i]*x[i]/4.0;
		 }
		
		//double eps=(abs(xn));
		//System.out.println("eps "+eps);
		//fn.setEps(eps);
		fn.compute(x, x2);
		 
		 
	    return fn.getSeries();
	}
	
	
	


}
