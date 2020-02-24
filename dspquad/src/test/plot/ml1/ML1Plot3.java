package test.plot.ml1;


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
import ijaux.quad.plot.UMiLe1;
 
 
public class ML1Plot3 {

public static void main(String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
        @Override
		public void run() {
            JFrame frame = new JFrame("ML1 Charts");

            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            final XYSeriesCollection dataset = new XYSeriesCollection();
       
           XYSeries ds2 = dataset(1.0, -3.0, 10.0, 101);
           dataset.addSeries(ds2);
       
           XYSeries ds3 = datasetExp1(  -3.0, 10.0, 101);
           dataset.addSeries(ds3);
                 
           XYSeries ds4 = dataset(0.975, -3.0, 10.0, 101);
           dataset.addSeries(ds4);
           
            JFreeChart chart2 = ChartFactory.createXYLineChart("ML1 Plot, a>0",
                    "x", "y", dataset, PlotOrientation.VERTICAL, true, true,
                    false);
               
            ChartPanel cp = new ChartPanel(chart2);
          
            frame.getContentPane().add(cp);
        }
    });

}
 
	private static XYSeries dataset(double alpha,  double x0, double xn, int npoints) {
		
		UMiLe1 fn=new UMiLe1(alpha);
	    
		fn.compute(x0, xn, npoints);
	
	    return fn.getSeries();
	}

private static XYSeries datasetExp1( double x0, double xn, int npoints) {
		
		XYSeries series=new XYSeries("Exp  exp(x)");
 
		double[][] data = new double[2][]; //{ {0.1, 0.2, 0.3}, {1, 2, 3} };
 		 
	    double[] xx=Utils.linspace(x0, xn, npoints);
	    data[0]=xx;
	    double[] yy=new double[xx.length];
	    for (int i=0; i<xx.length; i++) {
	    
    		yy[i]= (exp(xx[i]) );
	    	series.add(xx[i], yy[i]);
	    }
	    data[1]=yy;
		    return  series;
	}

}
