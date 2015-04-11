/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch09;
import org.opensourcephysics.numerics.Function;

/**
 * Synthesize defines a Fourier series of sinusoidal functions.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
public class Synthesize implements Function {
  double[] cosCoefficients, sinCoefficients; // cosine and sine coefficients
  double a0;                                 // the constant term
  double omega0;

  /**
   * Constructs Synthesize.
   *
   * @param period
   * @param a0
   * @param cosCoef
   * @param sinCoef
   */
  public Synthesize(double period, double a0, double[] cosCoef, double[] sinCoef) {
    omega0 = Math.PI*2/period;
    cosCoefficients = cosCoef;
    sinCoefficients = sinCoef;
    this.a0 = a0;
  }

  /**
   * Evalutates the Fourier series at x.
   *
   * @param x
   *
   * @return the value
   */
  public double evaluate(double x) {
    double f = a0/2;
    // sum the cosine terms
    for(int i = 0, n = cosCoefficients.length;i<n;i++) {
      f += cosCoefficients[i]*Math.cos(omega0*x*(i+1));
    }
    // sum the sine terms
    for(int i = 0, n = sinCoefficients.length;i<n;i++) {
      f += sinCoefficients[i]*Math.sin(omega0*x*(i+1));
    }
    return f;
  }
}

/* 
 * Open Source Physics software is free software; you can redistribute
 * it and/or modify it under the terms of the GNU General Public License (GPL) as
 * published by the Free Software Foundation; either version 2 of the License,
 * or(at your option) any later version.

 * Code that uses any portion of the code in the org.opensourcephysics package
 * or any subpackage (subdirectory) of this package must must also be be released
 * under the GNU GPL license.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston MA 02111-1307 USA
 * or view the license online at http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2007  The Open Source Physics project
 *                     http://www.opensourcephysics.org
 */
