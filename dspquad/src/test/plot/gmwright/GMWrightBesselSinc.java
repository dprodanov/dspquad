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
 
public class GMWrightBesselSinc {

public static void main(String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
        @Override
		public void run() {
            JFrame frame = new JFrame("GMWright Charts");

            frame.setSize(800, 450);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           // frame.setLayout( new FlowLayout() );
            frame.setVisible(true);

            final XYSeriesCollection dataset = new XYSeriesCollection();
   
            XYSeries ds1 = datasetJ0(1.0, 1.5, -15.0, 15.0, 301);
           dataset.addSeries(ds1);
           JFreeChart chart1 = ChartFactory.createXYLineChart("GMWright Plot, a=1, Bessel J0 function",
                   "x", "y", dataset, PlotOrientation.VERTICAL, true, true,
                   false);
              
           ChartPanel cp = new ChartPanel(chart1);
        
           final XYSeriesCollection dataset2 = new XYSeriesCollection();
           XYSeries ds2 = datasetSinc(-15.0, 15.0, 301);
           dataset2.addSeries(ds2);       
           dataset.addSeries(ds2);
           frame.getContentPane().add(cp);
       
        }
    });

}
 
	private static XYSeries datasetJ0(double alpha, double beta, double x0, double xn, int npoints) {
		
		UGMWright fn=new UGMWright(alpha, beta);
		
		 double[] x=Utils.linspace(x0, xn, npoints);
		 double[] x2=new double[npoints];
		 for (int i=0; i<npoints; i++) {
			 x2[i]=-x[i]*x[i];
		 }
		
		double eps=abs(xn);
		fn.setEps(eps);
			
		fn.compute(x, x2);
		 
		 
	    return fn.getSeries();
	}
	
private static XYSeries datasetSinc( double x0, double xn, int npoints) {
		
			XYSeries series=new XYSeries("sin(2*x)/(x sqrt(pi))");
	 
			double[][] data = new double[2][]; //{ {0.1, 0.2, 0.3}, {1, 2, 3} };
	 		 
		    double[] xx=Utils.linspace(x0, xn, npoints);
		    data[0]=xx;
		    double[] yy=new double[xx.length];
		    //  sin(2*x)/(sqrt(%pi)*x)
		    double fr=1.0/sqrt(PI);
		    for (int i=0; i<xx.length; i++) {
		    	if (xx[i]==0) 
		    		yy[i]=2*fr;
		    	else 
		    		yy[i]=fr*sin(2.0*xx[i])/xx[i];
		    	series.add(xx[i], yy[i]);
		    }
		    data[1]=yy;
 		    return  series;
	}


}
