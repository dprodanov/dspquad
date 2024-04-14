package ijaux.quad.cel;
 
/**
 *  (C) Dimiter Prodanov 2024
 *   Arithmetic-Geometric Mean algorithm for Elliptic functions
 *  https://dlmf.nist.gov/22.20
 * based in part on 
 * https://github.com/moiseevigor/elliptic
 */

import static java.lang.Math.sin; 
import static java.lang.Math.asin; 
import static java.lang.Math.cos; 
import static java.lang.Math.cosh; 
import static java.lang.Math.tanh; 
import static java.lang.Math.sqrt; 
import static java.lang.Math.pow; 
import static java.lang.Math.abs; 

public class EllipticFunctions {

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
            result[2] = 1.0;
        } else { // currentM == 1
            result[3] = asin(tanh(currentU));
            result[0] = tanh(currentU);
            result[1] = 1. / cosh(currentU);
            result[2] = 1. / cosh(currentU);
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
        double[] result = new double[4];
        //double a, b, c;
        int nd=(int) Math.min( (-Math.log(tol)/Math.log(2)/2.0), 8);
        //System.out.println(nd);

        double[] a = new double[nd];
        double[] c = new double[nd];
        double[] b = new double[nd];
        
        a[0]=1.;
        c[0]=sqrt(m);
        b[0]=sqrt(1. - m);
       

        int i = 0;
        // AGM
        while (abs(c[i]) >= tol && i< nd ) {
            a[i+1] = 0.5 * (a[i] + b[i]);
            b[i+1] = sqrt  (a[i] * b[i]);
            c[i+1] = 0.5 * (a[i] - b[i]);          
            i++;

        }
 
        int n=i; 
      
        
        double phi = pow(2, n) * a[n] * u;
       
        for (i=n; i>0; i--) {
        	// System.out.println(phi + " " +i);
             phi = 0.5 * (asin(c[i]/ a[i] * sin(phi) ) + phi);
            	
        }
        //System.out.println(i);
        
        result[0] = sin(phi); //sn
        result[1] = cos(phi); //cd
        result[2] = sqrt(1 - m * result[0]*result[0]); //dn
        result[3] = phi;

        return result;
    }

    
    public static void main(String[] args) {
        double u = 0.5;
        double m = 0.3;
        double tol = 1e-16;// Math.ulp(1.0);
        System.out.println(tol);
        
        double[] result = ellipj(u, m, tol);
        System.out.println("SN: " + result[0]);
        System.out.println("CN: " + result[1]);
        System.out.println("DN: " + result[2]);
        System.out.println("AM: " + result[3]);
    }
}
  