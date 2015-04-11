/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see:
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch10;

/**
 * Maxwell models electrodynamcis using the
 * Visscher-Yee finite difference approximation to Maxwell's equations.
 *
 * The current() method was revised 04/04/2006.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.1
 */
public class Maxwell {
  // static variables determine units and time scale
  static final double pi4 = 4*Math.PI;
  static final double dt = 0.03;
  static final double dl = 0.1;
  static final double escale = dl/(4*Math.PI*dt);
  static final double bscale = escale*dl/dt;
  static final double jscale = 1;
  double dampingCoef = 0.1; // damping coefficient near boundaries
  int size;
  double t;                 // time
  double[][][][] E, B, J;

  /**
   * Creates Maxwell and initializes arrays to the given size.
   * @param size int
   */
  public Maxwell(int size) {
    this.size = size;
    // 3D arrays for electric field, magnetic field, and current
    // last three indices indicate location, first index indicates x, y, or z component
    E = new double[3][size][size][size];
    B = new double[3][size][size][size];
    J = new double[3][size][size][size];
  }

  /**
   * Does a computation step by advancing the time.
   */
  public void doStep() {
    current(t); // update the current
    computeE(); // step electric field
    computeB(); // step magnetic field
    damping();  // damp transients
    t += dt;
  }

  /**
   * Sets the current.
   *
   * The current flows for a short time in opposite directions from the middle and then stops.
   * Correct 04/04/2006: J[mid+i][mid][mid][0] changed to J[0][mid+i][mid][mid]
   * @param t
   */
  void current(double t) {
    final int mid = size/2;
    double delta = 1.0;
    for(int i = -3;i<5;i++) {
      J[0][mid+i][mid][mid] = (t<delta) ? +1 : 0;
    }
  }

  /**
   * Computes the electric field defined on the faces.
   */
  void computeE() {
    for(int x = 1;x<size-1;x++) {
      for(int y = 1;y<size-1;y++) {
        for(int z = 1;z<size-1;z++) {
          double curlBx = (B[1][x][y][z]-B[1][x][y][z+1]+B[2][x][y+1][z]-B[2][x][y][z])/dl;
          E[0][x][y][z] += dt*(curlBx-pi4*J[0][x][y][z]);
          double curlBy = (B[2][x][y][z]-B[2][x+1][y][z]+B[0][x][y][z+1]-B[0][x][y][z])/dl;
          E[1][x][y][z] += dt*(curlBy-pi4*J[1][x][y][z]);
          double curlBz = (B[0][x][y][z]-B[0][x][y+1][z]+B[1][x+1][y][z]-B[1][x][y][z])/dl;
          E[2][x][y][z] += dt*(curlBz-pi4*J[2][x][y][z]);
        }
      }
    }
  }

  /**
   * Computes the magnetic field defined at the edges.
   */
  void computeB() {
    for(int x = 1;x<size-1;x++) {
      for(int y = 1;y<size-1;y++) {
        for(int z = 1;z<size-1;z++) {
          double curlEx = (E[2][x][y][z]-E[2][x][y-1][z]+E[1][x][y][z-1]-E[1][x][y][z])/dl;
          B[0][x][y][z] -= dt*curlEx;
          double curlEy = (E[0][x][y][z]-E[0][x][y][z-1]+E[2][x-1][y][z]-E[2][x][y][z])/dl;
          B[1][x][y][z] -= dt*curlEy;
          double curlEz = (E[1][x][y][z]-E[1][x-1][y][z]+E[0][x][y-1][z]-E[0][x][y][z])/dl;
          B[2][x][y][z] -= dt*curlEz;
        }
      }
    }
  }

  /**
   * Damps the fields near the boundaries.
   */
  void damping() {
    for(int i = 0;i<size;i++) {
      for(int j = 0;j<size;j++) {
        for(int w = 0;w<4;w++) { // w used to index cell near boundary subject to damping
          for(int comp = 0;comp<3;comp++) {
            E[comp][w][i][j] -= dampingCoef*E[comp][w][i][j];
            E[comp][size-w-1][i][j] -= dampingCoef*E[comp][size-w-1][i][j];
            E[comp][i][w][j] -= dampingCoef*E[comp][i][w][j];
            E[comp][i][size-w-1][j] -= dampingCoef*E[comp][i][size-w-1][j];
            E[comp][i][j][w] -= dampingCoef*E[comp][i][j][w];
            E[comp][i][j][size-w-1] -= dampingCoef*E[comp][i][j][size-w-1];
            B[comp][w][i][j] -= dampingCoef*B[comp][w][i][j];
            B[comp][size-w-1][i][j] -= dampingCoef*B[comp][size-w-1][i][j];
            B[comp][i][w][j] -= dampingCoef*B[comp][i][w][j];
            B[comp][i][size-w-1][j] -= dampingCoef*B[comp][i][size-w-1][j];
            B[comp][i][j][w] -= dampingCoef*B[comp][i][j][w];
            B[comp][i][j][size-w-1] -= dampingCoef*B[comp][i][j][size-w-1];
          }
        }
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
