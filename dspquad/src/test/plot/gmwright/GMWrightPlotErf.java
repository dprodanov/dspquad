package test.plot.gmwright;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ijaux.quad.Erf;
import ijaux.quad.Utils;
import ijaux.quad.plot.UGMWright;
 
public class GMWrightPlotErf {

public static void main(String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
        @Override
		public void run() {
            JFrame frame = new JFrame("GMWright Charts");

            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            final XYSeriesCollection dataset = new XYSeriesCollection();
       
           XYSeries ds1 = dataset(-0.5, 1.0, -9.0, 9.0, 101);
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
		
		XYSeries series=new XYSeries("Gauss erf(x/2)+1");
		
		Erf erf=new Erf();
 
		double[][] data = new double[2][]; 
 		 
	    double[] xx=Utils.linspace(x0, xn, npoints);
	    data[0]=xx;
	    double[] yy=new double[xx.length];
	    final double fr=1.0;
	    for (int i=0; i<xx.length; i++) {
	    	
    		yy[i]=erf.eval(0.5*xx[i])+fr;
	    	series.add(xx[i], yy[i]);
	    }
	    data[1]=yy;
		    return  series;
	}



}
