package test.plot.gamma;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

import ijaux.quad.Utils;
import ijaux.quad.euler.Gamma;
 
 
public class GammaPlotDemo21 {

public static void main(String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
        @Override
		public void run() {
            JFrame frame = new JFrame("Charts");

            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
 
            XYDataset ds2 = gammaDataset(0.05, 5.0, 100);
            JFreeChart chart2 = ChartFactory.createXYLineChart("Gamma Plot",
                    "x", "y", ds2, PlotOrientation.VERTICAL, true, true,
                    false);
            
            ChartPanel cp = new ChartPanel(chart2);
           
            frame.getContentPane().add(cp);
        }
    });

}

	 

	
	private static XYDataset gammaDataset(double x0, double xn, int npoints) {
		
		Gamma gamma=new Gamma();
	    DefaultXYDataset ds = new DefaultXYDataset();
	
	    
	    double[][] data = new double[2][]; //{ {0.1, 0.2, 0.3}, {1, 2, 3} };
	
	    double[] xx=Utils.linspace(x0, xn, npoints);
	    data[0]=xx;
	    double[] yy=new double[xx.length];
	    
	    for (int i=0; i<xx.length; i++) {
	    	yy[i]=gamma.eval(xx[i]);
	    }
	    data[1]=yy;
	    ds.addSeries("Gamma function", data);
	
	    return ds;
	}


}
