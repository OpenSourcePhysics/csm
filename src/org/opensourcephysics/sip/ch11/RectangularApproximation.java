/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch11;
import org.opensourcephysics.display.Dataset;
import org.opensourcephysics.numerics.Function;

/**
 * RectangularApproximation displays a rectangular approximation to an integral.
 *
 *  @author Wolfgang Christian,  Jan Tobochnik, Harvey Gould, Joshua Gould
 *  @version 1.0   revised 06/09/05
 */
public class RectangularApproximation extends Dataset {
  double sum = 0; // will contain the integral

  /**
   * Constructs a RectangularApproximation to the integral for the given function.
   *
   * @param f Function
   * @param a double the lower limit
   * @param b double the upper limit
   * @param num int
   */
  public RectangularApproximation(Function f, double a, double b, int n) {
    setMarkerColor(new java.awt.Color(255, 0, 0, 128)); // transparent red
    setMarkerShape(Dataset.AREA);
    sum = 0;
    double x = a; // lower limit
    double y = f.evaluate(a);
    double dx = (b-a)/n;
    // use methods in Dataset superclass
    append(x, 0); // start on the x axis
    append(x, y); // the top left hand corner of the first rectangle
    while(x<b) {         // b is the upper limit
      x += dx;
      append(x, y);      // top right hand corner of current rectangle
      sum += y;
      y = f.evaluate(x); // calculate a new y at the new x
      append(x, y);      // the top left hand corner of the next rectangle
    }
    append(x, 0); // finish on the x axis
    sum *= dx;
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
