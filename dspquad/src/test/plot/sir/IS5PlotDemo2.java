package test.plot.sir;

import static java.lang.Math.E;
import static java.lang.Math.cosh;
import static java.lang.Math.exp;
import static java.lang.Math.log;
import static java.lang.Math.sinh;
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
import ijaux.quad.sir.ISR;

public class IS5PlotDemo2 {

	public IS5PlotDemo2() {
		// TODO Auto-generated constructor stub
	}
  
	
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

		private final double c1=Math.cosh(1);
		private final double s1=Math.sinh(1);
		
		private final double aq=c1*c1*c1/s1;

		
		@Override
		public double eval(double x) {
			double bb=gg*log(gg)-gg+aa;
//			//double cc=ww.eval( bb*exp(bb/gg-1)/gg)*gg;
//			//double w=bb*exp(1.0-exp(-x*cc)-x*cc- cc*log(x*x+1));
//			double cc=1.0;
//			cc=sqrt(gg*(2.0*bb));
//			if (gg<1) cc=sqrt(E*gg*(2.0*bb));
//			//double cc=sqrt((2.0*bb)/gg);
//			//if (x>0) cc=cc/sqrt(2.0);
//			if (x>0) cc=cc/sqrt(E);
//			double w=bb*exp(1.0-exp(-x*cc)-x*cc );
//			return w;
		 
			double cc=ww.eval( aa*log(aa+1))*gg/2;
			double  d=cosh(exp(-cc*x));
			double  w= aq*(exp(-cc*x)*sinh(exp(-cc*x)))/ (d*d*d);
			return bb*w;
		}



}
	
public static void main(String[] args) {

		
	IS5PlotDemo2 ip=new IS5PlotDemo2();
	double a=5.0;
	double g=2;
	//double bb=g*log(g)-g+a;
	Ker1 ker=ip.new Ker1( a, g);
		
	    SwingUtilities.invokeLater(new Runnable() {
	        @Override
			public void run() {
	        	
	        	int npoints=400;
	        	
	        	double x0=-1.0, x1=2.0;
	  
	        	
	        	ISR lwa=new ISR(g, a);		
	        	//lwa.setBranch(0);
	        	
	        	UPlotter plotter=new UPlotter("IS5("+g+","+a+")", lwa);


	        	
	        		        	
	        	UPlotter plotter2=new UPlotter("Ap", ker );
	        	
	            JFrame frame = new JFrame("Charts");

	            frame.setSize(600, 400);
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.setVisible(true);
	             XYSeriesCollection dataset = new XYSeriesCollection();
	          
	            XYSeries ds1 = plotter.dataset(x0,x1, npoints);
	            dataset.addSeries(ds1);
	            
	            XYSeries ds2 = plotter2.dataset(x0, x1, npoints);
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
