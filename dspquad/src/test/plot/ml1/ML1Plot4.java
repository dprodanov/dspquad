package test.plot.ml1;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ijaux.quad.plot.UMiLe1;
import ijaux.quad.plot.UMiLe2;
 
 
public class ML1Plot4 {

public static void main(String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
        @Override
		public void run() {
            JFrame frame = new JFrame("ML1 Charts");

            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            final XYSeriesCollection dataset = new XYSeriesCollection();
       
           XYSeries ds1 = dataset(0.5, 0, 2., 201);
           dataset.addSeries(ds1);
           XYSeries ds2 = dataset(1.0, 0, 2., 201);
           dataset.addSeries(ds2);
       
           XYSeries ds3 = dataset(2.0, 0, 6., 201);
           dataset.addSeries(ds3);
                  
            JFreeChart chart2 = ChartFactory.createXYLineChart("ML1 Plot, a>0",
                    "x", "y", dataset, PlotOrientation.VERTICAL, true, true,
                    false);
               
            ChartPanel cp = new ChartPanel(chart2);
          
            frame.getContentPane().add(cp);
        }
    });

}
 
	private static XYSeries dataset(double alpha,  double x0, double xn, int npoints) {
		
		UMiLe2 fn=new UMiLe2(alpha, 1., 0.01);
	    
		fn.compute(x0, xn, npoints);
	
	    return fn.getSeries();
	}


}
