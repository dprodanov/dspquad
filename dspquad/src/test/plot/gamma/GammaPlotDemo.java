package test.plot.gamma;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;

import ijaux.quad.plot.UGamma;
 
 
public class GammaPlotDemo {

public static void main(String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
        @Override
		public void run() {
            JFrame frame = new JFrame("Charts");

            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

    
            XYDataset ds2 = gammaDataset(0.01, 4.5, 100);
            JFreeChart chart2 = ChartFactory.createXYLineChart("Gamma Plot",
                    "x", "y", ds2, PlotOrientation.VERTICAL, true, true,
                    false);
            
            ChartPanel cp = new ChartPanel(chart2);
           
            frame.getContentPane().add(cp);
        }
    });

}
 
	private static XYDataset gammaDataset(double x0, double xn, int npoints) {
		
		UGamma gamma=new UGamma();
	    
		gamma.compute(x0, xn, npoints);
	
	    return gamma.getData();
	}


}
