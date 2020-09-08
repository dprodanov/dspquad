package test.plot.sir;

import static java.lang.Math.exp;
import static java.lang.Math.log;
import static java.lang.Math.sqrt;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ijaux.quad.QFunction;
import ijaux.quad.lam.LambertW;
import ijaux.quad.plot.UPlotter;
import ijaux.quad.sir.IS1;

public class IS1PlotDemo {

	public IS1PlotDemo() {
		// TODO Auto-generated constructor stub
	}

	  
	 //////////////////
	 // Approximation
	 /////////////////
//	 class Ker1 implements QFunction {
//
//		private double aa=1.0;
//		private double gg=1.0;
//		
//		public Ker1(double a, double g) {
//			gg=g;
//			aa=a;
//		}
//		
//		
//		@Override
//		public double eval(double x) {
//			double bb=gg*log(gg)-gg+aa;
//			double w=bb*exp(1.0-exp(-x*aa/(log(aa+1)))-x*aa/(log(aa+1)) );
//			return w;
//		}
//		
// 
//		
//	}
	
	LambertW ww=new   LambertW();
	
	//////////////////
	// Approximation
	/////////////////
	class Ker1 implements QFunction {

		private double aa=1.0;
		private double gg=1.0;

		public Ker1(double a, double g) {
			gg=g;
			aa=a;
		}


		@Override
		public double eval(double x) {
			double bb=gg*log(gg)-gg+aa;
			double cc=ww.eval( bb*exp(bb/gg-1)/gg)*gg;
			//double w=bb*exp(1.0-exp(-x*cc)-x*cc- cc*log(x*x+1));
			double w=bb*exp(1.0-exp(-x*cc)-x*cc );
			return w;
		}



}
	
public static void main(String[] args) {

		
	IS1PlotDemo ip=new IS1PlotDemo();
	double a=6.0;
	double g=2.0;
	//double bb=g*log(g)-g+a;
	Ker1 ker=ip.new Ker1( a, g);
		
	    SwingUtilities.invokeLater(new Runnable() {
	        @Override
			public void run() {
	        	
	        	
	        	IS1 lwa=new IS1(g, a);		
	        	//lwa.setBranch(0);
	        	
	        	UPlotter plotter=new UPlotter("IS1(g="+g+" a="+a+")", lwa);


	        	
	        		        	
	        	UPlotter plotter2=new UPlotter("Ap", ker );
	        	
	            JFrame frame = new JFrame("Charts");

	            frame.setSize(600, 400);
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.setVisible(true);
	             XYSeriesCollection dataset = new XYSeriesCollection();
	          
	            XYSeries ds1 = plotter.dataset(-3, 5, 300);
	            dataset.addSeries(ds1);
	            
	            XYSeries ds2 = plotter2.dataset(-3, 5, 300);
	            dataset.addSeries(ds2);
 
	              JFreeChart chart2 = ChartFactory.createXYLineChart("SIR",
	                    "x", "y", dataset, PlotOrientation.VERTICAL, true, true,
	                    false);
	            
	            ChartPanel cp = new ChartPanel(chart2);
	           
	            frame.getContentPane().add(cp);
	        }
	    });

	}

}
