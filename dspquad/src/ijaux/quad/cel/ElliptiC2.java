package ijaux.quad.cel;

import static java.lang.Math.*;

import ijaux.quad.QFunction;
 


public class ElliptiC2 implements QFunction {

	double kcc=1.0;
	double pp=1.0;
	double aa=1.0;
	double bb=1.0;
	
	double tol=1e-15;
	
	final static double PI2=PI/2.0;
	
	
	
	
	////////////////////////////

	  /**
     * Elliptic integrals: 
     *	This algorithm for the calculation of the complete elliptic
     *	integral (CEI) is presented in papers by Ronald Bulirsch,
     *	Numerical Calculation of Elliptic Integrals and Elliptic Functions, 
     *  Numerische Mathematik 7,78-90 (1965) and 
     *  Ronald Bulirsch: Numerical Calculation of Elliptic Integrals and Elliptic Functions III,
     *	Numerische Mathematik 13,305-315 (1969). 
     *  https://dlmf.nist.gov/19.2 
     *     cel(k_c, p, a, b)= 
     *     \int_{0}^{\pi/2} \frac{a \cos^2 {\theta}+ b \sin^2{\theta}}{\cos^2{\theta}+ p \sin^2{\theta}}
     *      \frac{d \theta}{\sqrt{\cos^2{\theta}  +  k^2_c \sin^2{\theta}}}
     *
     */
	
    protected double cel(double kcc, double pp, double aa, double bb) {
        double  kc=kcc, p=pp, a=aa, b=bb, e, m, f, q, g;
 
        if ( kc == 0.0 )  
        	return 0;
        	
        kc = abs(kc);
    	e = kc;
    	m = 1.0;   	
    	if (p > 0.0)    	{
    		p = sqrt(p);
    		b = b/p;
    	}  else {
    		f = kc*kc; 
    		q = 1.0-f;
    		g = 1.0-p;
    		f = f-p;
    		q = q*(b-a*p);
    		p = sqrt(f/g);
    		a = (a-b)/g;
    		b = -q/(p*g*g) + a*p;
    	}

    	f = a;
    	a = b/p + a;
    	g = e/p;
    	b = 2.0*(f*g + b);
    	p = p + g;
    	g = m;
    	m = m + kc;
    	
    	while (abs(g - kc) > g*tol) {
    		kc = 2.0*sqrt(e);
    		e = kc*m;
    		f = a;
    		a = b/p + a;
    		g = e/p;
    		b = 2.0*(f*g + b);
    		p = p + g;
    		g = m;
    		m = m + kc;
    	}
    	
    	return PI2*(a*m + b)/(m*(m + p));
        	
        
    }
    
    
	public ElliptiC2(double p, double a, double b) {
		aa=a;
		bb=b;
		pp=p;
	}
	
	public ElliptiC2(double p, double a, double b, double ttol) {
		aa=a;
		bb=b;
		pp=p;
		tol=ttol;
	}
	
	public void setParams(double p, double a, double b ) {
		aa=a;
		bb=b;
		pp=p;
	}

	/* the argument corresponds to kc
	 * (non-Javadoc)
	 * @see ijaux.quad.QFunction#eval(double)
	 */
	@Override
	public double eval(double kc) {
		kcc=kc;
		return cel(kc, pp, aa, bb);
	}

	public static void main(String[] args) {
		ElliptiC2 ec=new ElliptiC2(1.0, 1.0, 1.0);
		
		double ret= ec.eval(1.0);
		
		System.out.println (" C(1,1,1,1)="+ ret+"\t, "+PI2);
		ec.setParams(1.0, 1.0, 1.0 );
		ret=ec.cel (-0.5, 1.0,1.0, 1.0);
		
		System.out.println (" C(-0.5,1,1,1)="+ ret+"\t, ");
		
		ret= ec.eval(-.5);
		System.out.println (" C(-0.5,1,1,1)="+ ret+"\t, ");
	}

}
