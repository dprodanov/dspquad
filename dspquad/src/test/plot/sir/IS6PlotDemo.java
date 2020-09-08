package test.plot.sir;

import static java.lang.Math.exp;
import static java.lang.Math.log;
import static java.lang.Math.sqrt;
import static java.lang.Math.cosh;
import static java.lang.Math.sinh;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ijaux.quad.QFunction;
import ijaux.quad.lam.LambertW;
import ijaux.quad.plot.IFChart;
import ijaux.quad.plot.UIFunction;
import ijaux.quad.plot.UPlotter;
import ijaux.quad.sir.IS1;
import ijaux.quad.sir.IS2;
import ijaux.quad.sir.IS6;
import ijaux.quad.sir.ISR;
import ijaux.quad.sir.RIS;

public class IS6PlotDemo implements IFChart {

	public IS6PlotDemo() {
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
		
		private final double a=c1*c1*c1/s1;

		@Override
		public double eval(double x) {
			double bb=gg*log(gg)-gg+aa;
			
			double cc=ww.eval( aa*log(aa+1))*gg/2;
			double d=cosh(exp(-cc*x));
			double w= a*(exp(-cc*x)*sinh(exp(-cc*x)))/ (d*d*d);
			return bb*w;
		}
		
		/*
		private double approx(double x, double bb) {
			//double cc=lw_plus.eval( a*log(a+1))*g;		
			//double cc=lw_plus.eval( a*exp(-a/g))*g;
			double cc=lw_plus.eval( bb*exp(bb/g-1)/g)*g;
			double w=bb*exp(1.0-exp(-x*cc)-x*cc);
			return w;
		}
		 */

	}
	
public static void main(String[] args) {

		
	IS6PlotDemo ip=new IS6PlotDemo();
	double a=6.0;
	double g=2.0;
	double bb=g*log(g)-g+a;
	
	//LambertW ww=new   LambertW();
	//double cc=ww.eval( (bb+1)*exp(bb/g+1)/g)/g;
	double cc=(g+bb)/g;
	System.out.println("bb: " +bb+ " cc "+cc);
	
	Ker1 ker=ip.new Ker1( a, g);
		
	 
			SwingUtilities.invokeLater(new Runnable() {
			    @Override
				public void run() {
			    	
			    	
			    	IS6 lwa=new IS6(g, a );		
	
			    	UPlotter plotter=new UPlotter("IS6(g="+g+" a="+a+")", lwa);
				
			    	ISR lwa2=new ISR(g, a );		
			    	//lwa.setBranch(0);
			    	
			    	UPlotter plotter3=new UPlotter("IS5(g="+g+" a="+a+")", lwa2);
			    	
			    		        	
			    	UPlotter plotter2=new UPlotter("Ap", ker );
			    	
			        JFrame frame = new JFrame("Charts");

			        frame.setSize(600, 400);
			        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			        frame.setVisible(true);
			         XYSeriesCollection dataset = new XYSeriesCollection();
			      
			        XYSeries ds1 = plotter.dataset(-3, 8, 350);
			        dataset.addSeries(ds1);
			        
			        XYSeries ds2 = plotter2.dataset(-3, 8, 350);
			        dataset.addSeries(ds2);
 
			        XYSeries ds3 = plotter3.dataset(-3, 8, 350);
			        dataset.addSeries(ds3);
			        
			          JFreeChart chart = ChartFactory.createXYLineChart("SIR, Representation 2",
			                "x", "y", dataset, PlotOrientation.VERTICAL, true, true,
			                false);
			        
			        ChartPanel cp = new ChartPanel(chart);
			       
			        frame.getContentPane().add(cp);
			        
					
					ip.exportAsPNG(chart, 800, 600, "C:\\Temp\\chart3.png");  
					 
			   
			    }
			});
		 

	}


}
