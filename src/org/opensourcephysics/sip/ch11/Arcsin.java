/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch11;
import org.opensourcephysics.numerics.*;

/**
 * Arcsin demonstrates how to use interpolation to define an inverse function.
 *
 *  @author Wolfgang Christian,  Jan Tobochnik, Harvey Gould, Joshua Gould
 *  @version 1.0   revised 06/09/05
 */
public class Arcsin {
  static Function arcsin;

  private Arcsin() {} // probibit instantiation because all methods are static

  /**
   * Evaluates the arcsin function.
   *
   * @param x double
   * @return double the angle
   */
  static public double evaluate(double x) {
    if((x<-1)||(x>1)) {
      return Double.NaN;
    }
	return arcsin.evaluate(x);
  }

  static { // creates the static function arcsin when class is loaded
    int n = 10;
    double[] xValues = new double[n];
    double[] yValues = new double[n];
    double x = -Math.PI/2, dx = Math.PI/(n-1);
    for(int i = 0;i<n;i++) {
      xValues[i] = x;
      yValues[i] = Math.sin(x);
      x += dx;
    }
    arcsin = new CubicSpline(yValues, xValues);
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
