package test.plot.sir;

import static java.lang.Math.exp;
import static java.lang.Math.log;
import static java.lang.Math.sqrt;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ijaux.quad.QFunction;
import ijaux.quad.plot.IFChart;
import ijaux.quad.plot.UPlotter;
import ijaux.quad.sir.IS1;
import ijaux.quad.sir.IS2;
import ijaux.quad.sir.IS6;
import ijaux.quad.sir.ISR;
import ijaux.quad.sir.RIS;
import ijaux.quad.sir.SIR;

public class ISPlotDemo3 implements IFChart {

	public ISPlotDemo3() {
		// TODO Auto-generated constructor stub
	}

	 
	
public static void main(String[] args) {

		
	//ISPlotDemo3 ip=new ISPlotDemo3();
	double a=6.5;
	double g=2.0;
	ISPlotDemo3 ip = new ISPlotDemo3();
		
	    SwingUtilities.invokeLater(new Runnable() {
	        @Override
			public void run() {
	        	
	        	//ISR lwa2=new ISR(g, a );		
	        	IS6 lwa2=new IS6(g, a );
	        	RIS lwa3=new RIS(g, a );		
	        	SIR lwa=new SIR(g, a );		
	  
	        	double x0=-3.0;
	        	double x1=4.5;
	        	
	        	
	        	UPlotter plotter=new UPlotter("Susceptible", lwa);
	        		        	
	        	UPlotter plotter2=new UPlotter("Recovered", lwa3 );
	        	
	        	UPlotter plotter3=new UPlotter("Infected", lwa2);
	        	
	            JFrame frame = new JFrame("Charts");

	            frame.setSize(800, 600);
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.setVisible(true);
	             XYSeriesCollection dataset = new XYSeriesCollection();
	            
		            //I
		            XYSeries ds3 = plotter3.dataset(x0, x1, 300);
		            dataset.addSeries(ds3);
		            
		            // R
		             XYSeries ds2 = plotter2.dataset(x0, x1, 300);
		             dataset.addSeries(ds2);
		             
	             // S
		            XYSeries ds1 = plotter.dataset(x0, x1, 300);
		            dataset.addSeries(ds1);
	
		            
	   
	      
	        
	      
	        
	        
	              JFreeChart chart2 = ChartFactory.createXYLineChart("SIR Model",
	                    "time", "number", dataset, PlotOrientation.VERTICAL, true, true,
	                    false);
	              XYPlot plot= (XYPlot) chart2.getPlot();
	            plot.setBackgroundPaint(Color.WHITE);
	
	            plot.setRangeGridlinesVisible( false );
	            NumberAxis domain = (NumberAxis) plot.getDomainAxis();
	       
	            domain.setTickUnit(new NumberTickUnit(1.0));
	    
	            NumberAxis range = (NumberAxis) plot.getRangeAxis();
	        
	            range.setTickUnit(new NumberTickUnit(1.0));
	                    
	            ChartPanel cp = new ChartPanel(chart2);
	           
	            frame.getContentPane().add(cp);
	            
	            
	            
	           ip.exportAsPNG(chart2, 800, 600, "C:\\Temp\\sirchart3.png"); 
	        }
	    });

	}

}
