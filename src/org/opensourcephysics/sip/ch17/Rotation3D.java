/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch17;

/**
 * Rotation3D implements 3D rotations using a matrix representation.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 06/05/2005
 */
public class Rotation3D {
  private double[][] mat = new double[4][4]; // the transformation matrix

  /**
   * Creates a representation of a rotation about the origin
   * by some angle around the given axis.
   *
   * @param theta double
   * @param axis double[]
   */
  public Rotation3D(double theta, double[] axis) {
    double norm = Math.sqrt(axis[0]*axis[0]+axis[1]*axis[1]+axis[2]*axis[2]);
    double x = axis[0]/norm, y = axis[1]/norm, z = axis[2]/norm;
    double c = Math.cos(theta), s = Math.sin(theta);
    double t = 1-c;
    // matrix elements not listed are zero
    mat[0][0] = t*x*x+c;
    mat[0][1] = t*x*y-s*z;
    mat[0][2] = t*x*y+s*y;
    mat[1][0] = t*x*y+s*z;
    mat[1][1] = t*y*y+c;
    mat[1][2] = t*y*z-s*x;
    mat[2][0] = t*x*z-s*y;
    mat[2][1] = t*y*z+s*x;
    mat[2][2] = t*z*z+c;
    mat[3][3] = 1;
  }

  /**
   * Transforms the given point.
   *
   * @param point the coordinates to be transformed
   */
  public void direct(double[] point) {
    int n = point.length;
    double[] pt = new double[n];
    System.arraycopy(point, 0, pt, 0, n);
    for(int i = 0;i<n;i++) {
      point[i] = 0;
      for(int j = 0;j<n;j++) {
        point[i] += mat[i][j]*pt[j];
      }
    }
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
