package test.plot.mwright;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ijaux.quad.plot.UIMWright;
 
 
public class IMWrightPlot1 {

public static void main(String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
        @Override
		public void run() {
            JFrame frame = new JFrame("Charts");

            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            final XYSeriesCollection dataset = new XYSeriesCollection();
       
           XYSeries ds1 = dataset(0.25, -8.0, 8.0, 100);
           dataset.addSeries(ds1);
           XYSeries ds2 = dataset(0.5, -8.0, 8.0, 100);
           dataset.addSeries(ds2);
           XYSeries ds3 = dataset(0.33333, -8.0, 8.0, 100);
           dataset.addSeries(ds3);
           XYSeries ds4 = dataset(0.66667, -8.0, 8.0, 100);
           dataset.addSeries(ds4);
          
            JFreeChart chart2 = ChartFactory.createXYLineChart("IMWright Plot",
                    "x", "y", dataset, PlotOrientation.VERTICAL, true, true,
                    false);
               
            ChartPanel cp = new ChartPanel(chart2);
          
            frame.getContentPane().add(cp);
        }
    });

}
 
	private static XYSeries dataset(double alpha, double x0, double xn, int npoints) {
		
		UIMWright fn=new UIMWright(alpha);
	    
		fn.compute(x0, xn, npoints);
	
	    return fn.getSeries();
	}


}
