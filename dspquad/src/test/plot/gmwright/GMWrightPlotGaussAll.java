package test.plot.gmwright;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ijaux.quad.plot.UGMWright;
 
 
public class GMWrightPlotGaussAll {

public static void main(String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
        @Override
		public void run() {
            JFrame frame = new JFrame("GMWright Charts");

            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            final XYSeriesCollection dataset = new XYSeriesCollection();
       
			XYSeries ds0 = dataset(-0.5, 1, -9.0, 9.0, 101);
			dataset.addSeries(ds0);
			XYSeries ds1 = dataset(-0.5, 0.5, -9.0, 9.0, 101);
			dataset.addSeries(ds1);
			XYSeries ds2 = dataset(-0.5, 0, -9.0, 9.0, 101);
			dataset.addSeries(ds2);
			XYSeries ds3 = dataset(-0.5, -0.5, -9.0, 9.0, 101);
			dataset.addSeries(ds3);
			XYSeries ds4 = dataset(-0.5, -1.0, -9.0, 9.0, 101);
			dataset.addSeries(ds4);
			XYSeries ds5 = dataset(-0.5, -1.5, -9.0, 9.0, 101);
			dataset.addSeries(ds5);
			XYSeries ds6 = dataset(-0.5, -2.0, -9.0, 9.0, 101);
			dataset.addSeries(ds6);         
            JFreeChart chart2 = ChartFactory.createXYLineChart("GMWright Plot, a=-1/2, Gaussian derivatives",
                    "x", "y", dataset, PlotOrientation.VERTICAL, true, true,
                    false);
               
            ChartPanel cp = new ChartPanel(chart2);
          
            frame.getContentPane().add(cp);
        }
    });

}
 
	private static XYSeries dataset(double alpha, double beta, double x0, double xn, int npoints) {
		
		UGMWright fn=new UGMWright(alpha, beta);
	    
		fn.compute(x0, xn, npoints);
	
	    return fn.getSeries();
	}


}
