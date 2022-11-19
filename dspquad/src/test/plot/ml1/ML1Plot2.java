package test.plot.ml1;


import static java.lang.Math.*;

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
import ijaux.quad.plot.UMiLe2;
 
 
public class ML1Plot2 {

public static void main(String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
        @Override
		public void run() {
            JFrame frame = new JFrame("ML2 Charts");

            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            final XYSeriesCollection dataset = new XYSeriesCollection();

           XYSeries ds2 = dataset(2., 1., 0, 6.28, 101);
           dataset.addSeries(ds2);
       
           XYSeries ds3 = datasetExp1(  0, 6.28, 101);
           dataset.addSeries(ds3);
                  
            JFreeChart chart2 = ChartFactory.createXYLineChart("ML2 Plot, a>0",
                    "x", "y", dataset, PlotOrientation.VERTICAL, true, true,
                    false);
               
            ChartPanel cp = new ChartPanel(chart2);
          
            frame.getContentPane().add(cp);
        }
    });

}
 
	private static XYSeries dataset(double alpha, double beta, double x0, double xn, int npoints) {
		
		UMiLe2 fn=new UMiLe2(alpha, beta, 0.1);
	    
		
		 double[] x=Utils.linspace(x0, xn, npoints);
		 /*
		 double[] x3=new double[npoints];
		 for (int i=0; i<npoints; i++) {
			 x3[i]=x[i]*x[i]*x[i];
		 }
		*/
		//double eps=abs(xn);
		//fn.setEps(eps);
			
		fn.compute(x, x);
	
	    return fn.getSeries();
	}

private static XYSeries datasetExp1( double x0, double xn, int npoints) {
		
		//XYSeries series=new XYSeries(" (2*e^(-x/2)*cos((sqrt(3)*x)/2-(4*pi)/3)+e^x)/(3*x^2)");
	XYSeries series=new XYSeries("cos(x)");
		double[][] data = new double[2][]; //{ {0.1, 0.2, 0.3}, {1, 2, 3} };
 		 
	    double[] xx=Utils.linspace(x0, xn, npoints);
	    data[0]=xx;
	    double[] yy=new double[xx.length];
	    for (int i=0; i<xx.length; i++) {
	    	/*if (xx[i]==0)
	    		yy[i]=0.5;
	    	else
	    		yy[i]=(2.0*exp(-xx[i]/2)*cos((sqrt(3)*xx[i])/2.0-(4.0*PI)/3.0)+exp(xx[i]))/(3.0*xx[i]*xx[i]) ;*/
	    	yy[i]=cos(xx[i]);
	    	series.add(xx[i], yy[i]);
	    }
	    data[1]=yy;
		    return  series;
	}

}
