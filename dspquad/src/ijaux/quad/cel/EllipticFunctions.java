package ijaux.quad.cel;
 
/**
 *  (C) Dimiter Prodanov 2024
 *   Arithmetic-Geometric Mean algorithm for Elliptic functions
 *  https://dlmf.nist.gov/22.20
 * based in part on 
 * https://github.com/moiseevigor/elliptic
 * https://mathoverflow.net/questions/34363/simultaneously-computing-a-complete-elliptic-integral-and-its-complement
 */

import static java.lang.Math.sin; 
import static java.lang.Math.asin; 
import static java.lang.Math.cos; 
import static java.lang.Math.cosh; 
import static java.lang.Math.tanh; 
import static java.lang.Math.sqrt; 
import static java.lang.Math.pow; 
import static java.lang.Math.abs; 
import static java.lang.Math.min; 
import static java.lang.Math.log; 
import static java.lang.Math.tan;
import static java.lang.Math.atan; 
import static java.lang.Math.PI; 

public class EllipticFunctions {

	/**
     * 
     * @param u
     * @param m
     * @return 0 - sn; 1 - cn; 2 - dn; 3 - am
     */
    public static double[] ellipj(double u, double m) {
        return ellipj(u, m, Math.ulp(1.0));
    }

    /**
     * 
     * @param u
     * @param m
     * @param tol
     * @return 0 - sn; 1 - cn; 2 - dn; 3 - am
     */
    public static double[] ellipj(double u, double m, double tol) {
        double[] result = new double[4];

        double currentU = u;
        double currentM = m;

        if (currentM != 1 && currentM != 0) {
            result = compute(currentU, currentM, tol);
        } else if (currentM == 0) {
            result[3] = currentU;
            result[0] = sin(currentU);
            result[1] = cos(currentU);
            result[2] = 1.;
            result[4] = .5*Math.PI;
            result[5] = .5*Math.PI;
        } else { // currentM == 1
            result[3] = asin(tanh(currentU));
            result[0] = tanh(currentU);
            result[1] = 1. / cosh(currentU);
            result[2] = 1. / cosh(currentU);
            result[4] = 0;
            result[5] = 1.;
        }

        return result;
    }

    /**
     * 
     * @param u
     * @param m
     * @param tol
     * @return
     */
    private static double[] compute(double u, double m, double tol) {
    	
        double[] result = new double[8];
        int nd=(int) min( (-log(tol)/log(2.)/2.), 16);
  
        double[] a = new double[nd];
        double[] c = new double[nd];
        double[] b = new double[nd];
        
        a[0]=1.;
        c[0]=sqrt(m);
        b[0]=sqrt(1. - m);

        int i = 0;

        double s=0;
        double phin=abs(u);
        double Cp=0;  
        double sgn = sgn(u);
        
        int f=1;
        // AGM
        nd--;
        while (abs(c[i]) >= tol && i< nd ) {
            a[i+1] = 0.5 * (a[i] + b[i]);  // a         
            b[i+1] = sqrt  (a[i] * b[i]); // g
            c[i+1] = 0.5 * (a[i] - b[i]);  //c
             
            s += f * c[i] * c[i];
            phin += atan(b[i]/a[i]*tan(phin) ) + PI*Math.ceil(phin/PI - 0.5) ;
            Cp+=c[i+1]*sin(phin ); 
            
            f *= 2;   
            i++;
        }
      
        int n=i; 
        if (n==nd) n--;
    
        double phi = f * a[n] * u;   
        for (i=n; i>0; i--) {
             phi = 0.5 * (asin(c[i]/ a[i] * sin(phi) ) + phi);
        }
        
        //System.out.println(i);        
        result[0] = sin(phi); //sn
        result[1] = cos(phi); //cn
        result[2] = sqrt(1 - m * result[0]*result[0]); //dn
        result[3] = phi; // am
        result[4] = 0.5*PI/b[n];  //K
        result[5] = result[4]*(a[0]*a[0]- .5*s); //E
        
 
        double Ff = phin / (a[n]*f);         
          
        result[6] = Ff*sgn;                  // F
        result[7] = Cp*sgn + (1. - .5*s) *result[6]; // E1
        
        return result;
    }

	/**
	 * @param u
	 * @return
	 */
	private static double sgn(double u) {
		double sgn=1.;
        if (u<0) sgn=-1.;
		return sgn;
	}

	/**
	 * 
	 * @param m
	 * @param tol
	 * @return
	 */
    public static double[] ef2(double m,  double tol) {
        double a = (1. + sqrt(1. - m)) / 2.;
        double c = m / (4. * a); // c_{n+1}= (a_n-g_n)/2 = c_n^2/(4 a_{n+1}
      //  double t = Math.log(c / (4. * a));
        double s = a * a;
        double f = 1.;
        double v;
        //System.out.println (s+" "+ d);
        
        while (abs(c) >= tol) {
            v = (a + sqrt((a - c) * (a + c))) / 2.;
        //    t += Math.log(a / v) / f;
            a = v;
            c = (c * c) / (4. * a);
            f *= 2.;
            s -= f * c * c;
          //System.out.println ("v "+a+ " c "+c+" s "+s);
        }

        double K = PI / (2. * a);
        double E = K *s;
       // System.out.println(E);
        return new double[]{K, E};
    }

    
    public static void main(String[] args) {
        double u = 0.5;
        double m = 0.3;
        double tol = 1e-16;
        //System.out.println(tol);
        
        double[] result = ellipj(u, m, tol);
        System.out.println("SN: " + result[0]);
        System.out.println("CN: " + result[1]);
        System.out.println("DN: " + result[2]);
        System.out.println("AM: " + result[3]);
        System.out.println("K: " + result[4]);
        System.out.println("E: " + result[5]);
        System.out.println("F: " + result[6]);
        System.out.println("E1: " + result[7]);
        
       // result = ef2(m, tol);
        
        System.out.println("negative argument");
        u=-u;
        result = ellipj(u, m, tol);
        System.out.println("SN: " + result[0]);
        System.out.println("CN: " + result[1]);
        System.out.println("DN: " + result[2]);
        System.out.println("AM: " + result[3]);
        System.out.println("K: " + result[4]);
        System.out.println("E: " + result[5]);
        System.out.println("F: " + result[6]);
        System.out.println("E1: " + result[7]);
        
    }
}
  