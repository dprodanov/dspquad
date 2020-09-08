package ijaux.quad.plot;

import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;

import ijaux.quad.Utils;
import ijaux.quad.wright.GMWright;

public class UGMWright extends GMWright implements UIFunction {

	private String title="GWright ";
	
	private DefaultXYDataset udata =null;
	private XYSeries series=null;
	
	private double[][] data=new double[2][];
	
	public UGMWright(double aa, double bb) {
		super(aa, bb);
		title=title+" ("+aa +", "+bb+")";
		series=new XYSeries(title);
		udata = new DefaultXYDataset();
	}

	
	@Override
	public void compute(double x0, double xn, int npoints) {
			 	
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
