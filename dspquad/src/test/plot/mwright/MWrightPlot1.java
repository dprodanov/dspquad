package test.plot.mwright;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ijaux.quad.plot.UMWright;
 
 
public class MWrightPlot1 {

public static void main(String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
        @Override
		public void run() {
            JFrame frame = new JFrame("MWright Charts");

            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            final XYSeriesCollection dataset = new XYSeriesCollection();
       
           XYSeries ds1 = dataset(0.25, -5.0, 5.0, 101);
           dataset.addSeries(ds1);
           XYSeries ds2 = dataset(0.5, -5.0, 5.0, 101);
           dataset.addSeries(ds2);
           XYSeries ds3 = dataset(0.33333, -5.0, 5.0, 101);
           dataset.addSeries(ds3);
           XYSeries ds4 = dataset(0.66667, -5.0, 5.0, 101);
           dataset.addSeries(ds4);
           XYSeries ds5 = dataset(0.75, -5.0, 5.0, 101);
           dataset.addSeries(ds5);
          
            JFreeChart chart2 = ChartFactory.createXYLineChart("MWright Plot",
                    "x", "y", dataset, PlotOrientation.VERTICAL, true, true,
                    false);
               
            ChartPanel cp = new ChartPanel(chart2);
          
            frame.getContentPane().add(cp);
        }
    });

}
 
	private static XYSeries dataset(double alpha, double x0, double xn, int npoints) {
		
		UMWright gamma=new UMWright(alpha);
	    
		gamma.compute(x0, xn, npoints);
	
	    return gamma.getSeries();
	}


}
