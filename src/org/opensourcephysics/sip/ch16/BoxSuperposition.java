/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch16;

/**
 * A superposition of particle in a box quantum eigenstates.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould
 * @version 1.0  revised 03/23/05
 */
public class BoxSuperposition {
  double[] realCoef;
  double[] imagCoef;
  double[][] states;    // eigenfunctions
  double[] eigenvalues; // eigenvalues
  double[] x, realPsi, imagPsi;
  double[] zeroArray;

  /**
   * Constructs a time dependent superposition of particle in a box eigenstates.
   *
   * @param numberOfPoints int
   * @param realCoef double[]
   * @param imagCoef double[]
   */
  public BoxSuperposition(int numberOfPoints, double[] realCoef, double[] imagCoef) {
    if(realCoef.length!=imagCoef.length) {
      throw new IllegalArgumentException("Real and imaginary coefficients must have equal number of elements.");
    }
    this.realCoef = realCoef;
    this.imagCoef = imagCoef;
    int nstates = realCoef.length;
    // delay allocation of arrays for eigenstates
    states = new double[nstates][];    // eigenfunctions
    eigenvalues = new double[nstates]; // eigenvalues
    realPsi = new double[numberOfPoints];
    imagPsi = new double[numberOfPoints];
    zeroArray = new double[numberOfPoints];
    x = new double[numberOfPoints];
    double dx = BoxEigenstate.a/(numberOfPoints-1);
    double xo = 0;
    for(int j = 0, n = numberOfPoints;j<n;j++) {
      x[j] = xo;
      xo += dx;
    }
    for(int n = 0;n<nstates;n++) {
      states[n] = BoxEigenstate.getEigenstate(n, numberOfPoints);
      eigenvalues[n] = BoxEigenstate.getEigenvalue(n);
    }
    update(0); // compute the superpositon at t = 0
  }

  /**
   * Computes the superposition state at the given time.
   *
   * @param time double
   */
  void update(double time) {
    // set real and imaginary parts of wave function to zero
    System.arraycopy(zeroArray, 0, realPsi, 0, realPsi.length);
    System.arraycopy(zeroArray, 0, imagPsi, 0, imagPsi.length);
    for(int i = 0, nstates = realCoef.length;i<nstates;i++) {
      double[] phi = states[i];
      double re = realCoef[i];
      double im = imagCoef[i];
      double sin = Math.sin(time*eigenvalues[i]);
      double cos = Math.cos(time*eigenvalues[i]);
      for(int j = 1, n = phi.length-1;j<n;j++) {
        realPsi[j] += (re*cos-im*sin)*phi[j];
        imagPsi[j] += (im*cos+re*sin)*phi[j];
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
