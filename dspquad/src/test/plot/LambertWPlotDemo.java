package test.plot;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ijaux.quad.QFunction;
import ijaux.quad.misc.LambertW;
import ijaux.quad.misc.LambertW2;
import ijaux.quad.plot.UPlotter;

public class LambertWPlotDemo {

	public LambertWPlotDemo() {
		// TODO Auto-generated constructor stub
	}

public static void main(String[] args) {

		
		
	    SwingUtilities.invokeLater(new Runnable() {
	        @Override
			public void run() {
	        	int branch=-1;
	        	LambertW lw=new LambertW(branch);    		
	        	
	        	UPlotter plotter=new UPlotter("Lambert W["+branch+"]", lw);

  	    		
	        	branch=0;
	        	LambertW lw2=new LambertW(branch);
	        	UPlotter plotter2=new UPlotter("Lambert W["+branch+"]", lw2);
	        	
	            JFrame frame = new JFrame("Charts");

	            frame.setSize(600, 400);
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.setVisible(true);
	             XYSeriesCollection dataset = new XYSeriesCollection();
	          
	            XYSeries ds1 = plotter.dataset(-0.4, 0, 300);
	            dataset.addSeries(ds1);
	            XYSeries ds2 = plotter2.dataset(-0.4, 0, 300);
	            dataset.addSeries(ds2);
	              JFreeChart chart2 = ChartFactory.createXYLineChart("Lambert W",
	                    "x", "y", dataset, PlotOrientation.VERTICAL, true, true,
	                    false);
	            
	            ChartPanel cp = new ChartPanel(chart2);
	           
	            frame.getContentPane().add(cp);
	        }
	    });

	}

}
