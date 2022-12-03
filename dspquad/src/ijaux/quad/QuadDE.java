package ijaux.quad;
import static java.lang.Math.*;

 

/*
 	Courtesy to Prof. Takuya Ooura  and  Dr. Robert A. van Engelen
 	Library for computation of special functions
 	and numerical integration
 	
The code is based mostly on
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
	          Q1 = h/6.  * (fa + 4.*fc + fb);
	          Q2 = h/12. * (fa + 4.*f.eval(d) + 2.*fc + 4.*f.eval(e) + fb);
	        } catch (RuntimeException ex) {
	        	return 0.0;
	        }
	        if (abs(Q2 - Q1) <= eps)
	            return Q2 + (Q2 - Q1) / 15.;
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
	          Q1 = h/6.  * (fa + 4.*fc + fb);
	          Q2 = h/12. * (fa + 4.*f.eval(d) + 2.*fc + 4.*f.eval(e) + fb);
	        } catch (RuntimeException ex) {
	        	return 0.0;
	        }
	        if (abs(Q2 - Q1) <= eps)
	            return Q2 + (Q2 - Q1) / 15.;
	        else
	            return adaptive(f,a, c, fa, fc, eps) + adaptive(f, c, b, fc, fb, eps);
	   }
	
	 
	/**
	 * Improving the Double Exponential Quadrature Tanh-Sinh, Sinh-Sinh and Exp-Sinh Formulas
	 Dr. Robert A. van Engelen, Genivia Labs
	 */
	public static double[] intdeq(QFunction f, double a, double b, double eps ) {
		 final double tol = 10.*eps;
		 //	 if (n <= 0) // use default levels n=6
		 final int n = 6; // 6 is “optimal”, 7 just as good taking longer
		 
		 final double c = (a+b)/2.; // center (mean)
		 final double d = (b-a)/2.; // half distance
		 double s = f.eval(c);
		 double v,  h = 2;
		 double err;
		 int k = 0;
	
		 if (eps <= 0) // use default eps=1E-8
			 eps = 1E-8;
		 do {
			 double p = 0, q, fp = 0, fm = 0, t, eh;
			 h /= 2.;
			 t = eh = exp(h);
			 if (k > 0)
			 eh *= eh;
			 do {
				 final double t1=1./t;
				 final double u = exp(t1-t); // = exp(-2*sinh(j*h)) = 1/exp(sinh(j*h))^2
				 final double r = 2.*u/(1.+u); // = 1 - tanh(sinh(j*h))
				 final double w = (t+t1)*r/(1.+u); // = cosh(j*h)/cosh(sinh(j*h))^2
				 final double x = d*r;
				 if (a+x > a) { // if too close to a then reuse previous fp
					 double y = f.eval(a+x);
				 if (!Double.isNaN(y))
					 fp = y; // if f(x) is finite, add to the local sum
				 }
				 if (b-x < b) { // if too close to b then reuse previous fm
					 double y = f.eval(b-x);
				 if (!Double.isNaN(y))
					 fm = y; // if f(x) is finite, add to the local sum
				 }
				 q = w*(fp+fm);
				 p += q;
				 t *= eh;
			 } while (abs(q) > eps*abs(p));
			 v = s-p;
			 s += p;
			 ++k;
		 } while (abs(v) > tol*abs(s) && k <= n);
		 err = abs(v)/(abs(s)+eps);
		 return new double[] {d*s*h, err}; // result with estimated relative error e
	 }
	 
	
	
	 // abscissas and weights pre-calculated with Legendre Stieltjes polynomials
	 static final double[] abscissas  = new double[] {
		 0.00000000000000000e+00,
		 7.65265211334973338e-02,
		 1.52605465240922676e-01,
		 2.27785851141645078e-01,
		 3.01627868114913004e-01,
		 3.73706088715419561e-01,
		 4.43593175238725103e-01,
		 5.10867001950827098e-01,
		 5.75140446819710315e-01,
		 6.36053680726515025e-01,
		 6.93237656334751385e-01,
		 7.46331906460150793e-01,
		 7.95041428837551198e-01,
		 8.39116971822218823e-01,
		 8.78276811252281976e-01,
		 9.12234428251325906e-01,
		 9.40822633831754754e-01,
		 9.63971927277913791e-01,
		 9.81507877450250259e-01,
		 9.93128599185094925e-01,
		 9.98859031588277664e-01,
	 };
	 
	 static final double[] weights = new double[] {
			 7.66007119179996564e-02,
			 7.63778676720807367e-02,
			 7.57044976845566747e-02,
			 7.45828754004991890e-02,
			 7.30306903327866675e-02,
			 7.10544235534440683e-02,
			 6.86486729285216193e-02,
			 6.58345971336184221e-02,
			 6.26532375547811680e-02,
			 5.91114008806395724e-02,
			 5.51951053482859947e-02,
			 5.09445739237286919e-02,
			 4.64348218674976747e-02,
			 4.16688733279736863e-02,
			 3.66001697582007980e-02,
			 3.12873067770327990e-02,
			 2.58821336049511588e-02,
			 2.03883734612665236e-02,
			 1.46261692569712530e-02,
			 8.60026985564294220e-03,
			 3.07358371852053150e-03,
			 };
	 
	 static final double[] gauss_weights  = new double[] {
			 1.52753387130725851e-01,
			 1.49172986472603747e-01,
			 1.42096109318382051e-01,
			 1.31688638449176627e-01,
			 1.18194531961518417e-01,
			 1.01930119817240435e-01,
			 8.32767415767047487e-02,
			 6.26720483341090636e-02,
			 4.06014298003869413e-02,
			 1.76140071391521183e-02,
			 };
	
	 // Adaptive Gauss-Kronrod (G10,K21)
	 
	 public static double[] quadgkro (QFunction f, double a, double b,  double eps) {
		 return qakro(f,a,b,6, 1e-4,eps,1.0e-8);
	 }
	 
	 static double[]  qakro(QFunction f, double a, double b, int n, double tol, double eps, double err) {
			 double c = (a+b)/2.;
			 double d = (b-a)/2.;
			
			 double[] r = qgk(f, c, d, err);
			 double e=r[1];
			 double s = d*r[0];
			 double t = abs(s*tol);

			 if (eps <= 0) // use default eps=1E-8
				 eps = 1E-8;
			 if (tol == 0)
				 tol = t;
			 
			 
			 if (n > 0 && t < e && tol < e) {
				 r= qakro(f, a, c, n-1, t/2., eps, err);
				 s=r[0];
				 err=r[1];
				 r=qakro(f, c, b, n-1, t/2., eps, e);
				 s +=r[0];
				 err += e;
			 	 return new double[] {s, err};
			 }
			 err = e;
			 return new double[] {s, err};
	 }
	 
	 
	 private static double[]  qgk(QFunction f, double c, double d, double err) {
	
		 double p = 0; // Kronrod quadrature sum
		 double q = 0; // Gauss quadrature sum
		 double fp, fm;
		
		 fp = f.eval(c);
		 p = fp * weights[0];
		 for (int i = 1; i < weights.length; i += 2) {
			 fp = f.eval(c + d * abscissas[i]);
			 fm = f.eval(c - d * abscissas[i]);
			 p += (fp + fm) * weights[i];
			 q += (fp + fm) * gauss_weights[i/2];
		 }
		 for (int i = 2; i < weights.length; i += 2) {
			 fp = f.eval(c + d * abscissas[i]);
			 fm = f.eval(c - d * abscissas[i]);
			 p += (fp + fm) * weights[i];
		 }
		 err = abs(p - q);
		 final double e = abs(2.*p*1e-17); // optional, to take 1e-17 MachEps prec. into account
		 if (err < e)
			 err = e;
		 return new double[] {p, err};
		}
				
}
