package test.plot.gmwright;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ijaux.quad.plot.IFChart;
import ijaux.quad.plot.UGMWright;
 
 
public class GMWrightPlot1 implements IFChart {

public static void main(String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
    	
    	GMWrightPlot1 ip= new GMWrightPlot1();
    	
        @Override
		public void run() {
            JFrame frame = new JFrame("GmWright Charts");

            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            final XYSeriesCollection dataset = new XYSeriesCollection();
       
           XYSeries ds1 = dataset(-0.5, 0.5, -9.0, 9.0, 101);
           dataset.addSeries(ds1);
           XYSeries ds2 = dataset(-0.5, 1.0, -9.0, 9.0, 101);
           dataset.addSeries(ds2);
           XYSeries ds3 = dataset(-0.33333, 0.66667, -9.0, 9.0, 101);
           dataset.addSeries(ds3);
           XYSeries ds4 = dataset(-0.33333, 1.0, -9.0, 9.0, 101);
           dataset.addSeries(ds4);
           XYSeries ds5 = dataset(-0.66667, 0.33333, -9.0, 9.0, 101);
           dataset.addSeries(ds5);
           XYSeries ds6 = dataset(-0.66667, 1.0, -9.0, 9.0, 101);
           dataset.addSeries(ds6);         
            JFreeChart chart2 = ChartFactory.createXYLineChart("GMWright Plot, a<0",
                    "x", "y", dataset, PlotOrientation.VERTICAL, true, true,
                    false);
               
            ChartPanel cp = new ChartPanel(chart2);
          
            frame.getContentPane().add(cp);
            //ip.exportAsPNG(chart2, 800, 600, "C:\\Temp\\wright1.png");
        }
    });

}
 
	private static XYSeries dataset(double alpha, double beta, double x0, double xn, int npoints) {
		
		UGMWright fn=new UGMWright(alpha, beta);
	    
		fn.compute(x0, xn, npoints);
	
	    return fn.getSeries();
	}


}
