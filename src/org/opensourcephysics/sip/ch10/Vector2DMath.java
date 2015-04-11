/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch10;

/**
 * VectorMath defines vector operations for use by the RadiatingCharge class.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0
 */
final class Vector2DMath {
  private Vector2DMath() {
    // a private constructor prohibits users from instantiating this class
  }

  /**
   * Calculate the two-component dot product.
   * @param a  the first  vector
   * @param b  the second vector
   * @return the dot product
   */
  static public double dot2D(double[] a, double[] b) {
    double sum = 0;
    for(int i = 0;i<2;i++) {
      sum += a[i]*b[i];
    }
    return sum;
  }

  /**
   * Calculate the magnitude of a two-component vector.
   * @param a  the  vector
   * @return the magnitude
   */
  static public double mag2D(double[] a) {
    double sum = 0;
    for(int i = 0;i<2;i++) {
      sum += a[i]*a[i];
    }
    return Math.sqrt(sum);
  }

  /**
   * Calculate the cross product of two-component vectors.
   * The result is the component perpendicular to the plane.
   *
   * @param a  the first  vector
   * @param b  the second vector
   * @return the cross product.
   */
  static public double cross2D(double[] a, double[] b) {
    return a[0]*b[1]-a[1]*b[0];
  }

  /**
   * Calculate the cross product of a vector within the xy plane and a vector perpendicular to that plane.
   * The operation returns the xy vector.
   *
   * @param a  the vector in the plane
   * @param b  the vector perpendicular to the plane
   */
  static public double[] crossZ(double[] a, double bz) {
    double temp = a[0];
    a[0] = a[1]*bz;
    a[1] = -temp*bz;
    return a;
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
