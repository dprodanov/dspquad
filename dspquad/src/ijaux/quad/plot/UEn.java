package ijaux.quad.plot;

import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;

import ijaux.quad.En;
import ijaux.quad.Utils;

public class UEn extends En implements UIFunction {

	private String title= "exp(x)";
	DefaultXYDataset udata =new DefaultXYDataset();
	XYSeries series=null;
	
	public UEn(int nn) {
		super(nn);
		title=title+" -T_"+nn+"(x)";
		series=new XYSeries(title);
	}
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
	    data[0]=points;
	    double[] yy=new double[points.length];
	    
	    for (int i=0; i<dpoints.length; i++) {
	    	yy[i]=eval(points[i]);
	    	series.add(dpoints[i], yy[i]);
	    }
	    data[1]=yy;
	    udata.addSeries(title, data);
		
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
