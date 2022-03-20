package test.plot.gamma;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jtikz.TikzGraphics2D;

import ijaux.quad.plot.ULogGamma;
 
 
public class LogGammaPlotDemo1 {

public static void main(String[] args) {
	
 
 
    SwingUtilities.invokeLater(new Runnable() {
    	
    	
        @Override
		public void run() {
        	TikzGraphics2D t = new TikzGraphics2D();
        	JFrame frame = new JFrame("Charts");

            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

    
            XYDataset ds2 = logGammaDataset(0.01, 8, 100);
            JFreeChart chart2 = ChartFactory.createXYLineChart("Log Gamma Plot",
                    "x", "y", ds2, PlotOrientation.VERTICAL, true, true,
                    false);
            
            ChartPanel cp = new ChartPanel(chart2);
           
           frame.getContentPane().add(cp);
           t.paintSync(frame);
           t.close();
            
           
        }
    });
    
}
 
	private static XYDataset logGammaDataset(double x0, double xn, int npoints) {
		
		ULogGamma gamma=new ULogGamma();
	    
		gamma.compute(x0, xn, npoints);
	
	    return gamma.getData();
	}


}
