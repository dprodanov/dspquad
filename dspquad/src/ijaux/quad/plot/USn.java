package ijaux.quad.plot;

import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;

import ijaux.quad.Utils;
import ijaux.quad.cel.Sn;

public class USn extends Sn implements UIFunction {

	public USn(double aa) {
		super(aa);
		title=title+"("+aa +")";
		series=new XYSeries(title);
		udata = new DefaultXYDataset();
	}

	private String title="sn";
	
	DefaultXYDataset udata =null;
	XYSeries series=null;
	
	@Override
	public void compute(double x0, double xn, int npoints) {
			 
	    double[][] data = new double[2][]; 	
	    double[] xx=Utils.linspace(x0, xn, npoints);
	    data[0]=xx;
	    double[] yy=new double[xx.length];
	    
	    for (int i=0; i<xx.length; i++) {
	    	yy[i]=eval(xx[i]);
	    	series.add(xx[i], yy[i]);
	    }
	    data[1]=yy;
	   
	    udata.addSeries(title, data);
	}
	
	@Override
	public void compute(double[] dpoints, double[] points) {
		double[][] data = new double[2][]; 	
		data[0]=dpoints;
		double[] yy=new double[points.length];
	    
	    for (int i=0; i<points.length; i++) {
	    	yy[i]=eval(points[i]);
	    	series.add(dpoints[i], yy[i]);
	    }
	    data[1]=yy;  
	    //udata.addSeries(title, data);
		
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



}
