package test.plot.gmwright;


import static java.lang.Math.abs;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ijaux.quad.Utils;
import ijaux.quad.plot.IFChart;
import ijaux.quad.plot.UGMWright;
 
 
public class GMWrightBessel0 implements IFChart {

public static void main(String[] args) {
	GMWrightBessel0 ip= new GMWrightBessel0();
    SwingUtilities.invokeLater(new Runnable() {
        @Override
		public void run() {
            JFrame frame = new JFrame("GMWright Charts");

            frame.setSize(800, 900);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           // frame.setLayout( new FlowLayout() );
            frame.setVisible(true);

            final XYSeriesCollection dataset = new XYSeriesCollection();
       
           //XYSeries ds1 = datasetJ0(1.0, 1.0, -50.0, 50.0, 301);
            XYSeries ds1 = datasetJ0(1.5, 0.5, -15.0, 15.0, 301);
           dataset.addSeries(ds1);
           JFreeChart chart1 = ChartFactory.createXYLineChart("GMWright Plot, a=3/2, b=1/2",
                   "x", "y", dataset, PlotOrientation.VERTICAL, true, true,
                   false);
              
           ChartPanel cp = new ChartPanel(chart1);
           
      
//           final XYSeriesCollection dataset2 = new XYSeriesCollection();
//           XYSeries ds2 = datasetI0(1.5, 1.5, -3.0, 3.0, 101);
//           dataset2.addSeries(ds2);       
            
            
//            JFreeChart chart2 = ChartFactory.createXYLineChart("GMWright Plot, a=1, Bessel I0 function",
//                    "x", "y", dataset2, PlotOrientation.VERTICAL, true, true,
//                    false);
               
//            ChartPanel cp2 = new ChartPanel(chart2);
         
            frame.getContentPane().add(cp);
           // frame.getContentPane().add(cp2);
            ip.exportAsPNG(chart1, 800, 600, "C:\\Temp\\gmwright15.png"); 
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
		
		double eps=(abs(xn));
		System.out.println("eps "+eps);
		fn.setEps(eps);
		fn.compute(x, x2);
		 
		 
	    return fn.getSeries();
	}
	
private static XYSeries datasetI0(double alpha, double beta, double x0, double xn, int npoints) {
		
		UGMWright fn=new UGMWright(alpha, beta);
		 double[] x=Utils.linspace(x0, xn, npoints);
		 double[] x2=new double[npoints];
		 for (int i=0; i<npoints; i++) {
			 x2[i]=x[i]*x[i];
		 }
		 
		double eps= (abs(xn));
		fn.setEps(eps);
		fn.compute(x, x2);
		 
		 
	    return fn.getSeries();
	}


}
