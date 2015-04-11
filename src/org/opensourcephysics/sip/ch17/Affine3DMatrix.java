/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch17;
import org.opensourcephysics.numerics.*;

/**
 * Affine3DMatrix implements 3D affine transformations using a matrix representation.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 06/05/2005
 */
public class Affine3DMatrix implements Transformation {
  private double[][] matrix = new double[4][4]; // transformation matrix
  private double[][] inverse = null;            // inverse transformation matrix if it exists
  private boolean inverted = false; // true if inverse has been computed

  /**
   * Constructs a 3D affine transformation using the given matrix.
   *
   * Affine transformations can be applied to 3D or homogeneous 3D coordinates.
   * A 3 by 3 matrix sets the rotation and shear portion of the transformation.
   * A 4 by 4 matrix sets rotation, shear, and translation.
   * A null matrix sets the transformation to the identity transformation.
   *
   * @param mat double[][]
   */
  public Affine3DMatrix(double[][] matrix) {
    if(matrix==null) { // identity matrix
      this.matrix[0][0] = this.matrix[1][1] = this.matrix[2][2] = this.matrix[3][3] = 1;
      return;
    }
    for(int i = 0;i<matrix.length;i++) { // loop over the rows
      System.arraycopy(matrix[i], 0, this.matrix, 0, matrix[i].length);
    }
  }

  /**
   * Creates an AffineMatrix representing a rotation about the origin by some angle around
   * the given axis.
   *
   * @param theta double
   * @param axis double[]
   * @return AffineMatrix
   */
  public static Affine3DMatrix Rotation(double theta, double[] axis) {
    Affine3DMatrix at = new Affine3DMatrix(null);
    double[][] atMatrix = at.matrix;
    double norm = Math.sqrt(axis[0]*axis[0]+axis[1]*axis[1]+axis[2]*axis[2]);
    double x = axis[0]/norm, y = axis[1]/norm, z = axis[2]/norm;
    double c = Math.cos(theta), s = Math.sin(theta);
    double t = 1-c;
    // matrix elements not listed are zero
    atMatrix[0][0] = t*x*x+c;
    atMatrix[0][1] = t*x*y-s*z;
    atMatrix[0][2] = t*x*y+s*y;
    atMatrix[1][0] = t*x*y+s*z;
    atMatrix[1][1] = t*y*y+c;
    atMatrix[1][2] = t*y*z-s*x;
    atMatrix[2][0] = t*x*z-s*y;
    atMatrix[2][1] = t*y*z+s*x;
    atMatrix[2][2] = t*z*z+c;
    atMatrix[3][3] = 1;
    return at;
  }

  /**
   * Creates an AffineMatrix representing a translation.
   * @param theta double
   * @param axis double[]
   * @return AffineMatrix
   */
  public static Affine3DMatrix Translation(double dx, double dy, double dz) {
    Affine3DMatrix at = new Affine3DMatrix(null);
    double[][] m = at.matrix;
    // matrix elements not listed are zero
    m[0][0] = 1;
    m[0][3] = dx;
    m[1][1] = 1;
    m[1][3] = dy;
    m[2][2] = 1;
    m[2][3] = dz;
    m[3][3] = 1;
    return at;
  }

  /**
   * Provides a copy of this transformation.
   */
  public Object clone() {
    return new Affine3DMatrix(matrix);
  }

  /**
   * Transforms the given point.
   *
   * @param point the coordinates to be transformed
   */
  public double[] direct(double[] point) {
    int n = point.length;
    double[] tempPoint = new double[n];
    System.arraycopy(point, 0, tempPoint, 0, n);
    for(int i = 0;i<n;i++) {
      point[i] = 0;
      for(int j = 0;j<n;j++) {
        point[i] += matrix[i][j]*tempPoint[j];
      }
    }
    return point;
  }

  /**
   * Transforms the given point using the inverse transformation (if it exists).
   *
   * If the transformation is not invertible, then a call to this
   * method must throw a UnsupportedOperationException exception.
   *
   * @param point the coordinates to be transformed
   */
  public double[] inverse(double[] point) throws UnsupportedOperationException {
    if(!inverted) {
      calcInverse(); // computes inverse using LU decompostion
    }
    if(inverse==null) { // inverse does not exist
      throw new UnsupportedOperationException("inverse matrix does not exist.");
    }
    int n = point.length;
    double[] pt = new double[n];
    System.arraycopy(point, 0, pt, 0, n);
    for(int i = 0;i<n;i++) {
      point[i] = 0;
      for(int j = 0;j<n;j++) {
        point[i] += inverse[i][j]*pt[j];
      }
    }
    return point;
  }

  // calculates inverse using Lower-Upper decomposition
  private void calcInverse() {
    LUPDecomposition lupd = new LUPDecomposition(matrix);
    inverse = lupd.inverseMatrixComponents();
    inverted = true; // signal that the inverse computation has been performed
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
