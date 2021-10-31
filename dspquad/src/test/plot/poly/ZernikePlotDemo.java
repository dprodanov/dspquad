package test.plot.poly;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ijaux.quad.Utils;
import ijaux.quad.plot.UZernike;
 
 
public class ZernikePlotDemo {

public static void main(String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
        @Override
		public void run() {
            JFrame frame = new JFrame("Charts");

            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            
            final XYSeriesCollection dataset = new XYSeriesCollection();
            
            XYSeries ds1 = ZernikePoly(0, 1, 150, 2, 0 );
            dataset.addSeries(ds1);
           
            XYSeries ds2 = datasetZ20(0, 1, 150);
            dataset.addSeries(ds2);

            XYSeries ds3 = ZernikePoly(0, 1, 150, 4, 0 );
            dataset.addSeries(ds3);
            
            XYSeries ds4 = datasetZ40(0, 1, 150);
            dataset.addSeries(ds4);
            
            XYSeries ds5 = ZernikePoly(0, 1, 150, 4, 2 );
            dataset.addSeries(ds5);
            
            XYSeries ds6 = datasetZ42(0, 1, 150);
            dataset.addSeries(ds6);
     
            JFreeChart chart2 = ChartFactory.createXYLineChart("Zernike Plot",
                    "x", "y", dataset, PlotOrientation.VERTICAL, true, true,
                    false);
            
            ChartPanel cp = new ChartPanel(chart2);
           
            frame.getContentPane().add(cp);
        }
    });

}
 
	private static XYSeries ZernikePoly(double x0, double xn, int npoints, int n, int m) {
		
		UZernike fn=new UZernike(n, m);
	    
		fn.compute(x0, xn, npoints);
	
	    return fn.getSeries();
	}
	
	
	private static XYSeries datasetZ40( double x0, double xn, int npoints) {
		
		XYSeries series=new XYSeries("6*r^4-6*r^2+1");
 
		double[][] data = new double[2][]; //{ {0.1, 0.2, 0.3}, {1, 2, 3} };
 		 
	    double[] xx=Utils.linspace(x0, xn, npoints);
	    data[0]=xx;
	    double[] yy=new double[xx.length];
	    for (int i=0; i<xx.length; i++) {
	    	double r2=xx[i]*xx[i];
    		yy[i]= 6.*r2*r2-6.*r2+1. ;
	    	series.add(xx[i], yy[i]);
	    }
	    data[1]=yy;
		    return  series;
	}
	
private static XYSeries datasetZ42( double x0, double xn, int npoints) {
		
		XYSeries series=new XYSeries("4*r^4-3*r^2");
 
		double[][] data = new double[2][]; //{ {0.1, 0.2, 0.3}, {1, 2, 3} };
 		 
	    double[] xx=Utils.linspace(x0, xn, npoints);
	    data[0]=xx;
	    double[] yy=new double[xx.length];
	    for (int i=0; i<xx.length; i++) {
	    	double r2=xx[i]*xx[i];
    		yy[i]= 4.*r2*r2-3.*r2 ;
	    	series.add(xx[i], yy[i]);
	    }
	    data[1]=yy;
		    return  series;
	}
	
	private static XYSeries datasetZ20( double x0, double xn, int npoints) {
		
		XYSeries series=new XYSeries("2*r^2-1");
 
		double[][] data = new double[2][]; //{ {0.1, 0.2, 0.3}, {1, 2, 3} };
 		 
	    double[] xx=Utils.linspace(x0, xn, npoints);
	    data[0]=xx;
	    double[] yy=new double[xx.length];
	    for (int i=0; i<xx.length; i++) {
	    	double r2=xx[i]*xx[i];
    		yy[i]= 2.*r2- 1. ;
	    	series.add(xx[i], yy[i]);
	    }
	    data[1]=yy;
		    return  series;
	}


}
