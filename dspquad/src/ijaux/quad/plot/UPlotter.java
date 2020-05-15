package ijaux.quad.plot;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ijaux.quad.QFunction;
import ijaux.quad.Utils;

/*
 * class for plotting  functions
 */
public class UPlotter implements UIFunction {

	private String title=null;
	private DefaultXYDataset udata =new DefaultXYDataset();
	private XYSeries series=null;
	
	private QFunction fn=null, fnx=null,fny=null;
	
	public UPlotter(String name) {
		title=name;
	}
	
	public UPlotter(String name, QFunction qf) {
		title=name;
		fn=qf;
		series=new XYSeries(title);
	}
	
	public UPlotter(String name, QFunction qf, QFunction transf) {
		title=name;
		fn=qf;
		fnx=transf;
		series=new XYSeries(title);
	}
	
	public void setFunction(QFunction qf) {
		fn=qf;
	}
	
	public void setTransformX(QFunction transf) {
		fnx=transf;
	}
	
	public void setTransformY(QFunction transf) {
		fny=transf;
	}
	
	public XYSeries dataset( double x0, double xn, int npoints) {
		if (fnx==null) {
			compute(x0, xn, npoints);		
		}else {
			double[] dpoints=transformX(x0, xn, npoints);
			double[] x=Utils.linspace(x0, xn, npoints);
			compute(x, dpoints);
		}
		    return getSeries();
	}
	
	public XYSeries dataset( double x0, double xn, int npoints, QFunction transf) {
		fnx=transf;
		double[] dpoints=transformX(   x0,   xn,   npoints);
		double[] x=Utils.linspace(x0, xn, npoints);
		compute(x, dpoints);		
	    return getSeries();
	}

	@Override
	public void compute(double x0, double xn, int npoints) {
		double[][] data = new double[2][]; 
	    double[] xx=Utils.linspace(x0, xn, npoints);
	    data[0]=xx;
	    double[] yy=new double[xx.length];
	    if (fny!=null) {
	    	 for (int i=0; i<xx.length; i++) {
			    yy[i]=fny.eval(fn.eval(xx[i]));
			    series.add(xx[i], yy[i]);
	    	 }
	    } else
		    for (int i=0; i<xx.length; i++) {
		    	yy[i]=fn.eval(xx[i]);
		    	series.add(xx[i], yy[i]);
		    }
	    data[1]=yy;
	    udata.addSeries(title, data);
		
	}

	@Override
	public void compute(double[] dpoints, double[] points) {
		double[][] data = new double[2][]; 
	    data[0]=points;
	    double[] yy=new double[points.length];
	    if (fny!=null) {
	    	for (int i=0; i<dpoints.length; i++) {
		    	yy[i]=fny.eval(fn.eval(points[i]));
		    	series.add(dpoints[i], yy[i]);
		    }
	    } else
		    for (int i=0; i<dpoints.length; i++) {
		    	yy[i]=fn.eval(points[i]);
		    	series.add(dpoints[i], yy[i]);
		    }
	    data[1]=yy;
	    udata.addSeries(title, data);
		
	}
	
	public double[]  transformX( double x0, double xn, int npoints) {
  		 double[] x=Utils.linspace(x0, xn, npoints);
		 double[] x2=new double[npoints];
		 for (int i=0; i<npoints; i++) {
			 x2[i]=fnx.eval(x[i]);
		 }
		 return x2;
	}
	


	@Override
	public void setTitle(String title) {
		this.title=title;
	}

	@Override
	public String getTile() {
		return title;
	}

	@Override
	public XYDataset getData() {
		return udata;
	}

	@Override
	public XYSeries getSeries() {
		return series;
	} 
	
	
public static void main(String[] args) {

		
		
	    SwingUtilities.invokeLater(new Runnable() {
	        @Override
			public void run() {
	        	// lambda construct
	        	QFunction Lambda = (x) -> 0.5*x;
	        	
	        	UPlotter plotter=new UPlotter("x^2 /2", Lambda);
	        	// lambda transform
	        	plotter.setTransformX(
	        			( (x) -> x*x	)
	        			);
	            JFrame frame = new JFrame("Charts");

	            frame.setSize(600, 400);
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.setVisible(true);
	             XYSeriesCollection dataset = new XYSeriesCollection();
	          
	            XYSeries ds1 = plotter.dataset(-5, 5, 300);
	            dataset.addSeries(ds1);
	              JFreeChart chart2 = ChartFactory.createXYLineChart("x^2",
	                    "x", "y", dataset, PlotOrientation.VERTICAL, true, true,
	                    false);
	            
	            ChartPanel cp = new ChartPanel(chart2);
	           
	            frame.getContentPane().add(cp);
	        }
	    });

	}

}
