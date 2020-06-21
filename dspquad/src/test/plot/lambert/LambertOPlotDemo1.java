package test.plot.lambert;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ijaux.quad.lam.LambertO;
import ijaux.quad.lam.LambertW;
import ijaux.quad.plot.UPlotter;
import static java.lang.Math.exp;
import static java.lang.Math.log;

public class LambertOPlotDemo1 {

	public LambertOPlotDemo1() {
		// TODO Auto-generated constructor stub
	}

public static void main(String[] args) {

		
		
	    SwingUtilities.invokeLater(new Runnable() {
	        @Override
			public void run() {
	        	
	        	double x0=-5.0;
	        	double x1=20.0;
	        	
	        	int branch=-1;
	        	LambertO lw=new LambertO();    		
	        	
	        	UPlotter plotter=new UPlotter("Lambert Omega", lw);

	      
  	    		
	        	branch=0;
	        	LambertW lw2=new LambertW(branch);
	        	UPlotter plotter2=new UPlotter("Lambert W["+branch+"](exp(x)", lw2);
	        	
	        	plotter2.setTransformX(
	        			( (x) -> exp(x)	)
	        			);
	        	
	        	UPlotter plotter3=new UPlotter("x-log(x +1)", 
	        			(x) ->  (x -log(x +1.0) ) );
	        	
	            JFrame frame = new JFrame("Charts");

	            frame.setSize(600, 400);
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.setVisible(true);
	             XYSeriesCollection dataset = new XYSeriesCollection();
	          
	            XYSeries ds1 = plotter.dataset(x0, x1, 300);
	            dataset.addSeries(ds1);
	            XYSeries ds2 = plotter2.dataset(x0, x1, 300);
	            dataset.addSeries(ds2);
	            
	            XYSeries ds3 = plotter3.dataset(x0, x1, 300);
	            dataset.addSeries(ds3);
	              JFreeChart chart2 = ChartFactory.createXYLineChart("Lambert Omega",
	                    "x", "y", dataset, PlotOrientation.VERTICAL, true, true,
	                    false);
	            
	            ChartPanel cp = new ChartPanel(chart2);
	           
	            frame.getContentPane().add(cp);
	        }
	    });

	}

}
