package test;
 

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
        double[] mu = new double[nd];
        double[] aArray = new double[nd];
        double[] cArray = new double[nd];
        double[] bArray = new double[nd];
        
        mu[0]=m;
        aArray[0]=1.0;
        cArray[0]=sqrt(m);
        bArray[0]=sqrt(1 - m);
       
 

        int i = 0;
        while (abs(cArray[i]) >= tol && i< nd ) {
  
            aArray[i+1] = 0.5 * (aArray[i] + bArray[i]);
            bArray[i+1] = sqrt  (aArray[i] * bArray[i]);
            cArray[i+1] = 0.5 * (aArray[i] - bArray[i]);          
            i++;

        }
 
        int n=i; 
      //  System.out.println(n+" "+ cArray[n]);
    
       // System.out.println( Arrays.toString(aArray));
        //System.out.println( Arrays.toString(bArray));
      //  System.out.println( Arrays.toString(cArray));
        
        double phi = pow(2, n) * aArray[n] * u;
       
        for (i=n; i>0; i--) {
        	// System.out.println(phi + " " +i);
             phi = 0.5 * (asin(cArray[i]/ aArray[i] * sin(phi) ) + phi);
            	
        }
        //System.out.println(i);
        
        result[0] = sin(phi);
        result[1] = cos(phi);
        result[2] = sqrt(1 - m * pow(sin(phi), 2));
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
  