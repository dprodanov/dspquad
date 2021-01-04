package test.plot.sir;

import static java.lang.Math.log;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ijaux.quad.QFunction;
import ijaux.quad.lam.LambertO;
import ijaux.quad.lam.LambertW;
import ijaux.quad.lam.LambertW2;
import ijaux.quad.plot.IFChart;
import ijaux.quad.plot.UPlotter;
import ijaux.quad.sir.InvI;
import ijaux.quad.sir.InvI2;
import ijaux.quad.sir.InvI3;

public class InvIDemo4 implements IFChart {

	public InvIDemo4() {
		// TODO Auto-generated constructor stub
	}

public static void main(String[] args) {

	InvIDemo4 ip = new InvIDemo4();
		
	    SwingUtilities.invokeLater(new Runnable() {
	        @Override
			public void run() {
	        	
	        	double a=5.0;
	        	double g=2.0;
	        	double bb=g*log(g)-g+a;
	        	
	        	InvI3 lwa=new InvI3(g, a );		
	        	int branch=-1;
	        	lwa.setBranch(branch);
	        	
	        	UPlotter plotter=new UPlotter("IS["+branch+"]", lwa);

	 	        	
	        	
	            JFrame frame = new JFrame("Charts");

	            frame.setSize(600, 400);
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.setVisible(true);
	             XYSeriesCollection dataset = new XYSeriesCollection();
	          
	            XYSeries ds1 = plotter.dataset(0.005, bb, 150);
	            dataset.addSeries(ds1);
	            
	            branch=0;
	            lwa.setBranch(branch);
	        	UPlotter plotter2=new UPlotter("IS["+branch+"]", lwa);
	        	 
  	            
	            XYSeries ds2 = plotter2.dataset(0.005, bb, 150);
	            dataset.addSeries(ds2);
 
	              JFreeChart chart2 = ChartFactory.createXYLineChart("SIR, Representation 2",
	                    "x", "y", dataset, PlotOrientation.VERTICAL, true, true,
	                    false);
	            
	            ChartPanel cp = new ChartPanel(chart2);
	           
	            frame.getContentPane().add(cp);
	            
	            ip.exportAsPNG(chart2, 800, 600, "C:\\Temp\\chart2.png");  
	        }
	    });

	}

}
