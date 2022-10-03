package ijaux.quad;
import static java.lang.Math.*;

 

/*
 	Courtesy to Prof. Takuya Ooura
 	Library for computation of special functions
 	and numerical integration
 	
The code is based in mostly on
 * http://www.kurims.kyoto-u.ac.jp/~ooura/intde.html
for the tanh-sinh (double exponential) method
 
 * 
 * Bibliography
 * M.Mori, Developments in the double exponential formula for numerical integration, Proceedings of the International Congress of Mathematicians, Kyoto 1990, 1991, Springer-Verlag, 1585-1594.
 * H.Takahasi and M.Mori, Double exponential formulas for numerical integration, Pub. RIMS Kyoto Univ. 9, 1974, 721-741
 * T.Ooura and M.Mori, The Double exponential formula for oscillatory functions over the half infinite interval, Journal of Computational and Applied Mathematics 38, 1991, 353-360
 * M.Mori and T.Ooura, Double exponential formulas for Fourier type integrals with a divergent integrand, Contributions in Numerical Mathematics, ed. R.P.Agarwal, World Scientific Series in Applicable Analysis, 2, 1993, 301-308
 * H.Toda and H.Ono, Some remarks for efficient usage of the double exponential formulas(in Japanese), Kokyuroku, RIMS, Kyoto Univ. 339, 1978, 74-109.
 */

/*
 *  * @license This library is free software; you can redistribute it and/or
 *      modify it under the terms of the GNU Lesser General Public
 *      License as published by the Free Software Foundation; either
 *      version 2.1 of the License, or (at your option) any later version.
 *
 *      This library is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *       Lesser General Public License for more details.
 *
 *      You should have received a copy of the GNU Lesser General Public
 *      License along with this library; if not, write to the Free Software
 *      Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */


public class QuadDE {
		
	public static final String version="1.0";

 /**
  * @param f - integrand f(x)
  * f(x) needs to be analytic over (a,b).
  * @param a - lower limit of integration 
  * @param b - upper limit of integration (double)
  * @param eps - estimate of the absolute error
	        relative error
	            eps is relative error requested excluding 
	            cancellation of significant digits.
	            i.e. eps means : (absolute error) / 
	                             (integral_a^b |f(x)| dx).
	            eps does not mean : (absolute error) / I.
		
		intde computes the  integral of f(x) over (a,b)
 
		    [remarks]
	        error message
            err >= 0 : normal termination.
            err < 0  : abnormal termination (m >= mmax).
                       i.e. convergent error is detected :
                           1. f(x) or (d/dx)^n f(x) has 
                              discontinuous points or sharp 
                              peaks over (a,b).
                              you must divide the interval 
                              (a,b) at this points.
                           2. relative error of f(x) is 
                              greater than eps.
                           3. f(x) has oscillatory factor 
                              and frequency of the oscillation 
                              is very high.
		*/
	public	static double[] intde(QFunction f, double a, double b, double eps){
			/* ---- adjustable parameters ---- */
			int mmax = 512;
			double efs = 0.1, hoff = 8.5;
			/* ------------------------------ */
			int m=1;
			double  iback, irback, t, ep, em, xw, xa, wg;
			double fa=0.0, fb=0.0, errt, errh=0, errd;

			//pi2 = 2 * atan(1.0);
			final double pi2 = 0.5*PI;
			final double epsln = 1.0 - log(efs * eps);
			final double epsh = sqrt(efs * eps);
			final double h0 = hoff / epsln;
			final double ehp = exp(h0);
			final double ehm = 1. / ehp;
			final double epst = exp(-ehm * epsln);
			final double ba = b - a;
			double ir = f.eval((a + b) * 0.5) * (ba * 0.25);
			double i = ir * (2. * pi2);
			double err = abs(i) * epst;
			//double f1=0, f2=0;
			double h = 2 * h0;
			//m = 1;
			do {
				iback = i;
				irback = ir;
				t = h * 0.5;
				do {
					em = exp(t);
					ep = pi2 * em;
					em = pi2 / em;
					do {
						xw = 1. / (1. + exp(ep - em));
						xa = ba * xw;
						wg = xa * (1 - xw);
						
						//f1 = f.eval(a + xa) * wg;
						//f2 = f.eval(b - xa) * wg;
						fa = f.eval(a + xa) * wg;
						fb = f.eval(b - xa) * wg;
						//if (!Double.isNaN(f1)) fa=f1*wg;
						//if (!Double.isNaN(f2)) fb=f2*wg;
						ir += fa + fb;
						i += (fa + fb) * (ep + em);
						errt = (abs(fa) + abs(fb)) * (ep + em);
						if (m == 1) err += errt * epst;
						ep *= ehp;
						em *= ehm;
					} while (errt > err || xw > epsh);
					t += h;
				} while (t < h0);
				if (m == 1) {
					errh = (err / epst) * epsh * h0;
					errd = 1. + 2. * errh;
				} else {
					errd = h * (abs(i - 2. * iback) + 4. * abs(ir - 2. * irback));
				}
				h *= 0.5;
				m *= 2;
			} while (errd > errh && m < mmax);
			i *= h;
			if (errd > errh) {
				err = -errd * m;
			} else {
				err = errh * epsh * m / (2. * efs);
			}
			return new double[] {i, err};
		}
		
 /**
  * @param f - integrand f(x) 
  * @param a - lower limit of integration 
  * @param eps - relative error requested 
    relative error
	            eps is relative error requested excluding 
	            cancellation of significant digits.
	            i.e. eps means : (absolute error) / 
	                             (integral_a^infinity |f(x)| dx).
	            eps does not mean : (absolute error) / I.
	            
	intdei computes the integral of f(x) over (a,infinity), 
	            f(x) has no oscillatory factor!
	f(x) needs to be analytic over (a,infinity).
	    [remarks]
	         error message
	            err >= 0 : normal termination.
	            err < 0  : abnormal termination (m >= mmax).
	                       i.e. convergent error is detected :
	                           1. f(x) or (d/dx)^n f(x) has 
	                              discontinuous points or sharp 
	                              peaks over (a,infinity).
	                              you must divide the interval 
	                              (a,infinity) at this points.
	                           2. relative error of f(x) is 
	                              greater than eps.
	                           3. f(x) has oscillatory factor 
	                              and decay of f(x) is very slow 
	                              as x -> infinity.
	*/
	public	static double[] intdei(QFunction f, double a, double eps){
			/* ---- adjustable parameter ---- */
			int mmax = 256;
			double efs = 0.1, hoff = 11.0;
			/* ------------------------------ */
			int m;
			double  epsh, h0, ehp, ehm, epst, ir, h, iback, irback, 
			t, ep, em, xp, xm, fp, fm, errt=0, errh=0, errd;

			//pi4 = atan(1.0);
			final double pi4 = 0.25*PI;
			final double epsln = 1. - log(efs * eps);
			epsh = sqrt(efs * eps);
			h0 = hoff / epsln;
			ehp = exp(h0);
			ehm = 1. / ehp;
			epst = exp(-ehm * epsln);
			ir = f.eval(a + 1);

			double i = ir * (2 * pi4);
			//System.out.println("i="+i);
			double err = abs(i) * epst;
			h = 2 * h0;
			m = 1;
			do {
				iback = i;
				irback = ir;
				t = h * 0.5;
				do {
					em = exp(t);
					ep = pi4 * em;
					em = pi4 / em;
					//System.out.println("t " +t+ " ep "+ep+" em "+em);
					do {
						xp = exp(ep - em);
						xm = 1 / xp;
						fp = f.eval(a + xp) * xp;
						fm = f.eval(a + xm) * xm;
						//System.out.println("  fp "+fp+" fm "+fm);
						if (Double.isNaN(fp)) fp=0.0;
						if (Double.isNaN(fm)) fm=0.0;
						ir += fp + fm;
						i += (fp + fm) * (ep + em);
						errt = (abs(fp) + abs(fm)) * (ep + em);
				 
						if (m == 1) err += errt * epst;
						ep *= ehp;
						em *= ehm;
						
					} while (errt > err || xm > epsh);
					t += h;
				} while (t < h0);
				if (m == 1) {
					errh = (err / epst) * epsh * h0;
					errd = 1 + 2. * errh;
				} else {
					errd = h * (abs(i - 2. * iback) + 4. * abs(ir - 2. * irback));
				}
				h *= 0.5;
				m *= 2;
			} while (errd > errh && m < mmax);
			i *= h;
			//System.out.println("i="+i);
			if (errd > errh) {
				err = -errd * m;
			} else {
				err = errh * epsh * m / (2. * efs);
			}
			
			return new double[] {i, err};
		}
	
	/**
	 * @param f - integrand f(x)  
                   f(x) needs to be analytic over (a,infinity)
       @param a - lower limit of integration 
       @param omega - frequency of oscillation 
       @param eps - relative error requested 
       relative error
            eps is relative error requested excluding 
            cancellation of significant digits.
            i.e. eps means : (absolute error) / 
                             (integral_a^R |f(x)| dx).
            eps does not mean : (absolute error) / I.
            
       @param err - estimate of the absolute error 
 
	  intdeo computes the integral of f(x) over (a, infinity), 
            f(x) has an oscillatory factor :
            f(x) = g(x) * sin(omega * x + theta) as x -> infinity.
   
    [remarks]
         error message
            err >= 0 : normal termination.
            err < 0  : abnormal termination (m >= mmax).
                       i.e. convergent error is detected :
                           1. f(x) or (d/dx)^n f(x) has 
                              discontinuous points or sharp 
                              peaks over (a,infinity).
                              you must divide the interval 
                              (a,infinity) at this point.
                           2. relative error of f(x) is 
                              greater than eps.
*/
	public static double[] intdeo(QFunction f, double a,  double omega, double eps) {
			/* ---- adjustable parameter ---- */
			int mmax = 256, lmax = 5;
			double efs = 0.1, enoff = 0.40, pqoff = 2.9, ppoff = -0.72;
			/* ------------------------------ */
			int m, l, k;
			double iback, irback, t, ep, em, tk,  
			wg, xa, fp, fm, errh=0, tn, errd;

			//pi4 = atan(1.0);
			final double pi4 = 0.25*PI;
			final double epsln = 1. - log(efs * eps);
			final double epsh = sqrt(efs * eps);
			final int n = (int) (enoff * epsln);
			final double frq4 = abs(omega) / (2. * pi4);
			final double per2 = 4. * pi4 / abs(omega);
			double pq = pqoff / epsln;
			double pp = ppoff - log(pq * pq * frq4);
			final double ehp = exp(2. * pq);
			final double ehm = 1. / ehp;
			double xw = exp(pp - 2. * pi4);

			double i = f.eval(a + sqrt(xw * (per2 * 0.5)));
			double ir = i * xw;
			i *= per2 * 0.5;
			double err = abs(i);
			double h = 2.0;
			m = 1;
			do {
				iback = i;
				irback = ir;
				t = h * 0.5;
				do {
					em = exp(2 * pq * t);
					ep = pi4 * em;
					em = pi4 / em;
					tk = t;
					do {
						xw = exp(pp - ep - em);
						wg = sqrt(frq4 * xw + tk * tk);
						xa = xw / (tk + wg);
						wg = (pq * xw * (ep - em) + xa) / wg;
						fm = f.eval(a + xa);
						fp = f.eval(a + xa + per2 * tk);
						if (Double.isNaN(fp)) fp=0.0;
						if (Double.isNaN(fm)) fm=0.0;
						ir += (fp + fm) * xw;
						fm *= wg;
						fp *= per2 - wg;
						i += fp + fm;
						if (m == 1) err += abs(fp) + abs(fm);
						ep *= ehp;
						em *= ehm;
						tk += 1.0;
					} while (ep < epsln);
					if (m == 1) {
						errh = err * epsh;
						err *= eps;
					}
					tn = tk;
					while (abs(fm) > err) {
						xw = exp(pp - ep - em);
						xa = xw / tk * 0.5;
						wg = xa * (1.0 / tk + 2. * pq * (ep - em));
						fm = f.eval(a + xa);
						ir += fm * xw;
						fm *= wg;
						i += fm;
						ep *= ehp;
						em *= ehm;
						tk += 1.0;
					}
					fm = f.eval(a + per2 * tn);
					em = per2 * fm;
					i += em;
					if (abs(fp) > err || abs(em) > err) {
						l = 0;
						for (;;) {
							l++;
							tn += n;
							em = fm;
							fm = f.eval(a + per2 * tn);
							xa = fm;
							ep = fm;
							em += fm;
							xw = 1;
							wg = 1;
							for (k = 1; k <= n - 1; k++) {
								xw = xw * (n + 1 - k) / k;
								wg += xw;
								fp = f.eval(a + per2 * (tn - k));
								xa += fp;
								ep += fp * wg;
								em += fp * xw;
							}
							wg = per2 * n / (wg * n + xw);
							em = wg * abs(em);
							if (em <= err || l >= lmax) break;
							i += per2 * xa;
						}
						i += wg * ep;
						if (em > err) err = em;
					}
					t += h;
				} while (t < 1);
				if (m == 1) {
					errd = 1. + 2. * errh;
				} else {
					errd = h * (abs(i - 2. * iback) + pq * abs(ir - 2. * irback));
				}
				h *= 0.5;
				m *= 2;
			} while (errd > errh && m < mmax);
			i *= h;
			if (errd > errh) {
				err = -errd;
			} else {
				err *= m * 0.5;
			}
			return new double[] {i, err};  
		}
	
	/** 
	 * adaptive integration
	 * @param f - integrand f(x) 
       @param a - lower limit of integration 
       @param b - upper limit of integration 
       @param eps - relative error requested 
	 */
	 public static double[] intdea(QFunction f, double a, double b, double eps) {
		 double ret=0.0;
		 double fa=f.eval(a);
	     double fb=f.eval(b);
	     ret=adaptive(f, a, b, fa, fb, eps);	
		 return  new double[] {ret, eps};  
		 
	 }
	
	 /**
	  *  adaptive integration
		 * @param f - integrand f(x) 
	       @param a - lower limit of integration 
	       @param b - upper limit of integration 
	       @param fa - f(a)
	       @param fb - f(b)
	       @param eps - relative error requested 
		 */
	 private static double adaptive(QFunction f, double a, double b, double fa, double fb, double eps) {
	        final double h = b - a;
	        final double c = (a + b) *0.5;
	        final double d = (a + c) *0.5;
	        final double e = (b + c) *0.5;
	        double Q1=0.0, Q2=0.0;
	       // niter++;
	        double fc=0;
	        try {
	          fc=f.eval(c);
	          Q1 = h/6.0  * (fa + 4.0*fc + fb);
	          Q2 = h/12.0 * (fa + 4.0*f.eval(d) + 2.0*fc + 4.0*f.eval(e) + fb);
	        } catch (RuntimeException ex) {
	        	return 0.0;
	        }
	        if (abs(Q2 - Q1) <= eps)
	            return Q2 + (Q2 - Q1) / 15.0;
	        else
	            return adaptive(f,a, c, fa, fc, eps) + adaptive(f, c, b, fc, fb, eps);
	   }
	 
	 /**
	  *  adaptive integration
		 * @param f - integrand f(x) 
		 *  the method is recursive and uses regula falsi splitting of the interval
		 *  
	       @param a -lower limit of integration 
	       @param b -upper limit of integration 
	       @param eps - relative error requested 
		 */
		 public static double[] intdeaf(QFunction f, double a, double b, double eps) {
			 double ret=0.0;
			 double fa=f.eval(a);
		     double fb=f.eval(b);
		     ret=adaptivef(f, a, b, fa, fb, eps);	
			 return  new double[] {ret, eps};  
			 
		 }
			
	 private static double adaptivef(QFunction f, double a, double b, double fa, double fb, double eps) {
	        final double h = b - a;
	        final double m= (a+b)*0.5;
	        final double fm=f.eval(m);
	        // regula falsi splitting
	        final double c= m + h/4.*((fb-fa)/(fm-fa)- (fb-fa)/(fb-fm));
	        //final double c = (a + b) *0.5;
	        final double d = (a + c) *0.5;
	        final double e = (b + c) *0.5;
	        double Q1=0.0, Q2=0.0;
	       // niter++;
	        double fc=0;
	        try {
	          fc=f.eval(c);
	          Q1 = h/6.0  * (fa + 4.*fc + fb);
	          Q2 = h/12.0 * (fa + 4.*f.eval(d) + 2.*fc + 4.*f.eval(e) + fb);
	        } catch (RuntimeException ex) {
	        	return 0.0;
	        }
	        if (abs(Q2 - Q1) <= eps)
	            return Q2 + (Q2 - Q1) / 15.0;
	        else
	            return adaptive(f,a, c, fa, fc, eps) + adaptive(f, c, b, fc, fb, eps);
	   }
	
	 
}
