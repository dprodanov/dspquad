package test.plot.gmwright;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ijaux.quad.Utils;
import ijaux.quad.plot.UGMWright;
import static java.lang.Math.*;
 
public class GMWrightPlotGauss6 {

public static void main(String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
        @Override
		public void run() {
            JFrame frame = new JFrame("GMWright Charts");

            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            final XYSeriesCollection dataset = new XYSeriesCollection();
       
           XYSeries ds1 = dataset(-0.5, -1.5, -9.0, 9.0, 101);
           dataset.addSeries(ds1);
           XYSeries ds2 = datasetGauss( -9.0, 9.0, 101);
           dataset.addSeries(ds2);
           
            JFreeChart chart2 = ChartFactory.createXYLineChart("GMWright Plot, a=-1/2, Gaussian",
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
	
	private static XYSeries datasetGauss( double x0, double xn, int npoints) {
		
		XYSeries series=new XYSeries("Gauss ((x^4-12*x^2+12)*%e^(-x^2/4))/(16*sqrt(%pi))");
 
		double[][] data = new double[2][]; //{ {0.1, 0.2, 0.3}, {1, 2, 3} };
 		 
	    double[] xx=Utils.linspace(x0, xn, npoints);
	    data[0]=xx;
	    double[] yy=new double[xx.length];
	    final double fr=1.0/sqrt(PI)/16.0;
	    for (int i=0; i<xx.length; i++) {
	    	final double x2=xx[i]*xx[i];
    		yy[i]=fr*(x2*x2-12*x2+12)*exp(-0.25*x2);
	    	series.add(xx[i], yy[i]);
	    }
	    data[1]=yy;
		    return  series;
	}



}
